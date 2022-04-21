package producerConsumer.interfaces;

public interface ConsumerBrokerInterface extends BrokerInterface {
   void subscribeToTopic(String topic, ConsumerInterface consumer);

}
