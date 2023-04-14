import java.util.ArrayList;

interface TaskManager {

    ArrayList getAllTasks(TypeTask type);

    boolean revoteAllTask(int type);

    Task getByIdTask(Integer id);
    Subtask getByIdSubTask(Integer id);
    Epic getByIdEpic(Integer id);


    boolean createTask(Task newTask );

    boolean createEpic(Epic newEpic);

    boolean createSubTask(Subtask subtask);

    boolean updateTask(Integer id, Task task);

    boolean updateEpic(Integer id, Epic dataEpic);

    boolean updateSubTask(Integer id, Subtask subtask);


     boolean removeTaskId(Integer id, TypeTask type);

    ArrayList<Subtask> getListSubtaskEpic(Integer id);

}