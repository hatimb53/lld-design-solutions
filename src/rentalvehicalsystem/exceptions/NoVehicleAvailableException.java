package rentalvehicalsystem.exceptions;

import rentalvehicalsystem.enums.VehicleType;

public class NoVehicleAvailableException extends RuntimeException{

    public NoVehicleAvailableException(VehicleType vehicle){
        super(vehicle.toString());

    }
}
