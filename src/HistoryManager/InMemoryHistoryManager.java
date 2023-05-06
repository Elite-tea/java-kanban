package HistoryManager;

import Tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private CustomLinkedList<Task> history = new CustomLinkedList<>();
    private final Map<Integer, Task> dataList = new HashMap<>();


    @Override
    public void add(Task task) {

        if (dataList.containsKey(task.getId())) {
            remove(task.getId());
        }

        dataList.put(task.getId(), task);
        history.linkLast(task);
    }

    @Override
    public List<Task> getHistory() {

        return history.getTasks();
    }

    @Override
    public void remove(int id) {

        if (dataList.containsKey(id)) {
            history.remove(dataList.get(id));
        }
    }

    @Override
    public void allRemove() { //Метод для удаления сразу всей истории в случае удаления всех задач

        history = null;
    }

    static class CustomLinkedList<T> { // В ТЗ очень непонятно, необходима реализация через класс или нет.

        private Node head;
        private Node tail;
        private int size = 0;

        private void linkLast(Task task) { // Добавляем задачу в конец списка

            Node prevNode = tail;
            Node node = new Node(tail, task, null);
            tail = node;

            if (prevNode == null) { //Если предыдущая нода пуска, значит эта будет головой.
                head = node;

            } else { //Иначе, эта будет следующей.
                prevNode.next = node;
            }

            size++;
        }

        public List<Task> getTasks() { // Собираем задачи в лист и возвращаем его.

            List<Task> tasks = new ArrayList<>();

            for (Node node = head; node != null; node = node.next) { //Пробежимся по нодам
                tasks.add(node.data);
            }

            return tasks;
        }

        void removeNode(Node node) {

            Node nextNode = node.next;
            Node prevNode = node.prev;

            if (prevNode == null) { //Проверяем ноду на пустоту, если пусто то головой будет следующая
                head = nextNode;
            } else {
                prevNode.next = nextNode;
            }

            if (nextNode == null) { //Аналогично для хвоста
                tail = prevNode;
            } else {
                node.next = null;
            }

            node.data = null;
            size--;
        }

        public void remove(Task task) { //Удаляем задачу из просмотра

            for (Node node = head; node != null; node = node.next) {
                if (task.equals(node.data)) {
                    removeNode(node);
                }
            }
        }

    }

    private static class Node { //Т.к нода используется только тут, не вижу смысла выводить ее в отдельный файл класса.

        Task data;
        Node next;
        Node prev;

        Node(Node prev, Task data, Node next) {

            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}