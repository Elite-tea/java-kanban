package TaskManager;

import HistoryManager.HistoryManager;
import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private int id = 0; // счетчик задач всего.

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epic = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public List<Task> getAllTasks(TypeTask type) { // Получение всех задач
        switch (type) {
            case TASK:
                return new ArrayList<>(tasks.values());

            case EPIC:
                return new ArrayList<>(epic.values());

            case SUBTASK:
                return new ArrayList<>(subtasks.values());
            default:
                return new ArrayList<>();
        }
    }


    @Override
    public boolean remoteAllTask(TypeTask type) { // Удалить все задачи. Если нет данных = null, не найден тип = false
        switch (type) {
            case TASK:
                tasks.clear();
                return true;
            case EPIC:
                epic.clear();
                subtasks.clear();
                return true;
            case SUBTASK:
                subtasks.clear();
                return true;
            default:
                return false;
        }
    }

    @Override
    public Task getByIdTask(Integer id) { // Получить задачу по идентификатору
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Subtask getByIdSubTask(Integer id) { // Получить суб задачу по идентификатору
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Epic getByIdEpic(Integer id) { // Получить эпик по идентификатору
        historyManager.add(epic.get(id));
        return epic.get(id);
    }

    @Override
    public boolean createTask(Task newTask) { // Создаем задачу типа Tasks.Task
        id++; // Тут хранится последний использованный id
        newTask.setStatus(Status.NEW);
        newTask.setId(id);
        tasks.put(id, newTask);
        return true;
    }

    @Override
    public boolean createEpic(Epic newEpic) { // Создаем задачу типа Tasks.Epic
        id++;
        newEpic.setStatus(Status.NEW);
        newEpic.setId(id);
        epic.put(id, newEpic);
        return true;
    }

    @Override
    public boolean createSubTask(Subtask subtask) { // Создаем задачу типа SubTask
        id++;
        subtask.setStatus(Status.NEW);
        subtask.setId(id);
        subtasks.put(id, subtask);
        return true;
    }

    @Override
    public boolean updateTask(Integer id, Task task) { // Обновление задачи типа Tasks.Task по id
        if (tasks.get(id) == null || tasks.get(id).getId() == 0) { // Проверка на существование. Задачи нет, вернем false.
            return false;
        } else {
            tasks.put(id, task);
            return true;
        }
    }

    @Override
    public boolean updateEpic(Integer id, Epic dataEpic) { // Обновление задачи типа Tasks. Tasks. Epic по идентификатору
        /* Проверка на существование. Если задачи не существует, вернем false. */
        if (epic.get(id).getId() == 0 || epic.get(id) == null) {
            return false;
        } else {
            epic.put(id, dataEpic);
            return true;
        }
    }

    @Override
    public boolean updateSubTask(Integer id, Subtask subtask) { // Обновление задачи типа SubTask по id
        if (subtasks.get(id) == null || subtasks.get(id).getId() == 0) {
            return false;
        } else {
            subtasks.put(id, subtask);
            checkStatus(id);
            return true;
        }
    }

    @Override
    public boolean checkStatus(Integer id) {
        ArrayList<Integer> idTheSubEpic; // Лист с id суб задач эпика.
        int idEpicSubTasks; // id эпика

        Subtask dataEpic = subtasks.get(id);  // Получаем id эпика
        idEpicSubTasks = dataEpic.getIdEpic();
        idTheSubEpic = epic.get(idEpicSubTasks).getIdSubtasks(); // Получаем по id эпика список всех суб задач.
        for (Integer integer : idTheSubEpic) { // Идем по всем суб задачам и проверяем их статус,

            if (subtasks.get(integer) != null) {
                int done = 0;
                int progress = 0;

                if (dataEpic.getStatus() == Status.DONE) {
                    done++;
                }

                if (dataEpic.getStatus() == Status.IN_PROGRESS) {
                    progress++;
                }

                if (done > 0) {
                    epic.get(idEpicSubTasks).setStatus(Status.DONE);
                } else if (progress > 0) {
                    epic.get(idEpicSubTasks).setStatus(Status.IN_PROGRESS);
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean removeTaskId(Integer id, TypeTask type) { // Удаляем по идентификатору
        if (tasks.get(id) == null && epic.get(id) == null && subtasks.get(id) == null) { // Проверяем на существование
            return false;
        } else {
            switch (type) {
                case TASK:
                    tasks.remove(id);
                case EPIC:
                    ArrayList<Integer> idSub;
                    idSub = (epic.get(id).getIdSubtasks());
                    for (Integer integer : idSub) {
                        subtasks.remove(integer);
                    }
                    epic.remove(id);
                case SUBTASK:
                    subtasks.remove(id);
                    checkStatus(id);
                default:
                    return false;
            }
        }
    }

    @Override
    public List<Subtask> getListSubtaskEpic(Integer id) { // Получение списка всех подзадач определённого эпика.
        Epic dataList = epic.get(id); // Лист для хранения данных конкретного эпика
        ArrayList<Integer> data; // Лист для хранения списка id суб задач конкретного эпика.
        if (epic.get(id) == null) {
            return new ArrayList<>(); // Возвращаем пустой лист при ошибке.
        } else {
            data = dataList.getIdSubtasks(); // Достаем данные id для эпика

            if (data.size() != 0) {
                ArrayList<Subtask> taskList = new ArrayList<>();
                for (Integer datum : data) {
                    taskList.add(subtasks.get(datum)); // Возвращаем данные каждой подзадачи
                }
                return taskList; // Сообщаем о выполнении задачи(Список подзадач передан)
            } else {
                return new ArrayList<>(); // Возвращаем пустой лист при ошибке.
            }

        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}