package rentalvehicalsystem.services;

import rentalvehicalsystem.enums.VehicleType;
import rentalvehicalsystem.models.BookingInfo;
import rentalvehicalsystem.models.VehicleBranchWiseDetail;
import rentalvehicalsystem.models.VehiclePriceInfo;

import java.time.LocalDateTime;
import java.util.*;

public class DataManagementService {
    private final Map<String, VehicleBranchWiseDetail> branchWiseDetails;

    public Map<String, VehicleBranchWiseDetail> getBranchWiseDetails() {
        return branchWiseDetails;
    }

    public List<VehiclePriceInfo> getVehiclePriceInfoList() {
        return vehiclePriceInfos;
    }

    private final List<VehiclePriceInfo> vehiclePriceInfos;


    public void bookVehicle(String branchName, VehicleType vehicleType, String vehicleId, LocalDateTime startTime, LocalDateTime endTime) {
        if (!branchWiseDetails.get(branchName).getUnavailableVehicle().containsKey(vehicleType)) {
            branchWiseDetails.get(branchName).getUnavailableVehicle().put(vehicleType, new PriorityQueue<BookingInfo>(Comparator.comparing(BookingInfo::getEndTime)));
        }
        branchWiseDetails.get(branchName).getUnavailableVehicle().get(vehicleType).offer(new BookingInfo(vehicleId, startTime, endTime));

    }

    public static DataManagementService INSTANCE = null;

    public static DataManagementService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManagementService();
        }
        return INSTANCE;
    }

    private DataManagementService() {
        branchWiseDetails = new HashMap<>();
        vehiclePriceInfos=new ArrayList<>();
    }

    public void createNewBranch(String branch) {
        if (!branchWiseDetails.containsKey(branch)) {
            branchWiseDetails.put(branch, new VehicleBranchWiseDetail(branch));
        }
    }

    public void addVehicle(String vehicleId, VehicleType vehicleType, String branchName) {
        if (!branchWiseDetails.get(branchName).getAvailableVehicle().containsKey(vehicleType)) {
            branchWiseDetails.get(branchName).getAvailableVehicle().put(vehicleType, new LinkedList<>());
        }
        branchWiseDetails.get(branchName).getAvailableVehicle().get(vehicleType).offer(vehicleId);
    }

    public void allocatePrice(String branchName, VehicleType vehicleType, double price) {
        vehiclePriceInfos.add(new VehiclePriceInfo(branchName, vehicleType, price));
    }

}
