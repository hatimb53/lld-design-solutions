package distributed_task_executor;

import distributed_task_executor.handlers.DistributedTaskManager;
import distributed_task_executor.interfaces.ClientInterface;
import distributed_task_executor.interfaces.TaskExecutionInterface;
import distributed_task_executor.nodes.Client;
import distributed_task_executor.tasks.examples.AddTask;
import distributed_task_executor.tasks.examples.PrintTask;

public class Application {
    public static void main(String args[]){
        int MAX_THREAD_POOL=10;
        int MAX_WORKERS=20;

        TaskExecutionInterface taskExecutionInterface=new DistributedTaskManager(MAX_THREAD_POOL,MAX_WORKERS);


        int count=1;
        for(int i=0;i<5;i++) {
            ClientInterface client = new Client("client-"+count++,taskExecutionInterface);
            client.submitTask(new AddTask());
        }
        for(int i=0;i<6;i++) {
            ClientInterface client = new Client("client-"+count++,taskExecutionInterface);
            client.submitTask(new PrintTask());
        }

    }
}
