package ru.yandex.taskTracker.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.taskTracker.service.HttpTaskManager;
import ru.yandex.taskTracker.service.KVServer;

import java.io.IOException;

public class HttpTaskManagerTests extends TaskManagerTest<HttpTaskManager>{
    KVServer testServer;

    @BeforeEach
    void startEnvironment() throws IOException {
        testServer = new KVServer();
        testServer.start();
        taskManager = new HttpTaskManager("http://localhost:8078/");
    }

    @AfterEach
    void stopEnvironment(){
        testServer.stop();
    }
}