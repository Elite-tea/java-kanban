import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    protected static int id = 1; // счетчик задач всего.
    static HashMap<Integer, Task> tasks = new HashMap<>();
    static HashMap<Integer, Subtask> subtasks = new HashMap<>();
    static HashMap<Integer, Epic> epic = new HashMap<>();

    public static HashMap getAllTasks(String type) { // Получение всех задач
        switch (type) {
            case "task":
                HashMap<Integer,Task> taskList = new HashMap<>();
                for (Integer i : tasks.keySet()) { // Получаем задачи типа task
                    taskList.put(i, tasks.get(i));

                }
                return taskList;

            case "epic":
                HashMap<Integer,Epic> epicList = new HashMap<>();
                for (Integer i : epic.keySet()) { // Получаем задачи типа epic
                    epicList.put(i, epic.get(i));
                }
                return epicList;

            case "subtask":
                HashMap<Integer,Subtask> subtaskList = new HashMap<>();
                for (Integer i : subtasks.keySet()) { // Получаем задачи типа subtask
                    subtaskList.put(i, subtasks.get(i));
                }
                return subtaskList;
            default:
                return new HashMap<>();
        }
    }

    public static boolean revoteAllTask(int type) { // Удалить все задачи. Если нет данных = null, не найден тип = false
        switch (type) {
            case 1:
                tasks.clear();
                return true;
            case 2:
                epic.clear();
                subtasks.clear();
                return true;
            case 3:
                subtasks.clear();
                return true;
            default:
                return false;
        }
    }

    public static Task getByIdTask(Integer id) { // Получить задачу по идентификатору

        return tasks.get(id);
    }
    public static Subtask getByIdSubTask(Integer id) { // Получить субзадачу  по идентификатору

        return subtasks.get(id);
    }
    public static Epic getByIdEpic(Integer id) { // Получить эпик по идентификатору

        return epic.get(id);
    }


    public static boolean createTask(Task newTask ) { // Создаем задачу типа Task
        tasks.put(id, newTask);
        id++;
            return true;
        }

    public static boolean createEpic(Epic newEpic) { // Создаем задачу типа Epic
        epic.put(id, newEpic);
        id++;
            return true;
    }

    public static boolean createSubTask(Subtask subtask) { // Создаем задачу типа SubTask
        subtasks.put(id, subtask);
        id++;
            return true;
    }

    public static boolean updateTask(Integer id, Task task) { // Обновление задачи типа Task по id
            if (tasks.get(id) == null || tasks.get(id).id == 0) { /* Проверка на существование.
                                                                        Если задачи не существует, вернем false. */
                return false;
            } else {
                tasks.put(id, task);
                return true;
            }
    }

    public static boolean updateEpic(Integer id, Epic dataEpic) { // Обновление задачи типа Epic по идентификатору
        if (epic.get(id).id == 0 || epic.get(id) == null) { /* Проверка на существование.
                                                                        Если задачи не существует, вернем false. */
            return false;
        } else {
            epic.put(id, dataEpic);
            return true;
        }
    }

    public static boolean updateSubTask(Integer id, Subtask subtask) { // Обновление задачи типа SubTask по id
        if (subtasks.get(id) == null || subtasks.get(id).id == 0) { /* Проверка на существование.
                                                                        Если задачи не существует, вернем false. */
            return false;
        } else {
            subtasks.put(id, subtask);
            ArrayList<Integer> idTheSubEpic; // Лист с id субзадач эпика.
            int idEpicSubTasks; // id эпика

            Subtask dataEpic = subtasks.get(id);  // Получаем id эпика
            idEpicSubTasks = dataEpic.idEpic;


            idTheSubEpic = epic.get(idEpicSubTasks).idSubtasks; // Получаем по id эпика список всех субзадач.

            for (Integer integer : idTheSubEpic) { // Идем по всем субзадачам и проверяем их статус,

                if (subtasks.get(integer) != null) {

                    if (dataEpic.getStatus().equals("Done")) {
                        epic.get(idEpicSubTasks).setStatus("Done");
                    }

                    if (dataEpic.getStatus().equals("IN_PROGRESS")) {
                        epic.get(idEpicSubTasks).setStatus("N_PROGRESS");
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
    }


    public static boolean removeTaskId(Integer id, String type) { // Удаляем по идентификатору
        if (tasks.get(id) == null && epic.get(id) == null && subtasks.get(id) == null) { // Проверяем на существование
            return false;
        } else {
            switch (type) {
                case "task":
                 tasks.remove(id);
                case "epic":
                    ArrayList<Integer> idSub;
                    idSub = (epic.get(id).idSubtasks);
                    for (Integer integer : idSub) {
                        subtasks.remove(integer);
                    }
                    epic.remove(id);
                case "subtask":
                    subtasks.remove(id);
                default:
                return false;
            }
        }
    }

    public static String getListSubtaskEpic(Integer id) { // Получение списка всех подзадач определённого эпика.
        Epic dataList = epic.get(id); // Лист для хранения данных конкретного эпика
        ArrayList<Integer> data; // Лист для хранения списка id субзадач конкретного эпика.
        if (epic.get(id) == null) {
            return "Error, id not found";
        } else {
                data = dataList.idSubtasks; // Достаем данные id для эпика

            if (data.size() != 0) {
                ArrayList<Subtask> taskList = new ArrayList<>();
                for (Integer datum : data) {
                    taskList.add(subtasks.get(datum)); // Возвращаем данные каждой подзадачи
                }
                return taskList.toString(); // Сообщаем о выполненой задаче(Список подзадач передан)
            } else {
                return "Error, the list is empty"; // Возвращаем сообщение об ошибке.
            }

        }
    }

}
