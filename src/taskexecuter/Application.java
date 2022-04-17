package taskexecuter;

import taskexecuter.handlers.DistributedTaskManager;
import taskexecuter.interfaces.ClientInterface;
import taskexecuter.nodes.Client;
import taskexecuter.tasks.examples.AddTask;
import taskexecuter.tasks.examples.PrintTask;

public class Application {
    public static void main(String args[]){
        int MAX_THREAD_POOL=10;
        int MAX_WORKERS=20;

        DistributedTaskManager distributedTaskManager=new DistributedTaskManager(MAX_THREAD_POOL,MAX_WORKERS);


        int count=1;
        for(int i=0;i<5;i++) {
            ClientInterface client = new Client("client-"+count++,distributedTaskManager);
            client.submitTask(new AddTask());
        }
        for(int i=0;i<6;i++) {
            ClientInterface client = new Client("client-"+count++,distributedTaskManager);
            client.submitTask(new PrintTask());
        }

    }
}
