public class Task {
    protected String name; // Название, кратко описывающее суть задачи
    protected int id; // Айди задачи.
    protected String detail; // Описание, в котором раскрываются детали.
    protected String status = "New"; /*Статус, отображающий её прогресс.
    Мы будем выделять следующие этапы жизни задачи(New, IN_PROGRESS, DONE)*/

    public Task(String name, String detail, int id) {
        this.name = name;
        this.detail = detail;
        this.id = id;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String newstatus) {
        newstatus = status;
    }

    @Override
    public String toString() {

        return "{Name " + name + "}\n{Details " + detail + "}\n{Status " + status + "}"; /* Разделитель между задачами
        явлеется символ "," так по нашей логике ПО понимает, где начинается следующая задача.*/
    }
}