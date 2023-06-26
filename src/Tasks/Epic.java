package Tasks;

import TaskManager.TypeTask;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    private ArrayList<Integer> idSubtasks = new ArrayList<>(); // Список id подзадач принадлежащих эпику.
    private LocalDateTime endTime;

    public Epic(String name, String detail, ArrayList<Integer> idSubtasks, int id, LocalDateTime startTime, int duration) {
        super(name, detail, id, duration, startTime);
        this.idSubtasks = idSubtasks;
        this.type = TypeTask.EPIC;
        this.setStartTime(getStartTime());
        this.endTime = getEndTime();
    }

    public Epic(String name, String detail, int id, LocalDateTime startTime, int duration) {
        super(name, detail, id, duration, startTime);
        this.idSubtasks = new ArrayList<>();
        this.type = TypeTask.EPIC;
        this.setStartTime(getStartTime());
        this.endTime = getEndTime();
    }

    public Epic(String name, String detail,int id) {

        super(name, detail, id, 0,  LocalDateTime.of(2023, 6,17,0,0));
    }

    public void setEndTime(LocalDateTime endTime){
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime(){
        return this.getStartTime().plusMinutes(getDuration());
    }

    public ArrayList<Integer> getIdSubtasks() {
        return idSubtasks;
    }

    public TypeTask getType() {
        return TypeTask.EPIC;
    }

    public void setIdSubtasks(int id) {
        idSubtasks.add(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(idSubtasks, epic.idSubtasks) && Objects.equals(endTime, epic.endTime);
    }

    @Override
    public String toString() { // Переопределение для корректной записи в файл.
        return getId() + "," + getType() + "," + getName() + ","
                + getStatus() + "," +  getDetail() + ","
                + getStartTime().format(getFormatter()) + "," +
                this.getEndTime().format(getFormatter()) + "," + getDuration() + ",\n";
    }
}