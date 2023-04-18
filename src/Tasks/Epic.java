package Tasks;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> idSubtasks; // Список id подзадач принадлежащих эпику.

    public Epic(String name, String detail, ArrayList<Integer> idSubtasks, int id) {
        super(name, detail, id);
        this.idSubtasks = idSubtasks;
    }

    public ArrayList<Integer> getIdSubtasks() {
        return idSubtasks;
    }

    @Override
    public String toString() {
        /* Разделителем между задачами явлеется символ "," так по нашей логике ПО понимает,
        где начинается следующая задача.*/
        return "{Name " + name + "}\n{Details " + detail + "}\n{Status " + status + "}\n{IdSubTasks" + idSubtasks + "}";
    }
}