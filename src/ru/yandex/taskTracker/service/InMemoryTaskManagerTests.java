package ru.yandex.taskTracker.service;

import org.junit.jupiter.api.BeforeEach;

public class InMemoryTaskManagerTests extends TaskManagerTest<InMemoryTaskManager> {

    /*public InMemoryTaskManagerTests() {
        taskManager = new InMemoryTaskManager();
    }*/

    @BeforeEach // 2 InMemory
    void startEnvironment(){
        taskManager = new InMemoryTaskManager();
    }
}
