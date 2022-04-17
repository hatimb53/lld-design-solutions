package taskexecuter.handlers;

import taskexecuter.enums.TaskStatus;
import taskexecuter.exceptions.TaskFailedException;
import taskexecuter.models.Task;
import taskexecuter.models.TaskResponse;
import taskexecuter.models.TaskResponseMeta;
import taskexecuter.nodes.Worker;

import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dispatcher {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    Queue<Task> taskQueue;
    final Queue<Worker> workerQueue;
    final Queue<TaskResponseMeta> taskResponseQueue;

    final TaskRuntimeManager taskRuntimeManager;
    final Queue<Runnable> workerRunnableQueue;


    public  Dispatcher(Queue<Task> taskQueue, Queue<Worker> workerQueue, TaskRuntimeManager taskRuntimeManager, Queue<TaskResponseMeta> taskResponseQueue, Queue<Runnable> runnable)
     {
        this.taskRuntimeManager = taskRuntimeManager;
        this.workerQueue = workerQueue;
        this.taskQueue = taskQueue;
        this.taskResponseQueue=taskResponseQueue;
        this.workerRunnableQueue =runnable;
    }


    void executeTask(Worker worker, Task task){
        Runnable workerRunnable =() -> {
            TaskStatus taskStatus=TaskStatus.PROCESSING;
            logger.log(Level.INFO,String.format("task in process id=%d token=%d",task.id,task.clientToken));

            try {
              worker.executeTask(task);

                taskStatus=TaskStatus.COMPLETED;
                logger.log(Level.INFO,String.format("task completed id=%d token=%d",task.id,task.clientToken));

            } catch (TaskFailedException | InterruptedException ex) {
                taskStatus=TaskStatus.FAILED;
                logger.log(Level.INFO,String.format("task failed id=%d token=%d",task.id,task.clientToken));


            } finally {
                synchronized (taskRuntimeManager) {
                    taskRuntimeManager.updateStatus(task.id,taskStatus);
                }
                synchronized (workerQueue) {
                    workerQueue.offer(worker);
                }
                synchronized (taskResponseQueue){
                    taskResponseQueue.offer(new TaskResponseMeta(task.clientToken,task.responseHandlerInterface,new TaskResponse(task.id,taskStatus)));
                }
            }
            logger.log(Level.INFO,String.format("task completed id=%d token=%d",task.id,task.clientToken));

        };
        synchronized (workerRunnableQueue) {
            workerRunnableQueue.offer(workerRunnable);
        }
    }
    public void dispatch() {
        Task task = taskQueue.poll();
        Worker worker = workerQueue.poll();
        taskRuntimeManager.updateStatus(task.id, TaskStatus.PROCESSING);
        executeTask(worker,task);
    }

}
