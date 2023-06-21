package Tests;

import TaskManager.FileBackedTasksManager;
import TaskManager.TypeTask;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTasksManagerTest extends TaskManagersTest<FileBackedTasksManager> {
    public static final Path path = Path.of("src/resources/base.csv");

    File file = new File(String.valueOf(path));
    ArrayList<Integer> idSubtasks = new ArrayList<>();
    Epic epicTest = new Epic("ЭпикТест", "Эпик тест", idSubtasks, 1, LocalDateTime.of(2023, 7, 10, 12, 0), 30);
    Subtask subTaskTest = new Subtask("СабТаскЭпикТест", "Саб таск эпик тест", 1, 2, LocalDateTime.of(2023, 6, 10, 12, 0), 30);
    Task task = new Task("Таск тест", "Описание таск теста", 3, 30, LocalDateTime.of(2023, 8, 10, 12, 0));

    @BeforeEach
    void beforeEach() {
        manager = new FileBackedTasksManager(file);
    }

    @AfterEach
    public void afterEach() {
        try {
            Files.delete(path);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void CorrectlySaveAndLoad() {
        manager.createTask(task);
        manager.createEpic(epicTest);
        manager.loadFromFile();
        assertEquals(List.of(task), manager.getAllTasks(TypeTask.TASK));
        assertEquals(List.of(epicTest), manager.getAllTasks(TypeTask.EPIC));
    }

    @Test
    public void SaveAndLoadEmptyTasksEpicsSubtasks() {
        FileBackedTasksManager fileManager = new FileBackedTasksManager(file);
        fileManager.save();
        fileManager.loadFromFile();
        assertEquals(Collections.EMPTY_LIST, manager.getAllTasks(TypeTask.TASK));
        assertEquals(Collections.EMPTY_LIST, manager.getAllTasks(TypeTask.EPIC));
        assertEquals(Collections.EMPTY_LIST, manager.getAllTasks(TypeTask.SUBTASK));
    }

    @Test
    public void SaveAndLoadEmptyHistory() {
        FileBackedTasksManager fileManager = new FileBackedTasksManager(file);
        fileManager.save();
        fileManager.loadFromFile();
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }
}