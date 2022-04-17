package distributed_task_executor.handlers;

import distributed_task_executor.enums.TaskStatus;
import distributed_task_executor.models.TaskRuntimeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class TaskRuntimeManager {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final List<TaskRuntimeInfo> taskRuntimeInfoList =new ArrayList<>();
    public void createTaskStatsInfo(long taskId,long clientId){
           taskRuntimeInfoList.add(new TaskRuntimeInfo(taskId,clientId));
    }
    TaskRuntimeInfo fetchInfoById(long id){
        return taskRuntimeInfoList.stream().filter(x->x.taskId==id).findFirst().orElseThrow(NoSuchElementException::new);
    }

   public synchronized void updateStatus(long id, TaskStatus status) {
       TaskRuntimeInfo taskRuntimeInfo = fetchInfoById(id);
        taskRuntimeInfo.status = status;
   }
}
