import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static HashMap<Integer, ArrayList<Task>> tasks = new HashMap<>();
    static HashMap<Integer, ArrayList<Subtask>> subtasks = new HashMap<>();
    static HashMap<Integer, ArrayList<Epic>> epic = new HashMap<>();

    static ArrayList<Task> taskList = new ArrayList<>();
    static ArrayList<Task> newTaskList = new ArrayList<>();
    static ArrayList<Epic> epicList = new ArrayList<>();
    static ArrayList<Epic> newEpicList = new ArrayList<>();
    static ArrayList<Epic> epicList2 = new ArrayList<>();
    static ArrayList<Subtask> subtaskList = new ArrayList<>();
    static ArrayList<Subtask> newSubtaskList = new ArrayList<>();

    public static void dataTest(){
        String name = "Задача";
        String detail = "Тело задачи";
        String detailNew = "Тело задачи обновлено";
        String details = "Тело Эпикзадачи";
        String newDetails = "Тело Эпикзадачи обновлено";
        String detailz = "Тело Субзадачи";
        String newDetailz = "Тело Субзадачи обновлено";
        String status = "New";
        String newStatus = "Done";
        ArrayList<Integer> idSubtasks = new ArrayList<>();
        ArrayList<Integer> idSubtasks2 = new ArrayList<>();

        int idEpic = 2;

        Task task = new Task(name, detail, status);
        Task newTask = new Task(name, detailNew, status);
        Epic epics = new Epic(name, details, status, idSubtasks);
        Epic newEpic = new Epic(name, newDetails, status, idSubtasks);
        Epic epics2 = new Epic(name, details, status, idSubtasks2);
        Subtask subtask = new Subtask(name, detailz, status, idEpic);
        Subtask newSubtask = new Subtask(name, newDetailz, newStatus, idEpic);

        taskList.add(task);
        newTaskList.add(newTask);

        idSubtasks.add(3);
        epicList.add(epics);
        newEpicList.add(newEpic);
        epicList2.add(epics2);

        subtaskList.add(subtask);
        newSubtaskList.add(newSubtask);
    }
    public static void main(String[] args) {
    //TODO Тут будет эмуляция обмена данных с условным сервером. Сугубо тесты
       dataTest();

        // Т Е С Т Ы ! ! !
        System.out.println(Manager.createTask(taskList)); //Тест пройден, результат true
        System.out.println(Manager.createEpic(epicList2)); //Тест пройден, результат true
        System.out.println(Manager.createEpic(epicList)); //Тест пройден, результат true
        System.out.println(Manager.createSubTask(subtaskList)); //Тест пройден, результат true
        System.out.println(Manager.createSubTask(subtaskList)); //Тест пройден, результат true
        System.out.println(Manager.revoteAllTask(1)); // Проверка на удаление всех задач с типом 1(Task)
        System.out.println(Manager.removeTaskId(0));// Тест пройден, данные удалены при вызове метода
        System.out.println("--Запрос задачи с идентификатором 0,2,3--");
        System.out.println(Manager.getByIdTask(0)); // Тест пройден, null потому что удален тип 1 командой выше
        System.out.println(Manager.getByIdEpic(2));
        System.out.println(Manager.getByIdSubTask(3));
        System.out.println("-----------------------------------------"); // Тест пройден

        System.out.println("-------Отображение задач всех видов------");
        System.out.println(Manager.getAllTasks(1)); // Отображение всех задач типа 1(Task)
        System.out.println("/////////////////////////////////////////");
        System.out.println(Manager.getAllTasks(2)); // Отображение всех задач типа 2(Epic)
        System.out.println("/////////////////////////////////////////");
        System.out.println(Manager.getAllTasks(3)); // Отображение всех типов задач 3(SubTask)
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Смотрим все подзадачи эпика id 2----");
        System.out.println(Manager.getListSubtaskEpic(2));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Task-----");
        System.out.println(Manager.updateTask(0 , newTaskList));
        System.out.println(Manager.getAllTasks(1));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Epic-----");
        System.out.println(Manager.updateEpic(2 , newEpicList));
        System.out.println(Manager.getAllTasks(2));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Subtask-----");// Тест пройден
        System.out.println(Manager.updateSubTask(3 , newSubtaskList));
        System.out.println(Manager.getAllTasks(3));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("--Узнаем устатус эпика после обновления--");
        System.out.println(Manager.getAllTasks(2));
        System.out.println("-----------------------------------------");// Тест пройден

    }
}
