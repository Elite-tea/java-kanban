public class Task {
    protected String name; // Название, кратко описывающее суть задачи
    public int id; // Айди задачи.
    protected String detail; // Описание, в котором раскрываются детали.
    protected Status status; /*Статус, отображающий её прогресс.
    Мы будем выделять следующие этапы жизни задачи(NEW, IN_PROGRESS, DONE)*/

    public Task(String name, String detail, int id) {
        this.name = name;
        this.detail = detail;
        this.id = id;
    }

    public Status getStatus() {

        return status;
    }

    public void setId(int newId) {
        id = newId;
    }

    public void setStatus(Status newstatus) {
        status = newstatus;
    }

    @Override
    public String toString() {

        return "{Name " + name + "}" +
                "\n{Details " + detail + "}" +
                "\n{Status " + status + "}"; // Переопределение для корректного вывода задач.
    }
}