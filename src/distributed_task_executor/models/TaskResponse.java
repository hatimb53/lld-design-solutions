package distributed_task_executor.models;

import distributed_task_executor.enums.TaskStatus;

public class TaskResponse {
    public long taskId;
    public TaskStatus taskStatus;

    public TaskResponse(long taskId, TaskStatus taskStatus) {

        this.taskId = taskId;
        this.taskStatus = taskStatus;
    }
}
