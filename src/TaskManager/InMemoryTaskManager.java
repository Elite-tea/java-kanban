package TaskManager;

import Exception.ValidationException;
import HistoryManager.HistoryManager;
import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0; // счетчик задач всего.
    protected static final Map<Integer, Task> tasks = new HashMap<>();
    protected static final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected static final Map<Integer, Epic> epic = new HashMap<>();
    protected static  HistoryManager historyManager;
    protected final Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime,
            Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(Task::getId));

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
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
    public boolean removeAllTask(TypeTask type) { // Удалить все задачи. Если нет данных = null, не найден тип = false
        historyManager.allRemove();
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
        historyManager.add(tasks.get(id));
        return tasks.get(id);
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
    public boolean createTask(Task newTask) { // Создаем задачу типа Task
        if (newTask.getId() == 0) {
            id++; // Тут хранится последний использованный id
            newTask.setId(id);
        }
        newTask.setStatus(Status.NEW);
        validation(newTask);
        tasks.put(newTask.getId(), newTask);
        prioritizedTasks.add(newTask);
        return true;
    }

    @Override
    public boolean createEpic(Epic newEpic) { // Создаем задачу типа Tasks.Epic
        if (newEpic.getId() == 0) {
            id++;
            newEpic.setId(id);
        }
        newEpic.setStatus(Status.NEW);
        epic.put(newEpic.getId(), newEpic);
        findTimeOfEpic(newEpic);
        return true;
    }

    @Override
    public boolean createSubTask(Subtask subtask) { // Создаем задачу типа SubTask
        validation(subtask);
        if (subtask.getId() == 0) {
            id++;
            subtask.setId(id);
        } else {
            subtask.setStatus(Status.NEW);
            subtasks.put(subtask.getId(), subtask);
            findTimeOfEpic(epic.get(subtask.getIdEpic()));
            prioritizedTasks.add(subtask);
        }
        return true;
    }

    @Override
    public boolean updateTask(Integer id, Task task) { // Обновление задачи типа Tasks.Task по id
        if (tasks.get(id) == null || tasks.get(id).getId() == 0) { // Проверка на существование. Задачи нет, вернем false.
            return false;
        } else {
            validation(task);
            prioritizedTasks.remove(tasks.get(task.getId()));
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
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
            prioritizedTasks.remove(tasks.get(subtask.getId()));
            subtasks.put(id, subtask);
            prioritizedTasks.add(subtask);
            checkStatus(id);
            return true;
        }
    }

    @Override
    public boolean removeTaskId(Integer id, TypeTask type) { // Удаляем по идентификатору
        if (tasks.get(id) == null && epic.get(id) == null && subtasks.get(id) == null) { // Проверяем на существование
            return false;
        } else {
            historyManager.remove(id);
            switch (type) {
                case TASK:
                    Task newTask = tasks.get(id);
                    tasks.remove(id);
                    prioritizedTasks.remove(newTask);
                    break;
                case EPIC:
                    Epic newEpic = epic.get(id);
                    ArrayList<Integer> idSub;
                    idSub = (epic.get(id).getIdSubtasks());
                    for (Integer integer : idSub) {
                        subtasks.remove(integer);
                        prioritizedTasks.remove(newEpic);
                    }
                    prioritizedTasks.remove(newEpic);
                    epic.remove(id);
                    break;
                case SUBTASK:
                    Subtask newSubtask = subtasks.get(id);
                    subtasks.remove(id);
                    prioritizedTasks.remove(newSubtask);
                    break;
                default:
                    return false;
            }
        }
        return true;
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

    @Override
    public void findTimeOfEpic(Epic epic) {
        List<Integer> subTaskIds = epic.getIdSubtasks();

        int duration = epic.getDuration();
        LocalDateTime startTime = epic.getStartTime();
        LocalDateTime endTime = epic.getEndTime();

        for (int id : subTaskIds) {
            Subtask subTask = subtasks.get(id);
            if (subTask.getStartTime() != null) {
                if (startTime == null || startTime.isAfter(subTask.getStartTime())) {
                    startTime = subTask.getStartTime();
                }
            }

            if (subTask.getEndTime() != null) {
                if (endTime == null || endTime.isBefore(subTask.getEndTime())) {
                    endTime = subTask.getEndTime();
                }
            }

            duration += subTask.getDuration();
        }
        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
        epic.setDuration(duration);

    }

    @Override
    public Map<Integer, Subtask> getSubtasksList() {
        return subtasks;
    }

    @Override
    public Map<Integer, Epic> getEpicsList() {
        return epic;
    }

    @Override
    public Map<Integer, Task> getTaskList() {
        return tasks;
    }

    public void setPrioritizedTasks(Task task) {
        prioritizedTasks.add(task);
    }

    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    private void validation(Task task) {
        for (Task prioritetTask : prioritizedTasks) {
            if (Objects.equals(task.getId(), prioritetTask.getId())) {
                continue;
            }
            if (prioritetTask.getStartTime() == null || task.getStartTime() == null) {
                break;
            }
            if (!task.getEndTime().isBefore(prioritetTask.getStartTime()) &&
                    !task.getEndTime().equals(prioritetTask.getStartTime())) {
                prioritetTask.getEndTime();
            } else {
                throw new ValidationException(task + " Ошибка: " + prioritetTask);
            }
        }
    }

    private void checkStatus(Integer id) {
        ArrayList<Integer> idTheSubEpic; // Лист с id суб задач эпика.
        int idEpicSubTasks; // id эпика

        Subtask dataEpic = subtasks.get(id);  // Получаем по id эпика
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
            }
        }
    }
}