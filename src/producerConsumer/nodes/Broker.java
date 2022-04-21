package producerConsumer.nodes;

import producerConsumer.exceptions.TopicNotFoundException;
import producerConsumer.interfaces.ConsumerBrokerInterface;
import producerConsumer.interfaces.ConsumerInterface;
import producerConsumer.interfaces.ProducerBrokerInterface;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Broker implements ProducerBrokerInterface, ConsumerBrokerInterface {
    Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    Map<String, Queue<String>> messageQueue;
    Map<String,List<ConsumerInterface>> consumerMap;

    Set<Long> registeredTokenSet=new HashSet<>();

    public Broker(){

        messageQueue=new HashMap<>();
        consumerMap=new HashMap<>();
        Runnable runnable=()->{
            while(true){
                 messageQueue.entrySet().forEach(entry->{
                     if(!entry.getValue().isEmpty()){
                         String message=entry.getValue().poll();
                         updateConsumer(entry.getKey(),message);
                     }
                 });

                try {
                    Thread.sleep((int) (Math.random() * 2000) +500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread=new Thread(runnable);
        thread.start();

    }


   public void subscribeToTopic(String topic, ConsumerInterface consumer){

        if(!consumerMap.containsKey(topic)){
            throw new TopicNotFoundException(topic);
        }
       consumerMap.get(topic).add(consumer);
    }


    public void produce(String topic, String message){
        if(!messageQueue.containsKey(topic)){
            throw new TopicNotFoundException(topic);
        }
        logger.log(Level.INFO,String.format("message produced %s",message));
        messageQueue.get(topic).offer(message);
    }

    @Override
    public void createTopic(String topic) {
        logger.log(Level.INFO,String.format("topic created  %s",topic));
        messageQueue.put(topic,new LinkedList<>());
        consumerMap.put(topic,new LinkedList<>());
    }

    void updateConsumer(String topic, String message){
        consumerMap.get(topic).forEach(consumer -> consumer.update(message));
    }

    @Override
    public long generateToken() {
        long token= Math.abs(new Random().nextLong());
        registeredTokenSet.add(token);
        return token;
    }
}
