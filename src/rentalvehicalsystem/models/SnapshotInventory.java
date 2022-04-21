package rentalvehicalsystem.models;

import rentalvehicalsystem.enums.Availability;
import rentalvehicalsystem.enums.VehicleType;

import java.util.List;

public class SnapshotInventory {
    List<ItemView> itemViewList;

    public SnapshotInventory(List<ItemView> itemViewList) {
        this.itemViewList = itemViewList;
    }

    public String toString() {
        return "snapshot:" + itemViewList;
    }

    public static class ItemView {
        String branch;
        VehicleType vehicleType;
        String vehicleId;
        Availability availbility;

        public ItemView(String branch, VehicleType vehicleType, String vehicleId, Availability availability) {
            this.branch = branch;
            this.vehicleType = vehicleType;
            this.vehicleId = vehicleId;
            this.availbility = availability;
        }

        public String toString() {
            return String.format("Branch: %s, Vehicle Type:%s, Vehicle Id:%s, Availability: %s"
                    , branch, vehicleType.toString(), vehicleId, availbility);
        }
    }

}
