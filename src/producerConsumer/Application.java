package producerConsumer;

import producerConsumer.nodes.Broker;
import producerConsumer.nodes.Consumer;
import producerConsumer.nodes.Producer;

import java.util.Random;

public class Application {
    public static void main(String args[]) throws InterruptedException {

        Broker broker=new Broker();
        String topic1="topic-1";
        Producer producer=new Producer(broker);
        producer.createTopic(topic1);

        Consumer consumer1=new Consumer("consumer-1",broker);

         consumer1.subscribe(topic1);

        Consumer consumer2=new Consumer("consumer-2",broker);

        consumer2.subscribe(topic1);

        for(int i=0;i<10;i++) {
            System.out.println("i="+i);
            producer.produce("message1");
            Thread.sleep((int) (Math.random() * 2000) +500);
        }




    }
}
