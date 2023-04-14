public class Managers {
     static InMemoryTaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}