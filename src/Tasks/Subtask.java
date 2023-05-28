package Tasks;

import TaskManager.TypeTask;

public class Subtask extends Task {
    private final int idEpic; // Id главной задачи, возможно пригодится для реализации проверки принадлежности к главному эпику.

    public Subtask(String name, String detail, int idEpic, int id) {
        super(name, detail, id);
        this.idEpic = idEpic;
        this.type = TypeTask.SUBTASK;
    }

    public int getIdEpic() {
        return idEpic;
    }

    public TypeTask getType() {
        return TypeTask.SUBTASK;
    }

    @Override
    public String toString() { // Переопределение для корректной записи в файл.
        return getId() + "," + getType() + "," + getName() + ","
                + getStatus() + "," +  getDetail() + "," + getIdEpic() + "\n";
    }
}