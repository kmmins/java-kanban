package ru.yandex.taskTracker.tests;

import org.junit.jupiter.api.Test;
import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.service.HistoryManager;
import ru.yandex.taskTracker.service.Managers;
import ru.yandex.taskTracker.service.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTests {
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final TaskManager taskManager = Managers.getDefault();
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Test
    void checkEmptyHistory() {
        var history = historyManager.getHistory();
        assertNotNull(history, "История возвращает null.");
        assertTrue(history.isEmpty(), "Список задач в истории не пустой.");
    }

    @Test
    void checkRepeatHistory() {
        var task1 = new Task();
        task1.setName("Задача_1");
        task1.setDescription("Описание_1");
        task1.setStartTime(LocalDateTime.parse("01.02.2022 12:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var createdTask1 = taskManager.createTask(task1);

        var task2 = new Task();
        task2.setName("Задача_2");
        task2.setDescription("Описание_2");
        task2.setStartTime(LocalDateTime.parse("02.02.2022 12:00", format));
        task2.setDuration(Duration.ofMinutes(60));
        var createdTask2 = taskManager.createTask(task2);

        var task3 = new Task();
        task3.setName("Задача_3");
        task3.setDescription("Описание_3");
        task3.setStartTime(LocalDateTime.parse("03.02.2022 12:00", format));
        task3.setDuration(Duration.ofMinutes(60));
        var createdTask3 = taskManager.createTask(task3);

        historyManager.appendHistory(createdTask1);
        historyManager.appendHistory(createdTask2);
        historyManager.appendHistory(createdTask3);
        historyManager.appendHistory(createdTask1);
        historyManager.appendHistory(createdTask1);
        historyManager.appendHistory(createdTask2);
        historyManager.appendHistory(createdTask2);

        var history = historyManager.getHistory();
        assertNotNull(history, "История возвращает null.");
        assertFalse(history.isEmpty(), "Список задач в истории пуст.");
        assertEquals(3, history.size(), "Неверное количество задач в истории.");
    }

    @Test
    void checkDelHistory() {
        var task4 = new Task();
        task4.setName("Задача_4");
        task4.setDescription("Описание_4");
        task4.setStartTime(LocalDateTime.parse("04.02.2022 12:00", format));
        task4.setDuration(Duration.ofMinutes(60));
        var createdTask4 = taskManager.createTask(task4);

        var task5 = new Task();
        task5.setName("Задача_5");
        task5.setDescription("Описание_5");
        task5.setStartTime(LocalDateTime.parse("05.02.2022 12:00", format));
        task5.setDuration(Duration.ofMinutes(60));
        var createdTask5 = taskManager.createTask(task5);

        var task6 = new Task();
        task6.setName("Задача_6");
        task6.setDescription("Описание_6");
        task6.setStartTime(LocalDateTime.parse("06.02.2022 12:00", format));
        task6.setDuration(Duration.ofMinutes(60));
        var createdTask6 = taskManager.createTask(task6);

        var task7 = new Task();
        task7.setName("Задача_7");
        task7.setDescription("Описание_7");
        task7.setStartTime(LocalDateTime.parse("07.02.2022 12:00", format));
        task7.setDuration(Duration.ofMinutes(60));
        var createdTask7 = taskManager.createTask(task7);

        var task8 = new Task();
        task8.setName("Задача_8");
        task8.setDescription("Описание_8");
        task8.setStartTime(LocalDateTime.parse("07.02.2022 12:00", format));
        task8.setDuration(Duration.ofMinutes(60));
        var createdTask8 = taskManager.createTask(task8);

        historyManager.appendHistory(createdTask4);
        historyManager.appendHistory(createdTask5);
        historyManager.appendHistory(createdTask6);
        historyManager.appendHistory(createdTask7);
        historyManager.appendHistory(createdTask8);

        var history = historyManager.getHistory();
        assertNotNull(history, "История возвращает null.");
        assertFalse(history.isEmpty(), "Список задач в истории пуст.");
        assertEquals(5, history.size(), "Неверное количество задач в истории.");

        historyManager.remove(createdTask4.getId());
        var historyAfterRemoveFirst = historyManager.getHistory();
        assertEquals(4, historyAfterRemoveFirst.size(), "Неверное количество задач в истории.");

        historyManager.remove(createdTask8.getId());
        var historyAfterRemoveLast = historyManager.getHistory();
        assertEquals(3, historyAfterRemoveLast.size(), "Неверное количество задач в истории.");

        historyManager.remove(createdTask6.getId());
        var historyAfterRemoveMid = historyManager.getHistory();
        assertEquals(2, historyAfterRemoveMid.size(), "Неверное количество задач в истории.");
    }
}