package Tests;

import TaskManager.TaskManager;
import TaskManager.TypeTask;
import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract class TaskManagersTest<T extends TaskManager> {
    protected T manager;
    ArrayList<Integer> idSubtasks = new ArrayList<>();

    Epic epicTest = new Epic("ЭпикТест", "Эпик тест", idSubtasks, 1,
            LocalDateTime.of(2023, 7, 10, 12, 0), 30);
    Subtask subTaskTest = new Subtask("СабТаскЭпикТест", "Саб таск эпик тест", 1, 2,
            LocalDateTime.of(2023, 6, 10, 12, 0), 30);
    Task task = new Task("Таск тест", "Описание таск теста", 3, 30,
            LocalDateTime.of(2023, 8, 10, 12, 0));


    @Test
    void createTask() {
        manager.createTask(task);
        final Task savedTask = manager.getByIdTask(task.getId());
        assertNotNull(savedTask, "Задача не найдена");
        assertEquals(task, savedTask, "Разные задачи");
        final Map<Integer, Task> tasks = manager.getTaskList();
        assertEquals(1, tasks.size(), "Не верное количество задач");
        assertNotNull(tasks, "Лист пустой");
        assertEquals(task, tasks.get(1), "Разные задачи");
    }

    @Test
    void createEpic() {
        manager.createEpic(epicTest);
        manager.createSubTask(subTaskTest);
        final Epic savedEpic = manager.getByIdEpic(epicTest.getId());
        assertNotNull(savedEpic, "Задача не найдена");
        final Map<Integer, Epic> epics = manager.getEpicsList();
        assertNotNull(epics, "Задача не найдена");
        assertEquals(1, epics.size(), "Неверное количество задач");
    }

    @Test
    void removeTaskId() {
        manager.createTask(task);
        Map<Integer, Task> tasks = manager.getTaskList();
        assertEquals(1, tasks.size());
        manager.removeTaskId(task.getId(), TypeTask.TASK);
        assertEquals(0, tasks.size(), "Задача существует");
    }

    @Test
    void removeSubTaskId() {
        manager.createEpic(epicTest);
        manager.createSubTask(subTaskTest);
        Map<Integer, Subtask> subtasks = manager.getSubtasksList();
        manager.removeTaskId(subTaskTest.getId(), TypeTask.SUBTASK);
        assertEquals(0, subtasks.size(), "Сaб Таск существует");
    }

    @Test
    void removeEpicId() {
        manager.createEpic(epicTest);
        manager.getByIdEpic(epicTest.getId());
        manager.createSubTask(subTaskTest);
        Map<Integer, Epic> epics = manager.getEpicsList();
        manager.removeTaskId(epicTest.getId(), TypeTask.EPIC);
        assertEquals(0, epics.size(), "Эпик существует");
    }

    @Test
    void updateTasks() {
        manager.createTask(task);
        Map<Integer, Task> tasks = manager.getTaskList();
        assertEquals(task, tasks.get(task.getId()));
        task.setStatus(Status.DONE);
        manager.updateTask(task.getId(), task);
        assertEquals(task, tasks.get(task.getId()), "Неверная задача");
    }

    @Test
    void updateSubtasks() {
        manager.createEpic(epicTest);
        manager.createSubTask(subTaskTest);
        assertEquals(subTaskTest, manager.getByIdSubTask(subTaskTest.getId()), "Не верное количество задач");
        subTaskTest.setStatus(Status.DONE);
        manager.updateTask(subTaskTest.getId(), subTaskTest);
        assertEquals(subTaskTest, manager.getByIdSubTask(subTaskTest.getId()), "Не верное количество задач");
    }

}