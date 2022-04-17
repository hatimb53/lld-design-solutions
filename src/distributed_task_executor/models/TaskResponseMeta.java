package distributed_task_executor.models;

import distributed_task_executor.interfaces.ResponseHandlerInterface;

public class TaskResponseMeta {
    public long userToken;
    public ResponseHandlerInterface responseHandlerInterface;
    public TaskResponse taskResponse;
    public TaskResponseMeta(long userToken, ResponseHandlerInterface responseHandlerInterface, TaskResponse taskResponse){
        this.responseHandlerInterface = responseHandlerInterface;
        this.taskResponse = taskResponse;
        this.userToken=userToken;
    }
}
