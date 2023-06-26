package HttpServer;

import HttpServer.Handler.*;
import TaskManager.Managers;
import TaskManager.TaskManager;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private final HttpServer httpServer;
    private static final int PORT = 8080;

    public HttpTaskServer() throws IOException {
        TaskManager taskManager = Managers.getDefault();

        this.httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks/task/", new HandlerTaskTasks(taskManager));
        httpServer.createContext("/tasks/epic/", new HandlerEpic(taskManager));
        httpServer.createContext("/tasks/subtask/", new HandlerSubTask(taskManager));
        httpServer.createContext("/tasks/subtask/epic/", new HandlerEpicSubTask(taskManager));
        httpServer.createContext("/tasks/history/", new HandlerHistory(taskManager));
        httpServer.createContext("/tasks/", new HandlerTasks(taskManager));
    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(1);
    }

}
