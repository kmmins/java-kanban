package ru.yandex.taskTracker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class FileBackedTasksManagerTests extends TaskManagerTest<FileBackedTasksManager> {

    /*public InMemoryTaskManagerTests() {
        taskManager = new InMemoryTaskManager();
    }*/

    @BeforeEach // 2 FileBacked
    void startEnvironment(){
        taskManager = new FileBackedTasksManager(".resources\\test.tmp");
    }

    @Test // 4
    void name(){

        //taskManager = new FileBackedTasksManager(".resources\\autosavetest.csv");
        /*taskManager.deleteAllTasks();
        taskManager.deleteAllSubTasks();
        taskManager.deleteAllEpics();*/
        taskManager.save();
        FileBackedTasksManager tmL = FileBackedTasksManager.loadFromFile(Paths.get(".resources\\test.tmp").toFile());
        //assertEquals(0, taskManagerLoad.getAllTasks().size());
        assertTrue(tmL.getAllTasks().isEmpty());
    }
}
