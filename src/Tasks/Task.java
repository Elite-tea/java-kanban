package Tasks;

import TaskManager.TypeTask;

public class Task {
    protected String name; // Название, кратко описывающее суть задачи
    protected Integer id; // Айди задачи.
    protected String detail; // Описание, в котором раскрываются детали.
    protected Status status; //Статус, отображающий её прогресс.(NEW, IN_PROGRESS, DONE)
    protected TypeTask type; //Тип задачи

    public Task(String name, String detail, int id) {
        this.name = name;
        this.detail = detail;
        this.id = id;
        this.type = TypeTask.TASK;
    }

    public Task() {
        this.type = TypeTask.TASK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int newId) {
        id = newId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status newStatus) {
        status = newStatus;
    }

    public TypeTask getType() {
        return TypeTask.TASK;
    }

    @Override
    public String toString() { // Переопределение для корректной записи в файл.
        return getId() + "," + getType() + "," + getName() + ","
                + getStatus() + "," +  getDetail() + ",\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Task task = (Task) o;

        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}