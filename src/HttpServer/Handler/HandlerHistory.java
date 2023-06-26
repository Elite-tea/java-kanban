package HttpServer.Handler;

import TaskManager.TaskManager;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
public class HandlerHistory extends DefaultHandler {

    public HandlerHistory(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        int statusCode = 400;
        String method = httpExchange.getRequestMethod();
        String path = String.valueOf(httpExchange.getRequestURI());

        System.out.println("Обработка запроса" + path + " с методом " + method);

        if (method.equals("GET")) {
            statusCode = 200;
            response = gson.toJson(taskManager.getHistory());
        } else {
            statusCode = 405;
            response = "Некорректный запрос";
        }

        httpExchange.getResponseHeaders().set("Content-Type", "text/plain; charset=");
        httpExchange.sendResponseHeaders(statusCode, 0);
        writers(httpExchange);

    }
}