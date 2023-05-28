package Tasks;

import TaskManager.TypeTask;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> idSubtasks = new ArrayList<>(); // Список id подзадач принадлежащих эпику.

    public Epic(String name, String detail, ArrayList<Integer> idSubtasks, int id) {
        super(name, detail, id);
        this.idSubtasks = idSubtasks;
        this.type = TypeTask.EPIC;
    }

    public Epic(String name, String detail,int id) {
        super(name, detail, id);
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
    public String toString() { // Переопределение для корректной записи в файл.
        return getId() + "," + getType() + "," + getName() + ","
                + getStatus() + "," +  getDetail() + ",\n";
    }
}