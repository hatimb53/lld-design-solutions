package rentalvehicalsystem;

import rentalvehicalsystem.enums.VehicleType;
import rentalvehicalsystem.services.*;
import rentalvehicalsystem.strategy.LowestRentalPriceStrategy;
import rentalvehicalsystem.strategy.RentalPriceStrategy;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String args[]) throws InterruptedException {
        DataManagementService dataManagementService = DataManagementService.getInstance();
        RentalPriceStrategy rentalPriceStrategy = new LowestRentalPriceStrategy(dataManagementService);
        VehicleRentalService vehicleRentalService = VehicleRentalService.getInstance(dataManagementService, rentalPriceStrategy);

        MonitoringService monitoringService = MonitoringService.getInstance(dataManagementService);

        Runnable runnable = () -> {
            while (true) {

                monitoringService.makeAvailable();

                try {
                    Thread.sleep(1000 * 60 * 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        vehicleRentalService.addBranch("A");

        vehicleRentalService.addVehicle("v1", VehicleType.Sedan, "A");

        vehicleRentalService.allocatePrice("A", VehicleType.Sedan, 100.00);

        LocalDateTime startTime = LocalDateTime.of(2021, 4, 21, 13, 0);
        LocalDateTime endTime = LocalDateTime.of(2021, 4, 21, 14, 0);

        vehicleRentalService.bookVehicle(VehicleType.Sedan, startTime, endTime);

        logger.log(Level.INFO, vehicleRentalService.viewVehicleInventory(startTime, endTime).toString());

        logger.log(Level.INFO, vehicleRentalService.viewVehicleInventory(LocalDateTime.now(), LocalDateTime.now()).toString());


    }


}
