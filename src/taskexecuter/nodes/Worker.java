package taskexecuter.nodes;

import taskexecuter.exceptions.TaskFailedException;
import taskexecuter.models.Task;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class Worker {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public long id;

    public Worker(long id){
        this.id=id;
    }
    public void executeTask(Task task) throws InterruptedException, TaskFailedException {
        logger.log(Level.INFO,String.format("task executing..id=%d",task.id));

        task.runnable.run();

        sleep((int)(Math.random()*5000)+500);

        if(new Random().nextBoolean()){
            throw new TaskFailedException(String.format("task failed id=%d",task.id));
        }
    }


}
