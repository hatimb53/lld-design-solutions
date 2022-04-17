package distributed_task_executor.interfaces;

import distributed_task_executor.models.Task;

public interface TaskExecutionInterface {
     void submitTask(Task task);
     long generateNewToken();

}
