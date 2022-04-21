package rentalvehicalsystem.services;

import rentalvehicalsystem.enums.Availability;
import rentalvehicalsystem.enums.VehicleType;
import rentalvehicalsystem.models.BookingInfo;
import rentalvehicalsystem.models.SnapshotInventory;
import rentalvehicalsystem.strategy.RentalPriceStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VehicleRentalService {
    private final DataManagementService dataManagementService;
    private final RentalPriceStrategy rentalPriceStrategy;

    public static VehicleRentalService INSTANCE = null;

    private VehicleRentalService(DataManagementService dataManagementService, RentalPriceStrategy rentalPriceStrategy) {
        this.dataManagementService = dataManagementService;
        this.rentalPriceStrategy = rentalPriceStrategy;
    }

    public static VehicleRentalService getInstance(DataManagementService dataManagementService, RentalPriceStrategy rentalPriceStrategy) {
        if (INSTANCE == null) {
            return new VehicleRentalService(dataManagementService, rentalPriceStrategy);
        }
        return INSTANCE;
    }

    public void addBranch(String branchName) {
        dataManagementService.createNewBranch(branchName);
    }

    public void allocatePrice(String branchName, VehicleType vehicleType, double price) {
        dataManagementService.allocatePrice(branchName, vehicleType, price);
    }

    public void addVehicle(String vehicleId, VehicleType vehicleType, String branchName) {
        dataManagementService.addVehicle(vehicleId, vehicleType, branchName);
    }

    public void bookVehicle(VehicleType vehicleType, LocalDateTime startTime, LocalDateTime endTime) {
        rentalPriceStrategy.allotVehicle(vehicleType, startTime, endTime);
    }

    public SnapshotInventory viewVehicleInventory(LocalDateTime startTime, LocalDateTime endTime) {
        List<SnapshotInventory.ItemView> viewList = new ArrayList<>();

        dataManagementService.getBranchWiseDetails().entrySet().forEach(entry -> {
            String branch = entry.getValue().getBranch();
            entry.getValue().getAvailableVehicle().entrySet().forEach(en -> {
                Iterator<String> iterator = en.getValue().iterator();
                while (iterator.hasNext()) {
                    String vehicleid = iterator.next();
                    viewList.add(new SnapshotInventory.ItemView(branch, en.getKey(), vehicleid, Availability.AVAILABLE));
                }

            });

            entry.getValue().getUnavailableVehicle().entrySet().forEach(en -> {
                Iterator<BookingInfo> iterator = en.getValue().iterator();
                while (iterator.hasNext()) {
                    BookingInfo bookingInfo = iterator.next();
                    if (bookingInfo.getEndTime().compareTo(startTime) < 0 || bookingInfo.getStartTime().compareTo(endTime) > 0) {
                        viewList.add(new SnapshotInventory.ItemView(branch, en.getKey(), bookingInfo.getVehicleId(), Availability.AVAILABLE));
                    } else {
                        viewList.add(new SnapshotInventory.ItemView(branch, en.getKey(), bookingInfo.getVehicleId(), Availability.NOT_AVAILABLE));
                    }
                }


            });
        });


        return new SnapshotInventory(viewList);
    }

}
