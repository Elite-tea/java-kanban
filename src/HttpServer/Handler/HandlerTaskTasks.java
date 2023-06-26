package HttpServer.Handler;

import TaskManager.TaskManager;
import TaskManager.TypeTask;
import Tasks.Task;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HandlerTaskTasks extends DefaultHandler {

    public HandlerTaskTasks(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        int statusCode;
        String method = httpExchange.getRequestMethod();
        String path = String.valueOf(httpExchange.getRequestURI());

        System.out.println("Обработка запроса" + path + " с методом " + method);

        switch (method) {
            case "GET":
                String query = httpExchange.getRequestURI().getQuery();
                if (query == null) {
                    statusCode = 200;
                    String jsonString = gson.toJson(taskManager.getAllTasks(TypeTask.TASK));
                    System.out.println("GET TASKS: " + jsonString);
                    response = gson.toJson(jsonString);
                } else {
                    try {
                        int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                        Task task = taskManager.getByIdTask(id);
                        if (task != null) {
                            statusCode = 200;
                            response = gson.toJson(task);
                        } else {
                            statusCode = 404;
                            httpExchange.sendResponseHeaders(statusCode, 0);
                            response = "Таск с таким id не существует";
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        statusCode = 400;
                        response = "В запросе нет параметра id";
                    } catch (NumberFormatException e) {
                        statusCode = 400;
                        response = "Некорректный запрос";
                    }
                }
                break;
            case "POST":
                String bodyRequest = readText(httpExchange);
                if(bodyRequest.isEmpty()){
                    statusCode = 400;
                    httpExchange.sendResponseHeaders(statusCode, 0);
                    return;
                }
                try {
                    Task task = gson.fromJson(bodyRequest, Task.class);
                    int id = task.getId();
                    taskManager.updateTask(task.getId(), task);
                    statusCode = 200;
                    response = "Таск с id=" + id + " обновлен";
                } catch (JsonSyntaxException e) {
                    statusCode = 400;
                    response = "Не верный запрос";
                }
                break;
            case "DELETE":
                response = "";
                query = httpExchange.getRequestURI().getQuery();
                if (query == null) {
                    taskManager.removeAllTask(TypeTask.TASK);
                    statusCode = 200;
                } else {
                    try {
                        int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                        taskManager.removeTaskId(id, TypeTask.TASK);
                        statusCode = 200;
                    } catch (StringIndexOutOfBoundsException e) {
                        statusCode = 400;
                        response = "В запросе нет параметра id";
                    } catch (NumberFormatException e) {
                        statusCode = 400;
                        response = "Не верный id";
                    }
                }
                break;
            default:
                statusCode = 405;
                response = "Некорректный запрос";
        }

        httpExchange.getResponseHeaders().set("Content-Type", "text/plain; charset=");
        httpExchange.sendResponseHeaders(statusCode, 0);
        writers(httpExchange);
    }
}