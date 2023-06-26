package TaskManager;

import HistoryManager.HistoryManager;
import HttpServer.KVTaskClient;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import com.google.gson.*;

import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {

    private final static String KEY_TASKS = "tasks";
    private final static String KEY_SUBTASKS = "subtasks";
    private final static String KEY_EPICS = "epics";
    private final static String KEY_HISTORY = "history";
    private final KVTaskClient client;
    private static final Gson gson = new GsonBuilder().create();

    public HttpTaskManager(HistoryManager historyManager, String path, Boolean startLoad) {
        super(historyManager);
        client = new KVTaskClient(path);
        if (startLoad) {
            load();
        }
    }

    public HttpTaskManager(HistoryManager historyManager, String path) {
        this(historyManager, path, false);
    }

    private void load() {
        JsonElement jsonTasks = JsonParser.parseString(client.load(KEY_TASKS));
        if (!jsonTasks.isJsonNull()) {
            JsonArray jsonTasksArray = jsonTasks.getAsJsonArray();
            for (JsonElement jsonTask : jsonTasksArray) {
                Task task = gson.fromJson(jsonTask, Task.class);
                tasks.put(task.getId(), task);
                setPrioritizedTasks(task);
            }
        }

        JsonElement jsonEpics = JsonParser.parseString(client.load(KEY_EPICS));
        if (!jsonEpics.isJsonNull()) {
            JsonArray jsonEpicsArray = jsonEpics.getAsJsonArray();
            for (JsonElement jsonEpic : jsonEpicsArray) {
                Epic task = gson.fromJson(jsonEpic, Epic.class);
                epic.put(task.getId(), task);
            }
        }

        JsonElement jsonSubtasks = JsonParser.parseString(client.load(KEY_SUBTASKS));
        if (!jsonSubtasks.isJsonNull()) {
            JsonArray jsonSubtasksArray = jsonSubtasks.getAsJsonArray();
            for (JsonElement jsonSubtask : jsonSubtasksArray) {
                Subtask task = gson.fromJson(jsonSubtask, Subtask.class);
                subtasks.put(task.getId(), task);
                setPrioritizedTasks(task);
            }
        }

        JsonElement jsonHistoryList = JsonParser.parseString(client.load(KEY_HISTORY));
        if (!jsonHistoryList.isJsonNull()) {
            JsonArray jsonHistoryArray = jsonHistoryList.getAsJsonArray();
            for (JsonElement jsonTaskId : jsonHistoryArray) {
                int taskId = jsonTaskId.getAsInt();
                if (this.subtasks.containsKey(taskId)) {
                    this.getByIdSubTask(taskId);
                } else if (this.epic.containsKey(taskId)) {
                    this.getByIdEpic(taskId);
                } else if (this.tasks.containsKey(taskId)) {
                    this.getByIdTask(taskId);
                }
            }
        }
    }

    public Integer findMaxId() {
        Integer newId = 0;
        for (Integer newTaskId : tasks.keySet()) {
            if (newTaskId > newId) {
                newId = newTaskId;
            }
        }
        for (Integer newEpicId : epic.keySet()) {
            if (newEpicId > newId) {
                newId = newEpicId;
            }
        }
        for (Integer newSubtaskId : subtasks.keySet()) {
            if (newSubtaskId > newId) {
                newId = newSubtaskId;
            }
        }
        return newId;
    }

    @Override
    public void save() {
        client.put(KEY_TASKS, gson.toJson(tasks.values()));
        client.put(KEY_SUBTASKS, gson.toJson(subtasks.values()));
        client.put(KEY_EPICS, gson.toJson(epic.values()));
        client.put(KEY_HISTORY, gson.toJson(this.getHistory().stream().map(Task::getId).collect(Collectors.toList())));
    }
}
