package producerConsumer.nodes;

import producerConsumer.interfaces.ConsumerBrokerInterface;
import producerConsumer.interfaces.ConsumerInterface;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements ConsumerInterface{
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    long token;
    String name;
    String topic;
    ConsumerBrokerInterface broker;

    Queue<String> messageQueue;

    public Consumer(String name, ConsumerBrokerInterface broker) {
        this.name = name;
        this.broker = broker;

        messageQueue = new LinkedList<>();

    }

   public void subscribe(String topic) {
        logger.log(Level.INFO, String.format("%s topic=%s  subscribed", name, topic));
        this.topic=topic;
        broker.subscribeToTopic(topic, this);
    }

    @Override
    public void update(String message) {
        logger.log(Level.INFO, String.format("%s topic=%s consumed message=%s", name, topic, message));
    }



}
