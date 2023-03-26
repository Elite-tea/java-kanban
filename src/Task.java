public class Task {
    String name; // Название, кратко описывающее суть задачи
    String detail; // Описание, в котором раскрываются детали.
    protected String status; /*Статус, отображающий её прогресс.
    Мы будем выделять следующие этапы жизни задачи(New, IN_PROGRESS, DONE)*/

    public Task(String name, String detail, String status) {
        this.name = name;
        this.detail = detail;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {

        return "{Name " + name + "}\n{Details " + detail + "}\n{Status " + status + "}"; /* Разделитель между задачами
        явлеется символ "," так по нашей логике ПО понимает, где начинается следующая задача.*/
    }
}