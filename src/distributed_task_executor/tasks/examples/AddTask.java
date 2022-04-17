package distributed_task_executor.tasks.examples;


import java.util.logging.Level;
import java.util.logging.Logger;

public class AddTask implements Runnable{

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Override
    public void run() {
        int a =2;
        int b=3;
        int c=a+b;

        logger.log(Level.INFO,String.format("sum of  %d and %d %d",a,b,c));
    }
}