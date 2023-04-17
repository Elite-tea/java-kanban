package HistoryManager;

import Tasks.Task;

import java.util.List;

public interface HistoryManager { // Менеджер истории, сохраняет и выводит историю просмотра задач.
    void add(Task task); // Добавление задачи в список задач.

    List<Task> getHistory(); // Отображение задач, просмотренных ранее.
}