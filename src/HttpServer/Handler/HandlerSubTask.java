package HttpServer.Handler;

import TaskManager.TaskManager;
import TaskManager.TypeTask;
import Tasks.Subtask;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HandlerSubTask extends DefaultHandler {

    public HandlerSubTask(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        int statusCode;
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                String query = exchange.getRequestURI().getQuery();
                if (query == null) {
                    statusCode = 200;
                    response = gson.toJson(taskManager.getAllTasks(TypeTask.SUBTASK));
                } else {
                    try {
                        int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                        Subtask subtask = taskManager.getByIdSubTask(id);
                        if (subtask != null) {
                            statusCode = 200;
                            response = gson.toJson(subtask);
                        } else {
                            statusCode = 404;
                            exchange.sendResponseHeaders(statusCode, 0);
                            response = "Сабтаск с данным id не существует.";
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        statusCode = 400;
                        response = "В запросе нет параметра id";
                    } catch (NumberFormatException e) {
                        statusCode = 400;
                        response = "Не верный id";
                    }
                }
                break;
            case "POST":
                String bodyRequest = readText(exchange);
                if (bodyRequest.isEmpty()) {
                    statusCode = 400;
                    exchange.sendResponseHeaders(statusCode, 0);
                    return;
                }
                try {
                    Subtask subtask = gson.fromJson(bodyRequest, Subtask.class);
                    int id = subtask.getId();
                    taskManager.updateTask(subtask.getId(), subtask);
                    statusCode = 200;
                    response = "Сабтаск с id=" + id + " обновлен";
                } catch (JsonSyntaxException e) {
                    response = "Не верный запрос";
                    statusCode = 400;
                }
                break;
            case "DELETE":
                response = "";
                query = exchange.getRequestURI().getQuery();
                if (query == null) {
                    taskManager.removeAllTask(TypeTask.SUBTASK);
                    statusCode = 200;
                } else {
                    try {
                        int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                        taskManager.removeTaskId(id, TypeTask.SUBTASK);
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
                response = "Не верный запрос";
        }

        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=");
        exchange.sendResponseHeaders(statusCode, 0);
        writers(exchange);
    }
}