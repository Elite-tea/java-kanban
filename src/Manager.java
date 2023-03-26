import java.util.ArrayList;

public class Manager extends Main {
   private static int id = 0; // счетчик задач всего.

    public static String getAllTasks(int type) { // Получение всех задач
        switch (type) {
            case 1:
                ArrayList<Task> taskList = new ArrayList<>();
                for (Integer i : tasks.keySet()) { // Получаем задачи типа task
                    taskList.addAll(tasks.get(i));
                }
                return taskList.toString();

            case 2:
                taskList = new ArrayList<>();
                for (Integer i : epic.keySet()) { // Получаем задачи типа epic
                    taskList.addAll(epic.get(i));
                }
                return taskList.toString();

            case 3:
                taskList = new ArrayList<>();
                for (Integer i : subtasks.keySet()) { // Получаем задачи типа epic
                    taskList.addAll(subtasks.get(i));
                }
                return taskList.toString();
            default:
                return "Error, type not found";
        }
    }

    public static boolean revoteAllTask(int type) { // Удалить все задачи. Если нет данных = null, не найден тип = false
        switch (type) {
            case 1:
                tasks.clear();
                return true;
            case 2:
                epic.clear();
                return true;
            case 3:
                subtasks.clear();
                return true;
            default:
                return false;
        }
    }

    public static ArrayList<Task> getByIdTask(Integer id) { // Получить задачу по идентификатору
        return tasks.get(id);
    }

    public static ArrayList<Epic> getByIdEpic(Integer id) { // Получить эпик по идентификатору
        return epic.get(id);
    }

    public static ArrayList<Subtask> getByIdSubTask(Integer id) { // Получить субзадачу  по идентификатору
        return subtasks.get(id);
    }

    public static boolean createTask(ArrayList<Task> task) { // Создаем задачу типа Task
        tasks.put(id, task);
        if (tasks.get(id).size() != 0) { // проверка записи
            id++;
            return true;
        } else {
            return false;
        }
    }

    public static boolean createEpic(ArrayList<Epic> dataEpic) { // Создаем задачу типа Epic
        epic.put(id, dataEpic);
        if (epic.get(id).size() != 0) { // проверка записи
            id++;
            return true;
        } else {
            return false;
        }
    }

    public static boolean createSubTask(ArrayList<Subtask> subtask) { // Создаем задачу типа SubTask
        subtasks.put(id, subtask);
        if (subtasks.get(id).size() != 0) { // проверка записи
            id++;
            return true;
        } else {

            return false;
        }
    }

    public static boolean updateTask(Integer id, ArrayList<Task> task) { // Обновление задачи типа Task по id
            if (tasks.get(id) == null || tasks.get(id).size() == 0) { /* Проверка на существование.
                                                                        Если задачи не существует, вернем false. */
                return false;
            } else {
                tasks.put(id, task);
                return true;
            }
    }

    public static boolean updateEpic(Integer id, ArrayList<Epic> dataEpic) { // Обновление задачи типа Epic по идентификатору
        if (epic.get(id).size() == 0 || epic.get(id) == null) { /* Проверка на существование.
                                                                        Если задачи не существует, вернем false. */
            return false;
        } else {
            epic.put(id, dataEpic);
            return true;
        }
    }

    public static boolean updateSubTask(Integer id, ArrayList<Subtask> subtask) { // Обновление задачи типа SubTask по id
        if (subtasks.get(id).size() == 0 || subtasks.get(id) == null) { /* Проверка на существование.
                                                                        Если задачи не существует, вернем false. */
            return false;
        } else {
            subtasks.put(id, subtask);
            ArrayList<Integer> idTheSubEpic = new ArrayList<>(); // Лист с id субзадач эпика.
            int idEpicSubTasks = 0; // id эпика

            for (Subtask dataEpic : subtasks.get(id)) { // Получаем id эпика
                idEpicSubTasks = dataEpic.idEpic;
            }

            for (Epic dataEpic : epic.get(idEpicSubTasks)) { // Получаем по id эпика список всех субзадач.
                idTheSubEpic = dataEpic.idSubtasks;
            }
            for (Integer integer : idTheSubEpic) { // Идем по всем субзадачам и проверяем их статус,
                if (subtasks.get(integer) != null) {
                    for (Subtask subTask : subtasks.get(integer)) {

                        if (!subTask.getStatus().equals("New") && !subTask.getStatus().equals("IN_PROGRESS")) {
                            for (Epic dataEpic : epic.get(idEpicSubTasks)) {
                                dataEpic.setStatus("Done");
                            }
                        }
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
    }


    public static boolean removeTaskId(Integer id) { // Удаляем по идентификатору
        if (tasks.get(id) == null || epic.get(id) == null || subtasks.get(id) == null) {
            return false;
        } else {

            if (tasks.get(id).size() != 0) {
                tasks.remove(id);
                return true;
            }

            if (epic.get(id).size() != 0) {
                epic.remove(id);
                return true;
            }

            if (subtasks.get(id).size() != 0) {
                subtasks.remove(id);
                return true;
            } else {
                return false;
            }
        }
    }

    public static String getListSubtaskEpic(Integer id) { // Получение списка всех подзадач определённого эпика.
        ArrayList<Epic> dataList = epic.get(id); // Лист для хранения данных конкретного эпика
        ArrayList<Integer> data = new ArrayList<>(); // Лист для хранения списка id субзадач конкретного эпика.
        if (tasks.get(id) == null || epic.get(id) == null || subtasks.get(id) == null) {
            return "Error, id not found";
        } else {
            for (Epic dataEpic : dataList) {
                data = dataEpic.idSubtasks; // Достаем данные id для эпика
            }

            if (data.size() != 0) {
                ArrayList<Task> taskList = new ArrayList<>();
                for (Integer datum : data) {
                    taskList.addAll(subtasks.get(datum)); // Возвращаем данные каждой подзадачи
                }
                return taskList.toString(); // Сообщаем о выполненой задаче(Список подзадач передан)
            } else {
                return "Error, the list is empty"; // Возвращаем сообщение об ошибке.
            }

        }
    }

}

