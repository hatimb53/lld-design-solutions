package taskexecuter.handlers;

import taskexecuter.models.TaskResponseMeta;

import java.util.Queue;
import java.util.logging.Logger;

public class ResponseHandler{
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        Queue<TaskResponseMeta> responseQueue;

        ResponseHandler(Queue<TaskResponseMeta> queue){
            responseQueue=queue;
        }
        void sendResponse(){

                TaskResponseMeta taskResponseMeta= responseQueue.poll();
                taskResponseMeta.responseHandlerInterface.sendResponse(taskResponseMeta.taskResponse);

        }
}
