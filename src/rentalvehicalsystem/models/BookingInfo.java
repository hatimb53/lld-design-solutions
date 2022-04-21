package rentalvehicalsystem.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingInfo {
    String vehicleId;

    public String getVehicleId() {
        return vehicleId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    LocalDateTime startTime;
    LocalDateTime endTime;
    public BookingInfo(String vehicleId,LocalDateTime startTime,LocalDateTime endTime){
        this.vehicleId=vehicleId;
        this.startTime=startTime;
        this.endTime=endTime;
    }

}
