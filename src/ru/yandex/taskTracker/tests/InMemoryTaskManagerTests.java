package ru.yandex.taskTracker.tests;

import org.junit.jupiter.api.BeforeEach;
import ru.yandex.taskTracker.service.InMemoryTaskManager;

public class InMemoryTaskManagerTests extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void startEnvironment(){
        taskManager = new InMemoryTaskManager();
    }
}