package TaskManager;

import Exception.ManagerSaveException;
import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;

import java.io.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;
    private final String HEAD = "id,type,name,status,description,epic \n"; // Переносил переменную по предложению IDEA)

    /**
     * {@inheritDoc}
     * Переопределяем методы для сохранения тасков в файл
     */

    @Override
    public boolean removeAllTask(TypeTask type) {

        save();

        return super.removeAllTask(type);
    }

    @Override
    public boolean createTask(Task newTask) {

        save();

        return super.createTask(newTask);
    }

    @Override
    public boolean createEpic(Epic newEpic) {

        save();

        return super.createEpic(newEpic);
    }

    @Override
    public boolean createSubTask(Subtask subtask) {

        save();

        return super.createSubTask(subtask);
    }

    @Override
    public boolean updateTask(Integer id, Task task) {

        save();

        return super.updateTask(id, task);
    }

    @Override
    public boolean updateEpic(Integer id, Epic dataEpic) {

        save();

        return super.updateEpic(id, dataEpic);
    }

    @Override
    public boolean updateSubTask(Integer id, Subtask subtask) {

        save();

        return super.updateSubTask(id, subtask);
    }

    @Override
    public boolean removeTaskId(Integer id, TypeTask type) {

        save();

        return super.removeTaskId(id, type);
    }

    @Override
    public Epic getByIdEpic(Integer id) {
        if (super.getByIdEpic(id) != null) {
            save();
        }
        return super.getByIdEpic(id);
    }

    @Override
    public Subtask getByIdSubTask(Integer id) {
        if (super.getByIdSubTask(id) != null) {
            save();
        }
        return super.getByIdSubTask(id);
    }

    @Override
    public Task getByIdTask(Integer id) {
        if (super.getByIdTask(id) != null) {
            save();
        }
        return super.getByIdTask(id);
    }

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public FileBackedTasksManager loadFromFile() { // Загружаем файл, обрабатываем построчно.

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            String line;

            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();

                if (line.isEmpty()) {
                    continue;
                }

                fromString(line);

            }

        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
        return new FileBackedTasksManager(file);
    }

    private void historyToString(Writer fileWriter) throws IOException { //

        for (Task task : getHistory()) {
            fileWriter.write(task.getId() + ",");
        }
    }

    private void fromString(String value) {

        String[] data = value.split(",");


        switch (data[1]) { // Сделал два варианта восстановления таска, оба рабочие, какой более верно использовать?
            case "TASK": // Первый вариант, восстановления таска через сеттеры.

                Task task = new Task();
                task.setName(data[2]);
                task.setId(Integer.parseInt(data[0]));
                task.setDetail(data[4]);
                task.setStatus(Status.valueOf(data[3]));
                tasks.put(task.getId(), task);
                break;

            case "EPIC": // Второй вариант, указываем параметры при создании. Как будет более корректно?

                Epic epics = new Epic(data[2], data[4], Integer.parseInt(data[0]));
                epics.setStatus(Status.valueOf(data[3]));
                epic.put(epics.getId(), epics);
                break;

            case "SUBTASK": // Так же и это, вариант с указанием параметров при создании таска.

                Subtask subtask = new Subtask(data[2], data[4], Integer.parseInt(data[5]), Integer.parseInt(data[0]));
                subtask.setStatus(Status.valueOf(data[3]));
                subtasks.put(subtask.getId(), subtask);
                Epic epicId = epic.get(Integer.parseInt(data[5]));
                epicId.setIdSubtasks(subtask.getId());
                epic.put(epicId.getId(), epicId);
                break;

            default:

                if (!data[0].equals("id")) {
                    for (String id : data) {
                        Task taskHistory;
                        if (tasks.containsKey(Integer.parseInt(id))) {
                            taskHistory = tasks.get(Integer.parseInt(id));
                        }
                        else if (subtasks.containsKey(Integer.parseInt(id))) {
                            taskHistory = subtasks.get(Integer.parseInt(id));
                        }
                        else {
                            taskHistory = epic.get(Integer.parseInt(id));
                        }
                        historyManager.add(taskHistory);
                    }
                }

        }

    }

    private void save() {

        try {
            Writer fileWriter = new FileWriter(file);

            fileWriter.write(HEAD);

            if (!super.getAllTasks(TypeTask.TASK).isEmpty()) {

                for (Task task : super.getAllTasks(TypeTask.TASK)) {
                    fileWriter.write(task.toString());
                }

            }

            if (!super.getAllTasks(TypeTask.EPIC).isEmpty()) {

                for (Task task : super.getAllTasks(TypeTask.EPIC)) {
                    fileWriter.write(task.toString());
                }

            }

            if (!super.getAllTasks(TypeTask.SUBTASK).isEmpty()) {

                for (Task task : super.getAllTasks(TypeTask.SUBTASK)) {
                    fileWriter.write(task.toString());
                }

            }

            fileWriter.write("\n");

            historyToString(fileWriter);
            fileWriter.close();

        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

}