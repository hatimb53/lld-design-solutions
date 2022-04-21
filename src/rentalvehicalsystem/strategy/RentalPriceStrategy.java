package rentalvehicalsystem.strategy;

import rentalvehicalsystem.enums.VehicleType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface RentalPriceStrategy {
    String allotVehicle(VehicleType vehicleType, LocalDateTime startTime, LocalDateTime endTime);
}
