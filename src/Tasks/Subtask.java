package Tasks;

import TaskManager.TypeTask;

import java.time.LocalDateTime;

public class Subtask extends Task {
    // Id главной задачи, возможно пригодится для реализации проверки принадлежности к главному эпику.
    private final int idEpic;

    public Subtask(String name, String detail, int idEpic, int id, LocalDateTime startTime, int duration) {
        super(name, detail, id, duration,  startTime);
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
                + getStatus() + "," +  getDetail() + "," + getIdEpic() + getStartTime().format(getFormatter()) + "," +
                this.getEndTime().format(getFormatter()) + ","
                + getDuration() + "," + idEpic + "\n";
    }
}