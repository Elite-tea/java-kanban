package Tests;

import HistoryManager.HistoryManager;
import HttpServer.KVServer;
import TaskManager.HttpTaskManager;
import TaskManager.Managers;
import TaskManager.TaskManager;
import TaskManager.TypeTask;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpTaskManagerTest<T extends TaskManagersTest<HttpTaskManager>> {
    private KVServer server;
    private TaskManager manager;

    private HttpTaskManager httpTaskManager;

    @BeforeEach
    public void createManager() throws IOException, InterruptedException {
        server = new KVServer();
        server.start();
        manager = Managers.getDefault("http://localhost:8078");

    }

    @AfterEach
    public void stopServer() {
        server.stop();
    }

    @Test
    public void shouldLoadTasks() throws IOException, InterruptedException {
        Task task1 = new Task("Таск тест", "Описание таск теста", 3, 30,
                LocalDateTime.of(2023, 8, 10, 12, 0));
        Task task2 = new Task("Таск тест", "Описание таск теста", 1, 30,
                LocalDateTime.of(2023, 10, 10, 12, 0));
        manager.createTask(task1);
        manager.createTask(task2);
        manager.getByIdTask(task2.getId());
        manager.getByIdTask(task1.getId());
        List<Task> list = manager.getHistory();
        assertEquals(manager.getAllTasks(TypeTask.TASK), list);

        HistoryManager historyManager = Managers.getDefaultHistory();
        httpTaskManager = new HttpTaskManager(historyManager, "http://127.0.0.1:" + KVServer.PORT, true);
        assertEquals(manager.getTaskList(), httpTaskManager.getTaskList(),
                "The list of tasks after unloading does not match");
        assertEquals(manager.getHistory(), httpTaskManager.getHistory(),
                "The list of history after unloading does not match");
        assertEquals(manager.getPrioritizedTasks(), httpTaskManager.getPrioritizedTasks(),
                "The list of prioritized tasks after unloading does not match");

    }

    @Test
    public void shouldLoadEpics() throws IOException, InterruptedException {

        Epic epic1 = new Epic("ЭпикТест", "Эпик тест", 1,
                LocalDateTime.of(2023, 7, 10, 12, 0), 30);
        Epic epic2 = new Epic("ЭпикТест", "Эпик тест", 2,
                LocalDateTime.of(2023, 10, 10, 12, 0), 30);
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        manager.getByIdEpic(epic1.getId());
        manager.getByIdEpic(epic2.getId());
        List<Task> list = manager.getHistory();
        assertEquals(manager.getAllTasks(TypeTask.EPIC), list);

        HistoryManager historyManager = Managers.getDefaultHistory();
        httpTaskManager = new HttpTaskManager(historyManager, "http://localhost:" + KVServer.PORT, true);
        assertEquals(manager.getEpicsList(), httpTaskManager.getEpicsList(),
                "The list of epics after unloading does not match");
        assertEquals(manager.getHistory(), httpTaskManager.getHistory(),
                "The list of history after unloading does not match");
    }

    @Test
    public void shouldLoadSubtasks() throws IOException, InterruptedException {
        Epic epic1 = new Epic("ЭпикТест", "Эпик тест", 1,
                LocalDateTime.of(2023, 7, 10, 12, 0), 30);
        Subtask subtask1 = new Subtask("СабТаскЭпикТест", "Саб таск эпик тест", 1, 3,
                LocalDateTime.of(2023, 6, 10, 12, 0), 30);
        Subtask subtask2 = new Subtask("СабТаскЭпикТест", "Саб таск эпик тест", 1, 2,
                LocalDateTime.of(2023, 10, 10, 12, 0), 30);
        manager.createEpic(epic1);
        manager.createSubTask(subtask1);
        manager.createSubTask(subtask2);
        manager.getByIdSubTask(subtask2.getId());
        manager.getByIdSubTask(subtask1.getId());
        List<Task> list = manager.getHistory();
        assertEquals(manager.getAllTasks(TypeTask.SUBTASK), list);

        HistoryManager historyManager = Managers.getDefaultHistory();
        httpTaskManager = new HttpTaskManager(historyManager, "http://localhost:" + KVServer.PORT, true);
        assertEquals(manager.getSubtasksList(), httpTaskManager.getSubtasksList(),
                "Список задач не совпадает");
        assertEquals(manager.getHistory(), httpTaskManager.getHistory(),
                "Список задач не совпадает");
// здесь http manager добавляет почему-то ещё и задачу из первого теста и подзадачу с идентификатором 2
        assertEquals(manager.getPrioritizedTasks(), httpTaskManager.getPrioritizedTasks(),
                "Список приоритетных задач не совпадает");
    }
}