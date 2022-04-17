package taskexecuter.tasks.examples;


import java.util.logging.Level;
import java.util.logging.Logger;

public class PrintTask implements Runnable{

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Override
    public void run() {
        logger.log(Level.INFO,"hello world");
    }
}