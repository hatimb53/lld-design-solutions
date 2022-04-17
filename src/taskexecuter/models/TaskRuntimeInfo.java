package taskexecuter.models;

import taskexecuter.enums.TaskStatus;

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
