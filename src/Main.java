import java.util.ArrayList;

public class Main {
    static String name = "Задача";
    static String detail = "Тело задачи";
    static String detailNew = "Тело задачи обновлено";
    static String details = "Тело Эпик задачи";
    static String newDetails = "Тело Эпик задачи обновлено";
    static String detailz = "Тело Суб задачи";
    static  String newDetailz = "Тело Суб задачи обновлено";
    static  String status = "New";
    static  String newStatus = "Done";
    static ArrayList<Integer> idSubtasks = new ArrayList<>();
    static  ArrayList<Integer> idSubtasks2 = new ArrayList<>();

    static int idEpic = 2;

    static InMemoryTaskManager taskManager = Managers.getDefault();


    static Task task = new Task(name, detail, taskManager.id);
    static Task newTask = new Task(name, detailNew, taskManager.id);
    static Epic epics = new Epic(name, details,idSubtasks, taskManager.id);
    static Epic newEpic = new Epic(name, newDetails, idSubtasks, taskManager.id);
    static Epic epics2 = new Epic(name, details, idSubtasks2, taskManager.id);
    static Subtask subtask = new Subtask(name, detailz,idEpic, taskManager.id);
    static Subtask newSubtask = new Subtask(name, newDetailz, idEpic, taskManager.id);

    public static void dataTest(){

    }
    public static void main(String[] args) {
        Managers.getDefault();
        Managers.getDefaultHistory();
    //TODO Тут будет эмуляция обмена данных с условным сервером. Сугубо тесты
        dataTest();

//         Т Е С Т Ы ! ! !

        System.out.println(taskManager.createTask(task)); //Тест пройден, результат true
        System.out.println(taskManager.createEpic(epics)); //Тест пройден, результат true
        System.out.println(taskManager.createEpic(newEpic)); //Тест пройден, результат true
        System.out.println(taskManager.createSubTask(subtask)); //Тест пройден, результат true
        System.out.println(taskManager.createSubTask(newSubtask)); //Тест пройден, результат true
        // System.out.println(taskManager.revoteAllTask(1)); // Проверка на удаление всех задач с типом 1(Task)
        // System.out.println(taskManager.removeTaskId(0,TypeTask.TASK ));// Тест пройден, данные удалены при вызове метода
        System.out.println("--Запрос задачи с идентификатором 1,2,3--");
        System.out.println(taskManager.getByIdTask(1)); // Тест пройден, null потому что удален тип 1 командой выше
        System.out.println(taskManager.getByIdEpic(2));
        System.out.println(taskManager.getByIdSubTask(4));
        System.out.println("-----------------------------------------"); // Тест пройден

      /*  System.out.println("-------Отображение задач всех видов------");
        System.out.println(taskManager.getAllTasks(TypeTask.TASK)); // Отображение всех задач типа 1(Task)
        System.out.println("/////////////////////////////////////////");
        System.out.println(taskManager.getAllTasks(TypeTask.EPIC)); // Отображение всех задач типа 2(Epic)
        System.out.println("/////////////////////////////////////////");
        System.out.println(taskManager.getAllTasks(TypeTask.SUBTASK)); // Отображение всех типов задач 3(SubTask)
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Смотрим все подзадачи эпика id 2----");
        System.out.println(taskManager.getListSubtaskEpic(2));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Task-----");
        System.out.println(taskManager.updateTask(0 , newTask));
        System.out.println(taskManager.getAllTasks(TypeTask.TASK));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Epic-----");
        System.out.println(taskManager.updateEpic(2 , epics2));
        System.out.println(taskManager.getAllTasks(TypeTask.EPIC));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Subtask-----");// Тест пройден
        System.out.println(taskManager.updateSubTask(3 , newSubtask));
        System.out.println(taskManager.getAllTasks(TypeTask.SUBTASK));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("--Узнаем статус эпика после обновления--");
        System.out.println(taskManager.getAllTasks(TypeTask.EPIC));
        System.out.println("-----------------------------------------");// Тест пройден

       */
        System.out.println(taskManager.inMemoryHistoryManager.getHistory()); // Тест пройден, история отображается.


    }
}
