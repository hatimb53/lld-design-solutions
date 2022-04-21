package rentalvehicalsystem.models;

import rentalvehicalsystem.enums.VehicleType;

import java.util.*;

public class VehicleBranchWiseDetail {
    String branch;



    public String getBranch() {
        return branch;
    }

    private void setBranch(String branch) {
        this.branch = branch;
    }

    public Map<VehicleType, Queue<String>> getAvailableVehicle() {
        return availableVehicle;
    }


    public Map<VehicleType, PriorityQueue<BookingInfo>> getUnavailableVehicle() {
        return unavailableVehicle;
    }


    Map<VehicleType, Queue<String>> availableVehicle;
    Map<VehicleType, PriorityQueue<BookingInfo>> unavailableVehicle;

    public VehicleBranchWiseDetail(String branch){
        setBranch(branch);
        availableVehicle=new HashMap<>();
        unavailableVehicle=new HashMap<>();

    }
}
