package ru.yandex.taskTracker.service;

import org.junit.jupiter.api.Test;
import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.model.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.taskTracker.model.TaskStatus.*;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Test
    void checkSubTuskRelatedAndEpicStatus() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        cSt1.setStatus(TaskStatus.DONE);
        cSt2.setStatus(IN_PROGRESS);
        cSt3.setStatus(TaskStatus.NEW);

        taskManager.updateSubTask(cSt1);
        taskManager.updateSubTask(cSt2);
        taskManager.updateSubTask(cSt3);

        assertEquals(cSt1.getEpicId(), cEp1.getId(), "Подзадача не привязана к эпику");
        assertEquals(cSt2.getEpicId(), cEp1.getId(), "Подзадача не привязана к эпику");
        assertEquals(cSt3.getEpicId(), cEp1.getId(), "Подзадача не привязана к эпику");
        assertEquals(List.of(cSt1, cSt2, cSt3), cEp1.getRelatedSubTasks(), "Список подзадач эпика с ошибкой");
        assertEquals(IN_PROGRESS, cEp1.getStatus(), "Некорректно рассчитан статус эпика");
    }

    @Test
    void checkGetAllTasks() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        assertNotNull(tasksList, "Метод вернул null");
        assertFalse(tasksList.isEmpty(), "Список задач пуст");
        assertEquals(1, tasksList.size(), "Неверное количество задач.");
        assertEquals(cT1, tasksList.get(0), "Задачи не совпадают.");
    }

    @Test
    void checkGetAllSubTasks() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        assertNotNull(subTasksList, "Метод вернул null");
        assertFalse(subTasksList.isEmpty(), "Список подзадач пуст");
        assertEquals(3, subTasksList.size(), "Неверное количество подзадач");
        assertEquals(cSt1, subTasksList.get(0), "Подзадачи не совпадают");
        assertEquals(cSt2, subTasksList.get(1), "Подзадачи не совпадают");
        assertEquals(cSt3, subTasksList.get(2), "Подзадачи не совпадают");
    }

    @Test
    void checkGetAllEpics() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        assertNotNull(epicList, "Метод вернул null");
        assertFalse(epicList.isEmpty(), "Список эпиков пуст");
        assertEquals(1, epicList.size(), "Неверное количество эпиков");
        assertEquals(cEp1, epicList.get(0), "Эпики не совпадают");
    }

    @Test
    void checkDeleteAllTasks() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        taskManager.deleteAllTasks();
        var tasksListAfterDel = taskManager.getAllTasks();
        assertNotNull(tasksListAfterDel, "Метод вернул null");
        assertTrue(tasksListAfterDel.isEmpty(), "Список задач не пуст");
    }

    @Test
    void checkDeleteAllSubTasks() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        assertEquals(3, subTasksList.size(), "Неверное количество задач.");
        taskManager.deleteAllSubTasks();
        var subTasksListAfterDel = taskManager.getAllSubTasks();
        assertNotNull(subTasksListAfterDel, "Метод вернул null");
        assertTrue(subTasksListAfterDel.isEmpty(), "Список задач не пуст");
    }

    @Test
    void checkDeleteAllEpics() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        assertEquals(1, epicList.size(), "Неверное количество задач.");
        taskManager.deleteAllEpics();
        var epicListAfterDel = taskManager.getAllEpics();
        assertNotNull(epicListAfterDel, "Метод вернул null");
        assertTrue(epicListAfterDel.isEmpty(), "Список задач не пуст");
    }

    @Test
    void checkGetTaskById() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var result = taskManager.getTaskById(cT1.getId());
        assertNotNull(result, "Метод вернул null");
        assertEquals(cT1, result, "Задачи не совпадают.");
    }

    @Test
    void checkGetSubTaskById() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var result = taskManager.getSubTaskById(cSt1.getId());
        assertNotNull(result, "Метод вернул null");
        assertEquals(cSt1, result, "Подзадачи не совпадают.");
    }

    @Test
    void checkGetEpicById() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var result = taskManager.getEpicById(cEp1.getId());
        assertNotNull(result, "Метод вернул null");
        assertEquals(cEp1, result, "Эпики не совпадают.");
    }

    @Test
    void checkCreateTask() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var task2 = new Task();
        task2.setName("Задача 2");
        task2.setDescription("Описание задачи 2");
        task2.setStartTime(LocalDateTime.parse("01.01.2022 10:30", format));
        task2.setDuration(Duration.ofMinutes(60));

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var savedTask = taskManager.getTaskById(cT1.getId());
        assertNotNull(savedTask, "Метод вернул null");
        assertEquals(cT1, savedTask, "Задачи не совпадают.");
        assertNotNull(tasksList, "Задачи не возвращаются.");

        final SameTimeTaskException exception = assertThrows(
                SameTimeTaskException.class,
                () -> taskManager.createTask(task2)
        );
        assertEquals("Задачи пересекаются по времени выполнения.", exception.getMessage());
        assertEquals(1, tasksList.size(), "Неверное количество задач.");
        assertEquals(cT1, tasksList.get(0), "Задачи не совпадают.");
    }

    @Test
    void checkCreateSubTask() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var sub4 = new SubTask(cEp1.getId());
        sub4.setName("Подзадача 4");
        sub4.setDescription("Описание подзадачи 4");
        sub4.setStartTime(LocalDateTime.parse("03.01.2022 12:15", format));
        sub4.setDuration(Duration.ofMinutes(60));

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var savedSubTask = taskManager.getSubTaskById(cSt1.getId());
        assertNotNull(savedSubTask, "Метод вернул null");
        assertEquals(cSt1, savedSubTask, "Подзадачи не совпадают.");
        assertNotNull(subTasksList, "Подзадачи не возвращаются.");

        final SameTimeTaskException exception = assertThrows(
                SameTimeTaskException.class,
                () -> taskManager.createSubTask(sub4)
        );
        assertEquals("Задачи пересекаются по времени выполнения.", exception.getMessage());
        assertEquals(3, subTasksList.size(), "Неверное количество подзадач.");
        assertEquals(cSt1, subTasksList.get(0), "Подзадачи не совпадают.");
    }

    @Test
    void checkCreateEpic() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var savedEpic = taskManager.getEpicById(cEp1.getId());
        assertNotNull(savedEpic, "Метод вернул null");
        assertEquals(cEp1, savedEpic, "Эпики не совпадают.");
        assertNotNull(epicList, "Эпики не возвращаются.");
        assertEquals(1, epicList.size(), "Неверное количество эпиков.");
        assertEquals(cEp1, epicList.get(0), "Эпики не совпадают.");
    }

    @Test
    void checkUpdateTask() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        assertEquals(TaskStatus.NEW, cT1.getStatus(), "Некорректный статус задачи");
        cT1.setStatus(TaskStatus.DONE);
        taskManager.updateTask(cT1);
        assertEquals(TaskStatus.DONE, cT1.getStatus(), "Некорректный статус задачи");
    }

    @Test
    void checkUpdateSubTask() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        assertEquals(NEW, cSt1.getStatus(), "Некорректный статус подзадачи");
        assertEquals(NEW, cSt2.getStatus(), "Некорректный статус подзадачи");
        assertEquals(NEW, cSt3.getStatus(), "Некорректный статус подзадачи");
        cSt1.setStatus(TaskStatus.DONE);
        cSt2.setStatus(TaskStatus.IN_PROGRESS);
        cSt3.setStatus(TaskStatus.DONE);
        taskManager.updateTask(cSt1);
        taskManager.updateTask(cSt2);
        taskManager.updateTask(cSt3);
        assertEquals(TaskStatus.DONE, cSt1.getStatus(), "Некорректный статус подзадачи");
        assertEquals(TaskStatus.IN_PROGRESS, cSt2.getStatus(), "Некорректный статус подзадачи");
        assertEquals(TaskStatus.DONE, cSt3.getStatus(), "Некорректный статус подзадачи");
    }

    @Test
    void checkUpdateEpic() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        assertEquals(TaskStatus.NEW, cEp1.getStatus(), "Некорректный статус эпика");
        cEp1.setStatus(TaskStatus.DONE);
        taskManager.updateEpic(cEp1);
        assertEquals(TaskStatus.DONE, cEp1.getStatus(), "Некорректный статус эпика");
    }

    @Test
    void checkDeleteTaskById() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        assertNotNull(tasksList, "Метод вернул null");
        assertFalse(tasksList.isEmpty(), "Список задач пуст");
        assertEquals(1, tasksList.size(), "Неверное количество задач.");
        assertEquals(cT1, tasksList.get(0), "Задачи не совпадают.");
        taskManager.deleteTaskById(cT1.getId());
        var tasksListAfterDel = taskManager.getAllTasks();
        assertNotNull(tasksListAfterDel, "Метод вернул null");
        assertTrue(tasksListAfterDel.isEmpty(), "Список задач не пуст");
    }

    @Test
    void checkDeleteSubTaskById() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        assertNotNull(subTasksList, "Метод вернул null");
        assertFalse(subTasksList.isEmpty(), "Список подзадач пуст");
        assertEquals(3, subTasksList.size(), "Неверное количество подзадач.");

        taskManager.deleteSubTaskById(cSt2.getId());
        var subTasksListAfterDel = taskManager.getAllSubTasks();
        assertNotNull(subTasksListAfterDel, "Метод вернул null");
        assertFalse(subTasksListAfterDel.isEmpty(), "Список подзадач пуст");
        assertEquals(2, subTasksListAfterDel.size(), "Неверное количество подзадач.");
        assertEquals(cSt1, subTasksListAfterDel.get(0), "Подзадачи не совпадают.");
        assertEquals(cSt3, subTasksListAfterDel.get(1), "Подзадачи не совпадают.");

    }

    @Test
    void checkDeleteEpicById() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        assertNotNull(epicList, "Метод вернул null");
        assertFalse(epicList.isEmpty(), "Список эпиков пуст");
        assertEquals(1, epicList.size(), "Неверное количество эпиков.");

        taskManager.deleteEpicById(cEp1.getId());
        var EpicListAfterDel = taskManager.getAllEpics();
        assertNotNull(EpicListAfterDel, "Метод вернул null");
        assertTrue(EpicListAfterDel.isEmpty(), "Список эпиков не пуст");
    }

    @Test
    void checkGetEpicSubTasks() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var result = taskManager.getEpicSubTasks(cEp1);

        assertEquals(cSt1.getEpicId(), cEp1.getId(), "Подзадача не привязана к эпику");
        assertEquals(cSt2.getEpicId(), cEp1.getId(), "Подзадача не привязана к эпику");
        assertEquals(cSt3.getEpicId(), cEp1.getId(), "Подзадача не привязана к эпику");
        assertEquals(List.of(cSt1, cSt2, cSt3), result, "Список подзадач эпика с ошибкой");
    }

    @Test
    void checkGetHistoryName() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        taskManager.getTaskById(cT1.getId());
        taskManager.getEpicById(cEp1.getId());
        taskManager.getSubTaskById(cSt3.getId());
        var result = taskManager.getHistoryName();
        assertNotNull(result, "История возвращает null.");
        assertFalse(result.isEmpty(), "Список задач в истории пуст.");
        assertEquals(3, result.size(), "Неверное количество задач в истории.");
    }

    @Test
    void checkGetPrioritizedTasks() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        var tasksList = taskManager.getAllTasks();
        var subTasksList = taskManager.getAllSubTasks();
        var epicList = taskManager.getAllEpics();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var result = taskManager.getPrioritizedTasks();

        assertNotNull(result, "Метод возвращает null.");
        assertFalse(result.isEmpty(), "Список приоритетных задач пуст.");
        assertEquals(4, result.size(), "Неверное количество задач в истории.");
    }
}