import java.util.ArrayList;

public class Main {
    static String name = "Задача";
    static String detail = "Тело задачи";
    static String detailNew = "Тело задачи обновлено";
    static String details = "Тело Эпикзадачи";
    static String newDetails = "Тело Эпикзадачи обновлено";
    static String detailz = "Тело Субзадачи";
    static  String newDetailz = "Тело Субзадачи обновлено";
    static  String status = "New";
    static  String newStatus = "Done";
    static ArrayList<Integer> idSubtasks = new ArrayList<>();
    static  ArrayList<Integer> idSubtasks2 = new ArrayList<>();
    static Manager manager = new Manager();
    static int idEpic = 2;

     static Task task = new Task(name, detail, manager.id);
    static Task newTask = new Task(name, detailNew,manager.id);
    static Epic epics = new Epic(name, details,idSubtasks,manager.id);
    static Epic newEpic = new Epic(name, newDetails, idSubtasks,manager.id);
    static Epic epics2 = new Epic(name, details, idSubtasks2,manager.id);
    static Subtask subtask = new Subtask(name, detailz,idEpic,manager.id);
    static Subtask newSubtask = new Subtask(name, newDetailz, idEpic,manager.id);
    public static void dataTest(){

    }
    public static void main(String[] args) {
    //TODO Тут будет эмуляция обмена данных с условным сервером. Сугубо тесты
        dataTest();

//         Т Е С Т Ы ! ! !
/*
        System.out.println(manager.createTask(task)); //Тест пройден, результат true
        System.out.println(manager.createEpic(epics)); //Тест пройден, результат true
        System.out.println(manager.createEpic(newEpic)); //Тест пройден, результат true
        System.out.println(manager.createSubTask(subtask)); //Тест пройден, результат true
        System.out.println(manager.createSubTask(newSubtask)); //Тест пройден, результат true
        // System.out.println(manager.revoteAllTask(1)); // Проверка на удаление всех задач с типом 1(Task)
        // System.out.println(manager.removeTaskId(0));// Тест пройден, данные удалены при вызове метода
        System.out.println("--Запрос задачи с идентификатором 0,2,3--");
        System.out.println(manager.getByIdTask(0)); // Тест пройден, null потому что удален тип 1 командой выше
        System.out.println(manager.getByIdEpic(2));
        System.out.println(manager.getByIdSubTask(3));
        System.out.println("-----------------------------------------"); // Тест пройден

        System.out.println("-------Отображение задач всех видов------");
        System.out.println(manager.getAllTasks(TypeTask.TASK)); // Отображение всех задач типа 1(Task)
        System.out.println("/////////////////////////////////////////");
        System.out.println(manager.getAllTasks(TypeTask.EPIC)); // Отображение всех задач типа 2(Epic)
        System.out.println("/////////////////////////////////////////");
        System.out.println(manager.getAllTasks(TypeTask.SUBTASK)); // Отображение всех типов задач 3(SubTask)
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Смотрим все подзадачи эпика id 2----");
        System.out.println(manager.getListSubtaskEpic(2));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Task-----");
        System.out.println(manager.updateTask(0 , newTask));
        System.out.println(manager.getAllTasks("task"));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Epic-----");
        System.out.println(manager.updateEpic(2 , epics2));
        System.out.println(manager.getAllTasks("epic"));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Subtask-----");// Тест пройден
        System.out.println(manager.updateSubTask(3 , newSubtask));
        System.out.println(manager.getAllTasks("subtask"));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("--Узнаем устатус эпика после обновления--");
        System.out.println(manager.getAllTasks("epic"));
        System.out.println("-----------------------------------------");// Тест пройден

 */
    }
}
