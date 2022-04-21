package rentalvehicalsystem.models;

import rentalvehicalsystem.enums.VehicleType;

public class VehiclePriceInfo {
    String branchName;
    VehicleType vehicleType;
    double price;

    public VehiclePriceInfo(String branchName,VehicleType vehicleType,double price){
        this.branchName=branchName;
        this.vehicleType=vehicleType;
        this.price=price;
    }

    public String getBranchName() {
        return branchName;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public double getPrice() {
        return price;
    }
}
