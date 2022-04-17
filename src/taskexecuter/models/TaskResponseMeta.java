package taskexecuter.models;

import taskexecuter.interfaces.ResponseHandlerInterface;

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
