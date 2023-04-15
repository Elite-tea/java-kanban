import java.util.ArrayList;
import java.util.List;

class InMemoryHistoryManager implements HistoryManager {
    protected List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (history.size() == 10) {
            history.remove(0); /* Здесь был инкремент, по моей логике список после удаления не перемещался, т.е
                                    не удалялся, а очищалась позиция и оставалась пустая. Теперь работает корректно. */
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}