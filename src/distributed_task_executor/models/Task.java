package distributed_task_executor.models;

import distributed_task_executor.interfaces.ResponseHandlerInterface;

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
