package HistoryManager;

import Tasks.Task;

import java.util.List;

public interface HistoryManager { // Менеджер истории, сохраняет и выводит историю просмотра задач
    void add(Task task); // Добавление задачи в список задач

    void remove(int id); // Удаление задачи из просмотра

    List<Task> getHistory(); // Отображение задач, просмотренных ранее

    void allRemove(); // Метод для удаления всей истории, в случае удаления всех задач
}