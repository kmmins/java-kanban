package ru.yandex.taskTracker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTracker.model.Epic;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTasksManagerTests extends TaskManagerTest<FileBackedTasksManager> {

    @BeforeEach
    void startEnvironment() {
        taskManager = new FileBackedTasksManager(".resources\\test.tmp");
    }

    @Test
    void checkLoadEmpty() {
        taskManager.save();
        FileBackedTasksManager tmLoad = FileBackedTasksManager.loadFromFile(Paths.get(".resources\\test.tmp").toFile());
        assertTrue(tmLoad.getAllTasks().isEmpty(), "Список задач не пустой");
    }

    @Test
    void checkLoadEpicWithoutSubTusks() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cE1 = taskManager.createEpic(epic);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        taskManager.save();
        FileBackedTasksManager tmLoad = FileBackedTasksManager.loadFromFile(Paths.get(".resources\\test.tmp").toFile());
        assertNotNull(tmLoad, "Эпики не возвращаются.");
        assertFalse(tmLoad.getAllEpics().isEmpty(), "Список эпиков пустой.");
        assertEquals(1, tmLoad.getAllEpics().size(), "Неверное количество эпиков.");
        assertEquals(cE1.getId(), tmLoad.getAllEpics().get(0).getId(), "Id эпиков не совпадают.");
    }

    @Test
    void checkLoadEmptyHistory() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cE1 = taskManager.createEpic(epic);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        taskManager.save();
        FileBackedTasksManager tmLoad = FileBackedTasksManager.loadFromFile(Paths.get(".resources\\test.tmp").toFile());
        assertNotNull(tmLoad, "Эпики не возвращаются.");
        assertFalse(tmLoad.getAllEpics().isEmpty(), "Список эпиков пустой.");
        assertEquals(1, tmLoad.getAllEpics().size(), "Неверное количество эпиков.");
        assertEquals(cE1.getId(), tmLoad.getAllEpics().get(0).getId(), "Id эпиков не совпадают.");
        var result = taskManager.getHistoryName();
        assertTrue(result.isEmpty(), "Список истории не пустой");
    }
}