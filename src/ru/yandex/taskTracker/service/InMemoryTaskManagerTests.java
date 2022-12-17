package ru.yandex.taskTracker.service;

import org.junit.jupiter.api.BeforeEach;

public class InMemoryTaskManagerTests extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void startEnvironment(){
        taskManager = new InMemoryTaskManager();
    }
}