public class Subtask extends Task {
   protected int idEpic; // Id главной задачи, возможно еще и пригодится для реализации проверки принадлежности к главному эпику.

   public Subtask(String name, String detail,int idEpic, int id) {
      super(name, detail, id);
      this.idEpic = idEpic;
   }

   @Override
   public String toString() {
      return "{Name " + name + "}\n{Details " + detail + "}\n{Status " + status + "}\n{IdEpicTasks " + idEpic + "}\n";
      /* Вывод 2-ой и следующих задач начинается с запятой, что по нашей логике для программы
       будет восприниматься как разделитель между задачами.*/
   }
}
