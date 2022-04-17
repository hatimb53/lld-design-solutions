package taskexecuter.models;

import taskexecuter.enums.TaskStatus;

public class TaskResponse {
    public long taskId;
    public TaskStatus taskStatus;

    public TaskResponse(long taskId, TaskStatus taskStatus) {

        this.taskId = taskId;
        this.taskStatus = taskStatus;
    }
}
