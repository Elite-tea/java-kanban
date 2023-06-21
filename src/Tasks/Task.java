package Tasks;

import TaskManager.TypeTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    protected String name; // Название, кратко описывающее суть задачи
    protected Integer id; // Айди задачи.
    protected String detail; // Описание, в котором раскрываются детали.
    protected Status status = Status.NEW; // Статус, отображающий её прогресс.(NEW, IN_PROGRESS, DONE)
    protected TypeTask type; // Тип задачи
    protected int duration; // Продолжительность задачи, оценка того, сколько времени она займёт в минутах (число);
    protected LocalDateTime startTime; // Дата, когда предполагается приступить к выполнению задачи.

    protected final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public Task(String name, String detail, int id, int duration, LocalDateTime startTime) {
        this.name = name;
        this.detail = detail;
        this.id = id;
        this.type = TypeTask.TASK;
        this.duration = duration;
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() { // Время завершения задачи
        try {
            return this.startTime.plus(Duration.ofMinutes(this.duration));
        } catch (NullPointerException e) {
            System.out.print("NullPointerException");
            return null;
        }
    }

    public DateTimeFormatter getFormatter() {
        return FORMATTER;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
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
        return getId() + "," + getType() + "," + getName() + "," + getStatus() + "," + getDetail() + "," +
                startTime.format(FORMATTER) + "," + this.getEndTime().format(FORMATTER) + "," + duration + ",\n";
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

        return Objects.equals(name, task.name) && Objects.equals(id, task.id) && Objects.equals(detail, task.detail) &&
                status == task.status && Objects.equals(duration, task.duration) &&
                Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}