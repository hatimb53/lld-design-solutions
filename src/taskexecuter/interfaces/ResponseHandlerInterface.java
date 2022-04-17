package taskexecuter.interfaces;

import taskexecuter.models.TaskResponse;

public interface ResponseHandlerInterface {
    void sendResponse(TaskResponse taskResponse);
}
