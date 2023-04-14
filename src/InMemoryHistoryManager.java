import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class InMemoryHistoryManager implements HistoryManager{
    protected List<Task> history = new ArrayList<>();
    protected int idHistory = 0;

    public void history(Task tasks) {
        if (history.size() == 10) {
            history.remove(idHistory);
            idHistory++;
        }
        add(tasks);
}

    @Override
    public void add(Task task) {
        history.add(task);
    }

    @Override
    public  List<Task> getHistory() {
        return history;
    }
}
