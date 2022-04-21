package rentalvehicalsystem.strategy;

import rentalvehicalsystem.enums.VehicleType;
import rentalvehicalsystem.exceptions.NoVehicleAvailableException;
import rentalvehicalsystem.models.VehiclePriceInfo;
import rentalvehicalsystem.services.DataManagementService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class LowestRentalPriceStrategy implements RentalPriceStrategy {
    private final DataManagementService dataManagementService;

    public LowestRentalPriceStrategy(DataManagementService dataManagementService) {
        this.dataManagementService = dataManagementService;
    }

    @Override
    public String allotVehicle(VehicleType vehicleType, LocalDateTime startTime, LocalDateTime endTime) {
        Set<String> branchSet = getAllBranchContainsVehicle(vehicleType);

        VehiclePriceInfo vehiclePriceInfo = dataManagementService.getVehiclePriceInfoList().stream()
                .filter(vehicle -> branchSet.contains(vehicle.getBranchName()) && vehicle.getVehicleType() == vehicleType)
                .sorted(Comparator.comparing(VehiclePriceInfo::getPrice)).findFirst().orElseThrow(() -> new NoVehicleAvailableException(vehicleType));

        String vehicleId = getAvailableVehicle(vehiclePriceInfo, vehicleType);

        dataManagementService.bookVehicle(vehiclePriceInfo.getBranchName(), vehicleType, vehicleId, startTime, endTime);

        return vehicleId;

    }

    private String getAvailableVehicle(VehiclePriceInfo vehiclePriceInfo, VehicleType vehicleType) {
        return dataManagementService.getBranchWiseDetails().get(vehiclePriceInfo.getBranchName()).getAvailableVehicle().get(vehicleType).poll();

    }

    private Set<String> getAllBranchContainsVehicle(VehicleType vehicleType) {
        return dataManagementService.getBranchWiseDetails().entrySet()
                .stream().filter(entry ->
                        entry.getValue().getAvailableVehicle().containsKey(vehicleType) &&
                                !entry.getValue().getAvailableVehicle().get(vehicleType).isEmpty()).map(x -> x.getValue().getBranch()).collect(Collectors.toSet());

    }
}
