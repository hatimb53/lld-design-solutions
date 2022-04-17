package taskexecuter.interfaces;

import taskexecuter.models.Task;

public interface TaskExecutionInterface {
     void submitTask(Task task);
     long generateNewToken();

}
