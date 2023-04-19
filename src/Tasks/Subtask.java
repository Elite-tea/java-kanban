package Tasks;

public class Subtask extends Task {
    private final int idEpic; // Id главной задачи, возможно пригодится для реализации проверки принадлежности к главному эпику.

    public Subtask(String name, String detail, int idEpic, int id) {
        super(name, detail, id);
        this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }

    @Override
    public String toString() {
        /* Вывод 2-ой и следующих задач начинается с запятой, что по нашей логике для программы
       будет восприниматься как разделитель между задачами.*/
        return "{Name " + name + "}" +
                "\n{Details " + detail + "}" +
                "\n{Status " + status + "}" +
                "\n{IdEpicTasks " + idEpic + "}\n";
    }
}