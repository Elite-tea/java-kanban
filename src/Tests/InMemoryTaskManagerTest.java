package Tests;

import TaskManager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;

class InMemoryTaskManagerTest extends TaskManagersTest<InMemoryTaskManager> {

    @BeforeEach
    public void init() {
        manager = new InMemoryTaskManager();
    }
}