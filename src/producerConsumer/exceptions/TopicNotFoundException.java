package producerConsumer.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TopicNotFoundException extends RuntimeException {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public TopicNotFoundException(String topicName) {
        super("topic not found");
        String errorMessage=String.format("topic not found %s", topicName);

        logger.log(Level.SEVERE, errorMessage);
    }
}
