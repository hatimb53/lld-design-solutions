package producerConsumer.interfaces;

public interface ProducerBrokerInterface extends BrokerInterface {
    void produce(String topic,String message);

    void createTopic(String topic);

}

