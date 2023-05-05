import TaskManager.Managers;
import TaskManager.TaskManager;
import TaskManager.TypeTask;
import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;

import java.util.ArrayList;

public class Main {
    static ArrayList<Integer> idSubtasks = new ArrayList<>();
    static ArrayList<Integer> idSubtasks2 = new ArrayList<>();

    static int idEpic = 3;

    static TaskManager taskManager = Managers.getDefault();

    static Task task = new Task("Задача task", "Тело задачи", 1);
    static Task newTask = new Task("Задача newTask", "Тело задачи обновлено", 1);
    static Epic epics = new Epic("Задача epics", "Тело Эпик задачи", idSubtasks, 2);
    static Epic newEpic = new Epic("Задача newEpic", "Тело задачи обновлено", idSubtasks, 6);
    static Epic epics2 = new Epic("Задача epics2", "Тело Эпик задачи", idSubtasks2, 3);
    static Subtask subtask = new Subtask("Задача subtask", "Тело Суб задачи", idEpic, 4);
    static Subtask newSubtask = new Subtask("Задача newSubtask", "Тело Суб задачи обновлено", idEpic, 5);
    public static void dataTest() {

    }

    public static void main(String[] args) {
        Managers.getDefault();
        Managers.getDefaultHistory();
        //TODO Тут будет эмуляция обмена данных с условным сервером. Сугубо тесты
        dataTest();

//         Т Е С Т Ы ! ! !

        System.out.println(taskManager.createTask(task)); //Тест пройден, результат true
        System.out.println(taskManager.createEpic(epics)); //Тест пройден, результат true
        System.out.println(taskManager.createEpic(epics2)); //Тест пройден, результат true
        System.out.println(taskManager.createSubTask(subtask)); //Тест пройден, результат true
        System.out.println(taskManager.createSubTask(newSubtask)); //Тест пройден, результат true
        System.out.println(taskManager.createEpic(newEpic)); //Тест пройден, результат true

        idSubtasks2.add(subtask.getId());
        idSubtasks2.add(newSubtask.getId());

        newSubtask.setStatus(Status.DONE);
        System.out.println("--Запрос задачи с идентификатором 1,3,4--");
        System.out.println(taskManager.getByIdTask(1));
        System.out.println(taskManager.getByIdTask(1));
        System.out.println(taskManager.getByIdTask(1));
        System.out.println(taskManager.getByIdTask(1));
        System.out.println(taskManager.getByIdTask(1));
       // System.out.println(taskManager.getByIdEpic(3));
       // System.out.println(taskManager.getByIdSubTask(4));
        System.out.println("-----------------------------------------"); // Тест пройден

        System.out.println("-------Отображение задач всех видов------");
        System.out.println(taskManager.getAllTasks(TypeTask.TASK)); // Отображение всех задач типа 1(Tasks.Task)
        System.out.println("/////////////////////////////////////////");
        System.out.println(taskManager.getAllTasks(TypeTask.EPIC)); // Отображение всех задач типа 2(Tasks.Epic)
        System.out.println("/////////////////////////////////////////");
        System.out.println(taskManager.getAllTasks(TypeTask.SUBTASK)); // Отображение всех типов задач 3(SubTask)
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Смотрим все подзадачи эпика id 3----");
        System.out.println(taskManager.getListSubtaskEpic(3));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Tasks.Task-----");
        newTask.setStatus(Status.DONE);
        System.out.println(taskManager.updateTask(1 , newTask));
        System.out.println(taskManager.getAllTasks(TypeTask.TASK));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Tasks.Epic-----");
        System.out.println(taskManager.updateEpic(3 , epics2));
        System.out.println(taskManager.getAllTasks(TypeTask.EPIC));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("-----Обновляем Tasks.Subtask-----");// Тест пройден
        System.out.println(taskManager.updateSubTask(5 , newSubtask));
        System.out.println(taskManager.getAllTasks(TypeTask.SUBTASK));
        System.out.println("-----------------------------------------");// Тест пройден

        System.out.println("--Узнаем статус эпика после обновления--");
        System.out.println(taskManager.getAllTasks(TypeTask.EPIC));
        System.out.println("-----------------------------------------");// Тест пройден


        System.out.println(taskManager.getHistory()); // Тест пройден, история отображается.


    }
}
