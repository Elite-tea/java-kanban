//import TaskManager.*;
//import Tasks.*;
//
//import java.io.File;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//
//public class Main {
//    static ArrayList<Integer> idSubtasks = new ArrayList<>();
//    static ArrayList<Integer> idSubtasks2 = new ArrayList<>();
//
//    static int idEpic = 3;
//
//    static TaskManager taskManager = Managers.getDefault();
//
//    //static FileBackedTasksManager fileBackedTasksManager2 = FileBackedTasksManager.loadFromFile((new File("src/resources/base.csv")));
//
//
//    static Task task = new Task("СабТаскЭпикТест2", "Саб таск эпик тест2", 1, 30,
//            LocalDateTime.of(2023,6,10,12,0));
//    static Task newTask = new Task("Задача newTask", "Тело задачи обновлено", 11, 30,
//            LocalDateTime.of(2023,6,10,12,0));
//    static Epic epics = new Epic("Задача epics", "Тело Эпик задачи", idSubtasks, 2);
//    static Epic newEpic = new Epic("Задача newEpic", "Тело задачи обновлено", idSubtasks, 6);
//    static Epic epics2 = new Epic("Задача epics2", "Тело Эпик задачи", idSubtasks2, 3);
//    static Subtask subtask = new Subtask("Задача subtask", "Тело Суб задачи", idEpic, 4,
//            LocalDateTime.of(2023,6,10,12,0),30);
//    static Subtask newSubtask = new Subtask("Задача newSubtask", "Тело Суб задачи обновлено", idEpic, 5,
//            LocalDateTime.of(2023,6,10,12,0),30);
//
//    public static void main(String[] args) {
//        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("src/resources/base.csv"));
//        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
//        Managers.getDefault();
//        Managers.getDefaultHistory();
//        // Не совсем понял что требовалось сделать с методом, но все работает.
//        FileBackedTasksManager fileBackedTasksManager2 = fileBackedTasksManager.loadFromFile();
//        //TODO Тут будет эмуляция обмена данных с условным сервером. Сугубо тесты
//        //dataTest();
//
////         Т Е С Т Ы ! ! !
//
////        System.out.println(fileBackedTasksManager.createTask(task)); //Тест пройден, результат true
////        System.out.println(fileBackedTasksManager.createEpic(epics)); //Тест пройден, результат true
////        System.out.println(fileBackedTasksManager.createEpic(epics2)); //Тест пройден, результат true
////        System.out.println(fileBackedTasksManager.createSubTask(subtask)); //Тест пройден, результат true
////        System.out.println(fileBackedTasksManager.createSubTask(newSubtask)); //Тест пройден, результат true
////        System.out.println(fileBackedTasksManager.createEpic(newEpic)); //Тест пройден, результат true
////
////        idSubtasks2.add(subtask.getId());
////        idSubtasks2.add(newSubtask.getId());
////
////        newSubtask.setStatus(Status.DONE);
////        System.out.println("--Запрос задачи с идентификатором 1,3,4--");
////        System.out.println(fileBackedTasksManager.getByIdTask(1));
////        System.out.println(fileBackedTasksManager.getByIdSubTask(4));
////        // System.out.println(fileBackedTasksManager.getByIdTask(1));
////        // System.out.println(fileBackedTasksManager.getByIdTask(1));
////        // System.out.println(fileBackedTasksManager.getByIdTask(1));
////       // System.out.println(taskManager.getByIdEpic(3));
////       // System.out.println(taskManager.getByIdSubTask(4));
////        System.out.println("-----------------------------------------"); // Тест пройден
////
////        System.out.println("-------Отображение задач всех видов------");
//        System.out.println(inMemoryTaskManager.getAllTasks(TypeTask.TASK)); // Отображение всех задач типа 1(Tasks.Task)
////        System.out.println("/////////////////////////////////////////");
//        System.out.println(inMemoryTaskManager.getAllTasks(TypeTask.EPIC)); // Отображение всех задач типа 2(Tasks.Epic)
////        System.out.println("/////////////////////////////////////////");
//      System.out.println(inMemoryTaskManager.getAllTasks(TypeTask.SUBTASK)); // Отображение всех типов задач 3(SubTask)
////        System.out.println("-----------------------------------------");// Тест пройден
////
////        System.out.println("-----Смотрим все подзадачи эпика id 3----");
////        System.out.println(fileBackedTasksManager.getListSubtaskEpic(3));
////        System.out.println("-----------------------------------------");// Тест пройден
////
////        System.out.println("-----Обновляем Tasks.Task-----");
////        newTask.setStatus(Status.DONE);
////        System.out.println(fileBackedTasksManager.updateTask(1 , newTask));
////        System.out.println(fileBackedTasksManager.getAllTasks(TypeTask.TASK));
////        System.out.println("-----------------------------------------");// Тест пройден
////
////        System.out.println("-----Обновляем Tasks.Epic-----");
////        System.out.println(fileBackedTasksManager.updateEpic(3 , epics2));
////        System.out.println(fileBackedTasksManager.getAllTasks(TypeTask.EPIC));
////        System.out.println("-----------------------------------------");// Тест пройден
////
////        System.out.println("-----Обновляем Tasks.Subtask-----");// Тест пройден
////        System.out.println(fileBackedTasksManager.updateSubTask(5 , newSubtask));
////        System.out.println(fileBackedTasksManager.getAllTasks(TypeTask.SUBTASK));
////        System.out.println("-----------------------------------------");// Тест пройден
////
////        System.out.println("--Узнаем статус эпика после обновления--");
////       System.out.println(fileBackedTasksManager.getAllTasks(TypeTask.EPIC));
//        System.out.println("-----------------------------------------");// Тест пройден
////
////
//
//        System.out.println(fileBackedTasksManager2.getHistory()); // Тест пройден, история отображается.
//
//
//
//    }
//}