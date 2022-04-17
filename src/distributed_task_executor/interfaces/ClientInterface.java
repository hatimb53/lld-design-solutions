package distributed_task_executor.interfaces;

public interface ClientInterface {
     void submitTask(Runnable runnable);
}
