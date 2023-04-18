package Tasks;

public class Task {
    protected String name; // Название, кратко описывающее суть задачи
    protected int id; // Айди задачи.
    protected String detail; // Описание, в котором раскрываются детали.
    protected Status status; //Статус, отображающий её прогресс.(NEW, IN_PROGRESS, DONE)

    public Task(String name, String detail, int id) {
        this.name = name;
        this.detail = detail;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(int newId) {
        id = newId;
    }

    public void setStatus(Status newStatus) {
        status = newStatus;
    }

    @Override
    public String toString() {
        return "{Name " + name + "}" +
                "\n{Details " + detail + "}" +
                "\n{Status " + status + "}"; // Переопределение для корректного вывода задач.
    }
}