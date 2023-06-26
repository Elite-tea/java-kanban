package TaskManager;

import HistoryManager.HistoryManager;
import HistoryManager.InMemoryHistoryManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault(String url) {
        return new HttpTaskManager(getDefaultHistory(), url);
    }

}