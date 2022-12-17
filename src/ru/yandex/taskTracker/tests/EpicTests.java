package ru.yandex.taskTracker.tests;

import org.junit.jupiter.api.Test;
import ru.yandex.taskTracker.model.*;
import ru.yandex.taskTracker.service.ManagerSaveException;
import ru.yandex.taskTracker.service.Managers;
import ru.yandex.taskTracker.service.SameTimeTaskException;
import ru.yandex.taskTracker.service.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.taskTracker.model.TaskStatus.*;

public class EpicTests {
    private final TaskManager taskManager = Managers.getDefault();
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public EpicTests() throws ManagerSaveException, SameTimeTaskException {
    }

    @Test
    void checkEpicStatusEmpty() {
        var epic1 = new Epic();
        epic1.setName("Эпик_1");
        epic1.setDescription("Описание_1");

        var createdEpic1 = taskManager.createEpic(epic1);
        var epics = taskManager.getAllEpics();

        assertNotNull(epics, "Эпики не возвращаются.");
        assertFalse(epics.isEmpty(), "Список эпиков пуст");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(createdEpic1, epics.get(0), "Эпики не совпадают.");
        assertEquals(NEW, createdEpic1.getStatus(), "Некорректно рассчитан статус эпика");
    }

    @Test
    void checkEpicStatusNewSubTask() {
        var epic2 = new Epic();
        epic2.setName("Эпик_2");
        epic2.setDescription("Описание_2");
        var createdEpic2 = taskManager.createEpic(epic2);
        var sub1 = new SubTask(createdEpic2.getId());
        sub1.setName("Саб_1");
        sub1.setDescription("Саб_описание_1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var sub2 = new SubTask(createdEpic2.getId());
        sub2.setName("Саб_2");
        sub2.setDescription("Саб_описание_2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var sub3 = new SubTask(createdEpic2.getId());
        sub3.setName("Саб_3");
        sub3.setDescription("Саб_описание_3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));

        taskManager.createSubTask(sub1);
        taskManager.createSubTask(sub2);
        taskManager.createSubTask(sub2);
        var subTasks = taskManager.getAllSubTasks();

        assertNotNull(subTasks, "Подзадачи не возвращаются.");
        assertFalse(subTasks.isEmpty(), "Список подзадач пуст");
        assertEquals(3, subTasks.size(), "Неверное количество подзадач.");
        assertEquals(NEW, createdEpic2.getStatus(), "Некорректно рассчитан статус эпика");
    }

    @Test
    void checkEpicStatusDoneSubTask() {
        var epic3 = new Epic();
        epic3.setName("Эпик_3");
        epic3.setDescription("Описание_3");
        var createdEpic3 = taskManager.createEpic(epic3);
        var sub4 = new SubTask(createdEpic3.getId());
        sub4.setName("Саб_4");
        sub4.setDescription("Саб_описание_4");
        sub4.setStartTime(LocalDateTime.parse("04.01.2022 12:00", format));
        sub4.setDuration(Duration.ofMinutes(60));
        var createdSubTask4 = taskManager.createSubTask(sub4);
        var sub5 = new SubTask(createdEpic3.getId());
        sub5.setName("Саб_5");
        sub5.setDescription("Саб_описание_5");
        sub5.setStartTime(LocalDateTime.parse("05.01.2022 12:00", format));
        sub5.setDuration(Duration.ofMinutes(60));
        var createdSubTask5 = taskManager.createSubTask(sub5);
        var sub6 = new SubTask(createdEpic3.getId());
        sub6.setName("Саб_6");
        sub6.setDescription("Саб_описание_6");
        sub6.setStartTime(LocalDateTime.parse("06.01.2022 12:00", format));
        sub6.setDuration(Duration.ofMinutes(60));
        var createdSubTask6 = taskManager.createSubTask(sub6);

        createdSubTask4.setStatus(TaskStatus.DONE);
        createdSubTask5.setStatus(TaskStatus.DONE);
        createdSubTask6.setStatus(TaskStatus.DONE);
        taskManager.updateSubTask(createdSubTask4);
        taskManager.updateSubTask(createdSubTask5);
        taskManager.updateSubTask(createdSubTask6);
        var subTasks = taskManager.getAllSubTasks();

        assertEquals(createdSubTask4, subTasks.get(0), "Подзадачи не совпадают.");
        assertEquals(createdSubTask5, subTasks.get(1), "Подзадачи не совпадают.");
        assertEquals(createdSubTask6, subTasks.get(2), "Подзадачи не совпадают.");
        assertEquals(DONE, createdEpic3.getStatus(), "Некорректно рассчитан статус эпика");
    }

    @Test
    void checkEpicStatusNewDoneSubTask() {
        var epic4 = new Epic();
        epic4.setName("Эпик_4");
        epic4.setDescription("Описание_4");
        var createdEpic4 = taskManager.createEpic(epic4);
        var sub7 = new SubTask(createdEpic4.getId());
        sub7.setName("Саб_7");
        sub7.setDescription("Саб_описание_7");
        sub7.setStartTime(LocalDateTime.parse("07.01.2022 12:00", format));
        sub7.setDuration(Duration.ofMinutes(60));
        var createdSubTask7 = taskManager.createSubTask(sub7);
        var sub8 = new SubTask(createdEpic4.getId());
        sub8.setName("Саб_8");
        sub8.setDescription("саб_описание_8");
        sub8.setStartTime(LocalDateTime.parse("08.01.2022 12:00", format));
        sub8.setDuration(Duration.ofMinutes(60));
        var createdSubTask8 = taskManager.createSubTask(sub8);
        var sub9 = new SubTask(createdEpic4.getId());
        sub9.setName("Саб_9");
        sub9.setDescription("Саб_описание_9");
        sub9.setStartTime(LocalDateTime.parse("09.01.2022 12:00", format));
        sub9.setDuration(Duration.ofMinutes(60));
        var createdSubTask9 = taskManager.createSubTask(sub9);

        createdSubTask7.setStatus(TaskStatus.DONE);
        createdSubTask8.setStatus(TaskStatus.NEW);
        createdSubTask9.setStatus(TaskStatus.NEW);
        taskManager.updateSubTask(createdSubTask7);
        taskManager.updateSubTask(createdSubTask8);
        taskManager.updateSubTask(createdSubTask9);

        assertEquals(IN_PROGRESS, createdEpic4.getStatus(), "Некорректно рассчитан статус эпика");
    }

    @Test
    void checkEpicStatusInProgressSubTask() {
        var epic5 = new Epic();
        epic5.setName("Эпик_5");
        epic5.setDescription("Описание_5");
        var createdEpic5 = taskManager.createEpic(epic5);
        var sub10 = new SubTask(createdEpic5.getId());
        sub10.setName("Саб_10");
        sub10.setDescription("Саб_описание_10");
        sub10.setStartTime(LocalDateTime.parse("10.01.2022 12:00", format));
        sub10.setDuration(Duration.ofMinutes(60));
        var createdSubTask10 = taskManager.createSubTask(sub10);
        var sub11 = new SubTask(createdEpic5.getId());
        sub11.setName("Саб_11");
        sub11.setDescription("Саб_описание_11");
        sub11.setStartTime(LocalDateTime.parse("11.01.2022 12:00", format));
        sub11.setDuration(Duration.ofMinutes(60));
        var createdSubTask11 = taskManager.createSubTask(sub11);
        var sub12 = new SubTask(createdEpic5.getId());
        sub12.setName("Саб_12");
        sub12.setDescription("Саб_описание_12");
        sub12.setStartTime(LocalDateTime.parse("12.01.2022 12:00", format));
        sub12.setDuration(Duration.ofMinutes(60));
        var createdSubTask12 = taskManager.createSubTask(sub12);

        createdSubTask10.setStatus(TaskStatus.IN_PROGRESS);
        createdSubTask11.setStatus(TaskStatus.IN_PROGRESS);
        createdSubTask12.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubTask(createdSubTask10);
        taskManager.updateSubTask(createdSubTask11);
        taskManager.updateSubTask(createdSubTask12);

        assertEquals(IN_PROGRESS, createdEpic5.getStatus(), "Некорректно рассчитан статус эпика");
    }
}