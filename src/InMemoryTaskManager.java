import java.util.ArrayList;
import java.util.HashMap;

class InMemoryTaskManager implements TaskManager {
    protected int id = 0; // счетчик задач всего.

    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected HashMap<Integer, Epic> epic = new HashMap<>();
    InMemoryHistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();


    public ArrayList getAllTasks(TypeTask type) { // Получение всех задач
        switch (type) {
            case TASK:
                ArrayList<Task> taskList = new ArrayList<>();
                for (Integer i : tasks.keySet()) { // Получаем задачи типа task
                    taskList.add(tasks.get(i));

                }
                return taskList;

            case EPIC:
                ArrayList<Epic> epicList = new ArrayList<>();
                for (Integer i : epic.keySet()) { // Получаем задачи типа epic
                    epicList.add(epic.get(i));
                }
                return epicList;

            case SUBTASK:
                ArrayList<Subtask> subtaskList = new ArrayList<>();
                for (Integer i : subtasks.keySet()) { // Получаем задачи типа subtask
                    subtaskList.add(subtasks.get(i));
                }
                return subtaskList;
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public boolean revoteAllTask(TypeTask type) { // Удалить все задачи. Если нет данных = null, не найден тип = false
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
        inMemoryHistoryManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Subtask getByIdSubTask(Integer id) { // Получить суб задачу по идентификатору
        inMemoryHistoryManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Epic getByIdEpic(Integer id) { // Получить эпик по идентификатору
        inMemoryHistoryManager.add(epic.get(id));
        return epic.get(id);
    }

    @Override
    public boolean createTask(Task newTask) { // Создаем задачу типа Task
        id++; // Теперь хранится последний использованный id а не свободный. Была идея инкрементировать id в параметре,
        // но это плохой тон, как сказал мой наставник
        newTask.setStatus(Status.NEW);
        newTask.setId(id);
        tasks.put(id, newTask);
        return true;
    }

    @Override
    public boolean createEpic(Epic newEpic) { // Создаем задачу типа Epic
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
    public boolean updateTask(Integer id, Task task) { // Обновление задачи типа Task по id
        if (tasks.get(id) == null || tasks.get(id).id == 0) { /* Проверка на существование.
                                                                        Если задачи не существует, вернем false. */
            return false;
        } else {
            tasks.put(id, task);
            return true;
        }
    }

    @Override
    public boolean updateEpic(Integer id, Epic dataEpic) { // Обновление задачи типа Tasks. Epic по идентификатору
        if (epic.get(id).id == 0 || epic.get(id) == null) { /* Проверка на существование.
                                                                        Если задачи не существует, вернем false. */
            return false;
        } else {
            epic.put(id, dataEpic);
            return true;
        }
    }

    @Override
    public boolean updateSubTask(Integer id, Subtask subtask) { // Обновление задачи типа SubTask по id
        if (subtasks.get(id) == null || subtasks.get(id).id == 0) { /* Проверка на существование.
                                                                        Если задачи не существует, вернем false. */
            return false;
        } else {
            subtasks.put(id, subtask);
            ArrayList<Integer> idTheSubEpic; // Лист с id суб задач эпика.
            int idEpicSubTasks; // id эпика

            Subtask dataEpic = subtasks.get(id);  // Получаем id эпика
            idEpicSubTasks = dataEpic.idEpic;


            idTheSubEpic = epic.get(idEpicSubTasks).idSubtasks; // Получаем по id эпика список всех суб задач.

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
                    idSub = (epic.get(id).idSubtasks);
                    for (Integer integer : idSub) {
                        subtasks.remove(integer);
                    }
                    epic.remove(id);
                case SUBTASK:
                    subtasks.remove(id);
                default:
                    return false;
            }
        }
    }

    @Override
    public ArrayList<Subtask> getListSubtaskEpic(Integer id) { // Получение списка всех подзадач определённого эпика.
        Epic dataList = epic.get(id); // Лист для хранения данных конкретного эпика
        ArrayList<Integer> data; // Лист для хранения списка id суб задач конкретного эпика.
        if (epic.get(id) == null) {
            return new ArrayList<>(); // Возвращаем пустой лист при ошибке.
        } else {
            data = dataList.idSubtasks; // Достаем данные id для эпика

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

}

enum Status {

    NEW,
    IN_PROGRESS,
    DONE
}

enum TypeTask {

    TASK,
    EPIC,
    SUBTASK
}