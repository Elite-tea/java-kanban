public class Subtask extends Task {
   int idEpic; // Id главной задачи, возможно еще и пригодится для реализации проверки принадлежности к главному эпику.

   public Subtask(String name, String detail,String status, int idEpic) {
      super(name, detail,status);
      this.idEpic = idEpic;
   }

   @Override
   public String toString() {
      return "{Name " + name + "}\n{Details " + detail + "}\n{Status " + status + "}\n{IdEpicTasks " + idEpic + "}\n";
      /* Вывод 2-ой и следующих задач начинается с запитой, что для ПО по нашей логике
       будет восприниматься как разделитель между задачами.*/
   }
}
