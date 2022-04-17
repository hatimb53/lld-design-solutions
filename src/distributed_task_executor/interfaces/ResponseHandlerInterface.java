package distributed_task_executor.interfaces;

import distributed_task_executor.models.TaskResponse;

public interface ResponseHandlerInterface {
    void sendResponse(TaskResponse taskResponse);
}
