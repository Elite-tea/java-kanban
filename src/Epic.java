import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> idSubtasks; // Списк id сподзадач пренадлежащих эпику.

    public Epic(String name, String detail, String status, ArrayList<Integer> idSubtasks) {
        super(name, detail,status);
        this.idSubtasks = idSubtasks;
    }
    @Override
    public String toString() {
        return "{Name " + name + "}\n{Details " + detail + "}\n{Status " + status + "}\n{IdSubTasks" + idSubtasks + "}";
        /* Разделителем между задачами явлеется символ "," так по нашей логике ПО понимает,
         где начинается следующая задача.*/
    }
}
