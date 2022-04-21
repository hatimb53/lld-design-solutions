package rentalvehicalsystem.services;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class MonitoringService {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final DataManagementService dataManagementService;
    private static MonitoringService INSTANCE = null;


    private MonitoringService(DataManagementService dataManagementService) {
        this.dataManagementService = dataManagementService;
    }

    public static MonitoringService getInstance(DataManagementService dataManagementService) {
        if (INSTANCE == null) {
            INSTANCE = new MonitoringService(dataManagementService);
        }
        return INSTANCE;
    }

    public void makeAvailable() {
        LocalDateTime currentDateTime = LocalDateTime.now();
       // logger.log(Level.INFO,"make available");
        //TODO: add logic for making vehicle available
        dataManagementService.getBranchWiseDetails().entrySet().stream().forEach(branchEntry -> {

            branchEntry.getValue().getUnavailableVehicle().entrySet().forEach(bookingEntry -> {
              //  logger.log(Level.INFO, String.format("current time %s booking entry end time %s", currentDateTime, bookingEntry.getValue().peek().getEndTime().toString()));
                while (!bookingEntry.getValue().isEmpty()&&currentDateTime.compareTo(bookingEntry.getValue().peek().getEndTime()) == 1) {
                   // logger.log(Level.INFO, String.format("current time %s booking entry end time %s", currentDateTime, bookingEntry.getValue().peek().getEndTime().toString()));

                    dataManagementService.getBranchWiseDetails().get(branchEntry.getKey()).getAvailableVehicle()
                            .get(bookingEntry.getKey()).offer(bookingEntry.getValue().poll().getVehicleId());

                }
            });
        });
    }
}
