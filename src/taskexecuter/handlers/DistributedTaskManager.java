package taskexecuter.handlers;

import taskexecuter.exceptions.UnAuthorizedUserException;
import taskexecuter.interfaces.TaskExecutionInterface;
import taskexecuter.models.Task;
import taskexecuter.models.TaskResponseMeta;
import taskexecuter.nodes.Worker;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DistributedTaskManager implements TaskExecutionInterface {

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    final Queue<Task> taskQueue = new LinkedList<>();
    final Queue<Worker> workerQueue = new LinkedList<>();
    final Queue<Runnable> workerRunnableQueue =new LinkedList<>();
    final Queue<TaskResponseMeta> responseQueue = new LinkedList<>();
    final TaskRuntimeManager taskRuntimeManager = new TaskRuntimeManager();

    Set<Long> registeredTokenSet = new HashSet<>();

    private final ExecutorService pool;


    public DistributedTaskManager(int MAX_T, int MAX_W) {

        pool = Executors.newFixedThreadPool(MAX_T);

        addWorkers(MAX_W);

        Runnable dispatcherExecutor = () -> {
            Dispatcher dispatcher = new Dispatcher(taskQueue, workerQueue, taskRuntimeManager, responseQueue, workerRunnableQueue);
            while (true) {
                if (!taskQueue.isEmpty() && !workerQueue.isEmpty()) {
                    dispatcher.dispatch();
                    logger.log(Level.INFO, "dispatched");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, "interrupted");
                }
            }
        };


        execute(dispatcherExecutor);

        Runnable responseHandlerExecutor = () -> {
            ResponseHandler responseHandler = new ResponseHandler(responseQueue);
            while (true) {
                if (!responseQueue.isEmpty()) {
                    logger.log(Level.INFO, "sending response");
                    responseHandler.sendResponse();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, "interrupted");
                }
            }
        };

        execute(responseHandlerExecutor);

        Runnable taskExecutor = () -> {
            while (true) {

                if (!workerRunnableQueue.isEmpty()) {
                    logger.log(Level.INFO, "executing...");
                    execute(workerRunnableQueue.poll());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, "interrupted");
                }
            }
        };
        execute(taskExecutor);

    }

    void execute(Runnable runnable) {
        pool.execute(runnable);
    }

    public void addWorkers(int n) {
        synchronized (workerQueue) {
            while (n-- > 0) {
                workerQueue.offer(new Worker(new Random().nextLong()));
            }
        }
    }


    @Override
    public void submitTask(Task task) {

        if (!registeredTokenSet.contains(task.clientToken)) {
            String errMsg="generate token first";

            throw new UnAuthorizedUserException(errMsg);
        }
        taskRuntimeManager.createTaskStatsInfo(task.id, task.clientToken);
        logger.log(Level.INFO, String.format("task created  id=%d token=%d", task.id, task.clientToken));

        taskQueue.offer(task);
    }

    @Override
    public long generateNewToken() {
        long token = Math.abs(new Random().nextLong());
        // logger.log(Level.INFO,"registered token="+token);
        registeredTokenSet.add(token);
        return token;
    }


}
