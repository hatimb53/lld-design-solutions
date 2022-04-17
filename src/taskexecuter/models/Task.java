package taskexecuter.models;

import taskexecuter.enums.TaskType;
import taskexecuter.interfaces.ResponseHandlerInterface;

import java.util.function.Consumer;

public class Task {
   public long id;
    public long clientToken;
    public ResponseHandlerInterface responseHandlerInterface;
    public Runnable runnable;

   public Task(long id, long clientToken, ResponseHandlerInterface responseHandlerInterface,Runnable runnable){
       this.id=id;

       this.runnable=runnable;
       this.clientToken=clientToken;
       this.responseHandlerInterface = responseHandlerInterface;

   }

}
