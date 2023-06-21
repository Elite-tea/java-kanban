package Tests;

import TaskManager.InMemoryTaskManager;
import TaskManager.TypeTask;
import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class EpicTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    ArrayList<Integer> idSubtasks = new ArrayList<>();
    Epic epicTest = new Epic("ЭпикТест", "Эпик тест", idSubtasks, 1, LocalDateTime.of(2023, 7, 10, 12, 0), 30);
    Subtask subTaskTest = new Subtask("СабТаскЭпикТест", "Саб таск эпик тест", 1, 2, LocalDateTime.of(2023, 6, 10, 12, 0), 30);
    Task task = new Task("Таск тест", "Описание таск теста", 3, 30, LocalDateTime.of(2023, 8, 10, 12, 0));


    @Test
    void createEpic() {
        inMemoryTaskManager.createEpic(epicTest);
        assertEquals(epicTest.getStatus(), Status.NEW, "Не верный статус эпика(createEpic)");
    }

    @Test
    void createEpicFromSubTasks() {
        inMemoryTaskManager.createEpic(epicTest);
        inMemoryTaskManager.createSubTask(subTaskTest);
        assertNotNull(inMemoryTaskManager.getByIdSubTask(subTaskTest.getId()), "Пустая задача(createEpicFromSubTasks)");
        assertEquals(inMemoryTaskManager.getAllTasks(TypeTask.SUBTASK).size(), 1, "Не верное количество " + "сабтасков(createEpicFromSubTasks)");
        assertEquals(subTaskTest.getIdEpic(), epicTest.getId(), "Id эпика и подзадач разные(createEpicFromSubTasks)");
        assertEquals(epicTest.getStatus(), Status.NEW, "Статус эпика не обновляется(createEpicFromSubTasks)");
    }

    @Test
    void createEpicFromSubTasksStatusDone() {
        inMemoryTaskManager.createEpic(epicTest);
        inMemoryTaskManager.createSubTask(subTaskTest);
        epicTest.setStatus(Status.DONE);
        inMemoryTaskManager.updateEpic(epicTest.getId(), epicTest);
        assertNotNull(inMemoryTaskManager.getByIdSubTask(subTaskTest.getId()), "Пустая задача(createEpicFromSubTasksStatusDone)");
        assertEquals(inMemoryTaskManager.getAllTasks(TypeTask.SUBTASK).size(), 1, "Не верное количество " + "сабтасков(createEpicFromSubTasksStatusDone)");
        assertEquals(subTaskTest.getIdEpic(), epicTest.getId(), "Id эпика и подзадач разные(createEpicFromSubTasksStatusDone)");
        assertEquals(epicTest.getStatus(), Status.DONE, "Статус эпика не обновляется(createEpicFromSubTasksStatusDone)");
    }

    @Test
    void createEpicFromSubTasksStatusNewAndDone() {
        inMemoryTaskManager.createEpic(epicTest);
        inMemoryTaskManager.createSubTask(subTaskTest);
        epicTest.setStatus(Status.DONE);
        inMemoryTaskManager.updateEpic(epicTest.getId(), epicTest);
        assertNotNull(inMemoryTaskManager.getByIdSubTask(subTaskTest.getId()), "Пустая задача(createEpicFromSubTasksStatusNewAndDone)");
        assertEquals(inMemoryTaskManager.getAllTasks(TypeTask.SUBTASK).size(), 1, "Не верное количество " + "сабтасков(createEpicFromSubTasksStatusNewAndDone)");
        assertEquals(subTaskTest.getIdEpic(), epicTest.getId(), "Id эпика и подзадач разные(createEpicFromSubTasksStatusNewAndDone)");
        assertEquals(epicTest.getStatus(), Status.DONE, "Статус эпика не обновляется(createEpicFromSubTasksStatusNewAndDone)");
    }

    @Test
    void createEpicFromSubTasksStatusInProgress() {
        inMemoryTaskManager.createEpic(epicTest);
        inMemoryTaskManager.createSubTask(subTaskTest);
        epicTest.setStatus(Status.IN_PROGRESS);
        inMemoryTaskManager.updateEpic(epicTest.getId(), epicTest);
        assertNotNull(inMemoryTaskManager.getByIdSubTask(subTaskTest.getId()), "Пустая задача(createEpicFromSubTasksStatusInProgress)");
        assertEquals(inMemoryTaskManager.getAllTasks(TypeTask.SUBTASK).size(), 1, "Не верное количество " + "сабтасков(createEpicFromSubTasksStatusInProgress)");
        assertEquals(subTaskTest.getIdEpic(), epicTest.getId(), "Id эпика и подзадач разные(createEpicFromSubTasksStatusInProgress)");
        assertEquals(epicTest.getStatus(), Status.IN_PROGRESS, "Статус эпика не обновляется(createEpicFromSubTasksStatusInProgress)");
    }


}