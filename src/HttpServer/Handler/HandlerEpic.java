package HttpServer.Handler;


import TaskManager.TaskManager;
import TaskManager.TypeTask;
import Tasks.Epic;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HandlerEpic extends DefaultHandler {
    public HandlerEpic(TaskManager taskManager) {
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
                    String jsonString = gson.toJson(taskManager.getAllTasks(TypeTask.EPIC));
                    System.out.println("GET EPIC: " + jsonString);
                    response = gson.toJson(jsonString);
                } else {
                    try {
                        int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                        Epic epic = taskManager.getByIdEpic(id);
                        if (epic != null) {
                            statusCode = 200;
                            response = gson.toJson(epic);
                        } else {
                            statusCode = 404;
                            exchange.sendResponseHeaders(statusCode, 0);
                            response = "Эпик с таким id не существует";
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
                if(bodyRequest.isEmpty()){
                    statusCode = 400;
                    exchange.sendResponseHeaders(statusCode, 0);
                    return;
                }
                try {
                    Epic epic = gson.fromJson(bodyRequest, Epic.class);
                    Integer id = epic.getId();
                    if (epic != null) {
                        taskManager.updateTask(epic.getId(),epic);
                        statusCode = 200;
                        response = "Эпик с id=" + id + " обновлен";
                    } else {
                        System.out.println("CREATED");
                        taskManager.createEpic(epic);
                        System.out.println("CREATED EPIC: " + epic);
                        int idCreated = epic.getId();
                        statusCode = 201;
                        response = "Эпик с id= " + idCreated + "создан";
                    }
                } catch (JsonSyntaxException e) {
                    statusCode = 400;
                    response = "Не верный запрос";
                }
                break;
            case "DELETE":
                response = "";
                query = exchange.getRequestURI().getQuery();
                if (query == null) {
                    taskManager.removeAllTask(TypeTask.EPIC);
                    statusCode = 200;
                } else {
                    try {
                        int id = Integer.parseInt(query.substring(query.indexOf("id=") + 3));
                        taskManager.removeTaskId(id,TypeTask.EPIC);
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