package Tests;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import HistoryManager.HistoryManager;
import HistoryManager.InMemoryHistoryManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HistoryManagerTest {
    HistoryManager historyManager = new InMemoryHistoryManager();
    Task task;
    Epic epic;
    Subtask subTask;
    int id = 0;

    @BeforeEach
    void beforeEach() {

        task = new Task("Таск тест", "Описание таск теста", id, 30,
                LocalDateTime.of(2023, 6, 19, 12, 0));
        id++;
        task.setId(1);
        epic = new Epic("Эпик тест", "Описание эпиктеста", id);
        id++;
        epic.setId(2);
        subTask = new Subtask("Сабтаск тест", "Описание сабтакса", id, 2,
                LocalDateTime.of(2023, 6, 19, 11, 0), 30);
        id++;
        subTask.setId(3);
    }

    @Test
    void create() {
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая.");
        assertEquals(1, history.size(), "История пустая");
    }

    @Test
    void createDoubleTask() {
        historyManager.add(task);
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая");
        assertEquals(1, history.size(), "Задачи равны");
    }

    @Test
    void removeHead() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subTask);
        historyManager.remove(task.getId());
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая");
        assertEquals(2, history.size(), "Задачи равны");
    }

    @Test
    void removeMiddle() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subTask);
        historyManager.remove(epic.getId());
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая");
        assertEquals(2, history.size(), "Задачи равны");
    }

    @Test
    void removeTail() {

        historyManager.add(epic);
        historyManager.add(subTask);
        historyManager.add(task);
        historyManager.remove(task.getId());
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая");
        assertEquals(2, history.size(), "Задачи равны");
    }
}