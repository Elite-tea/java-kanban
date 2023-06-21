package TaskManager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TaskManager {

    boolean removeAllTask(TypeTask type);

    List<Task> getAllTasks(TypeTask type);

    Task getByIdTask(Integer id);

    Subtask getByIdSubTask(Integer id);

    Epic getByIdEpic(Integer id);

    boolean createTask(Task newTask);

    boolean createEpic(Epic newEpic);

    boolean createSubTask(Subtask subtask);

    boolean updateTask(Integer id, Task task);

    boolean updateEpic(Integer id, Epic dataEpic);

    boolean updateSubTask(Integer id, Subtask subtask);

    boolean removeTaskId(Integer id, TypeTask type); // Спасибо за хот_кей! Очень удобно быстро форматировать код

    void findTimeOfEpic(Epic epic);

    void setPrioritizedTasks(Task task);

    Set<Task> getPrioritizedTasks();

    List<Subtask> getListSubtaskEpic(Integer id);

    List<Task> getHistory();

    Map<Integer, Subtask> getSubtasksList();

    Map<Integer, Epic> getEpicsList();

    Map<Integer, Task> getTaskList();
}