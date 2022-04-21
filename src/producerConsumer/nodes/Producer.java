package producerConsumer.nodes;

import producerConsumer.interfaces.ProducerBrokerInterface;

public class Producer{
    long token;

    String topic;
    ProducerBrokerInterface broker;

    public Producer(ProducerBrokerInterface broker){

        this.broker=broker;
        token=broker.generateToken();

    }
    public void createTopic(String topic){
        this.topic=topic;
        broker.createTopic(topic);
    }

    public void produce(String message){
        broker.produce(topic,message);
    }


}
