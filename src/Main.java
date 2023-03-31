import java.util.ArrayList;

public class Main {
    static String name = "Задача";
    static String detail = "Тело задачи";
    static String detailNew = "Тело задачи обновлено";
    static String details = "Тело Эпикзадачи";
    static String newDetails = "Тело Эпикзадачи обновлено";
    static String detailz = "Тело Субзадачи";
    static String newDetailz = "Тело Субзадачи обновлено";
    String status = "New";
    String newStatus = "Done";
    static ArrayList<Integer> idSubtasks = new ArrayList<>();
    static ArrayList<Integer> idSubtasks2 = new ArrayList<>();

    static int idEpic = 2;

     static Task task = new Task(name, detail, Manager.id);
    static Task newTask = new Task(name, detailNew,Manager.id);
    static Epic epics = new Epic(name, details,idSubtasks,Manager.id);
    static Epic newEpic = new Epic(name, newDetails, idSubtasks,Manager.id);
    static Epic epics2 = new Epic(name, details, idSubtasks2,Manager.id);
    static Subtask subtask = new Subtask(name, detailz,idEpic,Manager.id);
    static Subtask newSubtask = new Subtask(name, newDetailz, idEpic,Manager.id);
    public static void dataTest(){

    }
    public static void main(String[] args) {
    //TODO Тут будет эмуляция обмена данных с условным сервером. Сугубо тесты
       dataTest();

        // Т Е С Т Ы ! ! ! Уважаемый Владимир Белов! Очень приятно получать граммотный фитбек, вроде все работает, прошу
        // прощения если чего то не углядел, доделываю из больницы, слег с аппендицитом. Жду обратной связи, спасибо!

        System.out.println(Manager.createTask(task)); //Тест пройден, результат true
        System.out.println(Manager.createEpic(epics)); //Тест пройден, результат true
        System.out.println(Manager.createEpic(newEpic)); //Тест пройден, результат true
        System.out.println(Manager.createSubTask(subtask)); //Тест пройден, результат true
        System.out.println(Manager.createSubTask(newSubtask)); //Тест пройден, результат true
        // System.out.println(Manager.revoteAllTask(1)); // Проверка на удаление всех задач с типом 1(Task)
        // System.out.println(Manager.removeTaskId(0));// Тест пройден, данные удалены при вызове метода
        System.out.println("--Запрос задачи с идентификатором 0,2,3--");
        System.out.println(Manager.getByIdTask(0)); // Тест пройден, null потому что удален тип 1 командой выше
        System.out.println(Manager.getByIdEpic(2));
        System.out.println(Manager.getByIdSubTask(3));
        System.out.println("-----------------------------------------"); // Тест пройден

        System.out.println("-------Отображение задач всех видов------");
        System.out.println(Manager.getAllTasks("task")); // Отображение всех задач типа 1(Task)
        System.out.println("/////////////////////////////////////////");
        System.out.println(Manager.getAllTasks("epic")); // Отображение всех задач типа 2(Epic)
        System.out.println("/////////////////////////////////////////");
        System.out.println(Manager.getAllTasks("subtask")); // Отображение всех типов задач 3(SubTask)
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Смотрим все подзадачи эпика id 2----");
        System.out.println(Manager.getListSubtaskEpic(2));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Task-----");
        System.out.println(Manager.updateTask(0 , newTask));
        System.out.println(Manager.getAllTasks("task"));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Epic-----");
        System.out.println(Manager.updateEpic(2 , epics2));
        System.out.println(Manager.getAllTasks("epic"));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Subtask-----");// Тест пройден
        System.out.println(Manager.updateSubTask(3 , newSubtask));
        System.out.println(Manager.getAllTasks("subtask"));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("--Узнаем устатус эпика после обновления--");
        System.out.println(Manager.getAllTasks("epic"));
        System.out.println("-----------------------------------------");// Тест пройден

    }
}
