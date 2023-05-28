package TaskManager;

import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;
import Exception.ManagerSaveException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    static File file;
    String HEAD = "id,type,name,status,description,epic \n";

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public void save() {

        try {
            Writer fileWriter = new FileWriter(file);

            fileWriter.write(HEAD);

            if (!super.getAllTasks(TypeTask.TASK).isEmpty()) {

                for (Task task : super.getAllTasks(TypeTask.TASK)) {
                    fileWriter.write(task.toString());
                }

            } else {
                fileWriter.write("");
            }

            if (!super.getAllTasks(TypeTask.EPIC).isEmpty()) {

                for (Task task : super.getAllTasks(TypeTask.EPIC)) {
                    fileWriter.write(task.toString());
                }

            } else {
                fileWriter.write("");
            }

            if (!super.getAllTasks(TypeTask.SUBTASK).isEmpty()) {

                for (Task task : super.getAllTasks(TypeTask.SUBTASK)) {
                    fileWriter.write(task.toString());
                }

            } else {
                fileWriter.write("");
            }

            fileWriter.write("\n");

            historyToString(fileWriter);
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void historyToString(Writer fileWriter) throws IOException {

        for (Task task : getHistory()) {
            fileWriter.write(task.getId() + ",");
        }
    }

    private static void fromString(String value) {

        String[] data = value.split(",");


        switch (data[1]) { // Сделал два варианта восстановления таска, оба рабочие, какой более верно использовать?
            case "TASK":

                Task task = new Task();
                task.setName(data[2]);
                task.setId(Integer.parseInt(data[0]));
                task.setDetail(data[4]);
                task.setStatus(Status.valueOf(data[3]));
                tasks.put(task.getId(), task);
                break;

            case "EPIC":

                Epic epics = new Epic(data[2], data[4], Integer.parseInt(data[0]));
                epics.setStatus(Status.valueOf(data[3]));
                epic.put(epics.getId(), epics);
                break;

            case "SUBTASK":

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

    private static List<Integer> historyFromString(String value) {

        List<Integer> historyList = new ArrayList<>();

        if (value != null) {
            String[] idHistory = value.split(",");

            for (String id : idHistory) {
                historyList.add(Integer.parseInt(id));
            }
        }
        return historyList;
    }

    public static void loadFromFile() {

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
            throw new ManagerSaveException("Error read data file");
        }
    }

    @Override
    public boolean remoteAllTask(TypeTask type) {

        save();

        return super.remoteAllTask(type);
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
}