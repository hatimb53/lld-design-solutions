package distributed_task_executor.nodes;

import distributed_task_executor.interfaces.ClientInterface;
import distributed_task_executor.interfaces.ResponseHandlerInterface;
import distributed_task_executor.interfaces.TaskExecutionInterface;
import distributed_task_executor.models.Task;
import distributed_task_executor.models.TaskResponse;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements ClientInterface, ResponseHandlerInterface {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    long token;
    String name;
    TaskExecutionInterface taskExecutionInterface;

    public Client(String name,TaskExecutionInterface taskExecutionInterface){
        this.name=name;
        this.taskExecutionInterface=taskExecutionInterface;

        token= taskExecutionInterface.generateNewToken();
        logger.log(Level.INFO,String.format("client registered with name=%s token=%d",name,token));
    }
    @Override
    public void submitTask(Runnable taskRunnable) {

        long taskId=Math.abs(new Random().nextLong());

        Task task = new Task(taskId,token,this,taskRunnable);

        logger.log(Level.INFO,String.format("task submitted by client id=%d token=%d ",taskId,token));

        taskExecutionInterface.submitTask(task);

    }

    @Override
    public void sendResponse(TaskResponse taskResponse) {
        logger.log(Level.INFO, String.format("received response for %s task id=%s, status=%s  client token=%s", name, taskResponse.taskId, taskResponse.taskStatus, token));
    }
}
