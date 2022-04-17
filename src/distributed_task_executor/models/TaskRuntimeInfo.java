package distributed_task_executor.models;

import distributed_task_executor.enums.TaskStatus;

import java.time.LocalDate;

public class TaskRuntimeInfo {
   public long taskId;
   public long clientId;
   public TaskStatus status;
    LocalDate createdAt;
    LocalDate completedAt;

    public TaskRuntimeInfo(long taskId, long clientId){
        status=TaskStatus.CREATED;
        this.taskId=taskId;
        this.clientId=clientId;
    }
}
