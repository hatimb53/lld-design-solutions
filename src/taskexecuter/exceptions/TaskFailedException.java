package taskexecuter.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskFailedException extends  RuntimeException{
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public TaskFailedException(String errorMessage){
        super(errorMessage);
        logger.log(Level.SEVERE,errorMessage);
    }
}
