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
        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var task2 = new Task();
        task2.setName("Задача 2");
        task2.setDescription("Описание задачи 2");
        task2.setStartTime(LocalDateTime.parse("01.01.2022 11:00", format));
        task2.setDuration(Duration.ofMinutes(60));
        var cT2 = taskManager.createTask(task2);

        var tasksList = taskManager.getAllTasks();
        assertNotNull(tasksList, "Метод вернул null");
        assertFalse(tasksList.isEmpty(), "Список задач пуст");
        assertEquals(2, tasksList.size(), "Неверное количество задач.");
        assertEquals(cT1, tasksList.get(0), "Задачи не совпадают.");
        assertEquals(cT2, tasksList.get(1), "Задачи не совпадают.");
    }

    @Test
    void checkGetAllSubTasks() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

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

        var subTasksList = taskManager.getAllSubTasks();
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

        var epic2 = new Epic();
        epic.setName("Эпик 2");
        epic.setDescription("Описание эпика 2");
        var cEp2 = taskManager.createEpic(epic2);

        var epicList = taskManager.getAllEpics();
        assertNotNull(epicList, "Метод вернул null");
        assertFalse(epicList.isEmpty(), "Список эпиков пуст");
        assertEquals(2, epicList.size(), "Неверное количество эпиков");
        assertEquals(cEp1, epicList.get(0), "Эпики не совпадают");
        assertEquals(cEp2, epicList.get(1), "Эпики не совпадают");
    }

    @Test
    void checkDeleteAllTasks() {
        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var task2 = new Task();
        task2.setName("Задача 2");
        task2.setDescription("Описание задачи 1");
        task2.setStartTime(LocalDateTime.parse("01.01.2022 11:00", format));
        task2.setDuration(Duration.ofMinutes(60));
        var cT2 = taskManager.createTask(task2);

        var tasksList = taskManager.getAllTasks();
        assertNotNull(tasksList, "Метод вернул null");
        assertFalse(tasksList.isEmpty(), "Список задач пуст");
        assertEquals(cT1, tasksList.get(0), "Задачи не совпадают");
        assertEquals(cT2, tasksList.get(1), "Задачи не совпадают");
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

        var subTasksList = taskManager.getAllSubTasks();
        assertNotNull(subTasksList, "Метод вернул null");
        assertFalse(subTasksList.isEmpty(), "Список задач пуст");
        assertEquals(cSt1, subTasksList.get(0), "Задачи не совпадают");
        assertEquals(cSt2, subTasksList.get(1), "Задачи не совпадают");
        assertEquals(cSt3, subTasksList.get(2), "Задачи не совпадают");
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

        var epic2 = new Epic();
        epic2.setName("Эпик 2");
        epic2.setDescription("Описание эпика 2");
        var cEp2 = taskManager.createEpic(epic2);

        var epicList = taskManager.getAllEpics();
        assertNotNull(epicList, "Метод вернул null");
        assertFalse(epicList.isEmpty(), "Список задач пуст");

        assertEquals(2, epicList.size(), "Неверное количество задач.");
        assertEquals(cEp1, epicList.get(0), "Задачи не совпадают");
        assertEquals(cEp2, epicList.get(1), "Задачи не совпадают");
        taskManager.deleteAllEpics();
        var epicListAfterDel = taskManager.getAllEpics();
        assertNotNull(epicListAfterDel, "Метод вернул null");
        assertTrue(epicListAfterDel.isEmpty(), "Список задач не пуст");
    }

    @Test
    void checkGetTaskById() {
        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var task2 = new Task();
        task2.setName("Задача 2");
        task2.setDescription("Описание задачи 2");
        task2.setStartTime(LocalDateTime.parse("01.01.2022 11:00", format));
        task2.setDuration(Duration.ofMinutes(60));
        var cT2 = taskManager.createTask(task2);

        var checkResult1 = taskManager.getTaskById(cT1.getId());
        assertNotNull(checkResult1, "Метод вернул null");
        assertEquals(cT1, checkResult1, "Задачи не совпадают.");
        var checkResult2 = taskManager.getTaskById(cT2.getId());
        assertNotNull(checkResult2, "Метод вернул null");
        assertEquals(cT2, checkResult2, "Задачи не совпадают.");
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.getTaskById(25)
        );
        assertNull(exception.getMessage());
    }

    @Test
    void checkGetSubTaskById() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

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

        var checkResult1 = taskManager.getSubTaskById(cSt1.getId());
        assertNotNull(checkResult1, "Метод вернул null");
        assertEquals(cSt1, checkResult1, "Подзадачи не совпадают.");

        var checkResult2 = taskManager.getSubTaskById(cSt2.getId());
        assertNotNull(checkResult2, "Метод вернул null");
        assertEquals(cSt2, checkResult2, "Подзадачи не совпадают.");
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.getSubTaskById(50)
        );
        assertNull(exception.getMessage());
    }

    @Test
    void checkGetEpicById() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

        var epic2 = new Epic();
        epic2.setName("Эпик 2");
        epic2.setDescription("Описание эпика 2");
        var cEp2 = taskManager.createEpic(epic2);

        var checkResult1 = taskManager.getEpicById(cEp1.getId());
        assertNotNull(checkResult1, "Метод вернул null");
        assertEquals(cEp1, checkResult1, "Эпики не совпадают.");

        var checkResult2 = taskManager.getEpicById(cEp2.getId());
        assertNotNull(checkResult2, "Метод вернул null");
        assertEquals(cEp2, checkResult2, "Эпики не совпадают.");
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.getEpicById(75)
        );
        assertNull(exception.getMessage());
    }

    @Test
    void checkCreateTask() {
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

        var tasksList = taskManager.getAllTasks();
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

        var subTasksList = taskManager.getAllSubTasks();
        assertNotNull(subTasksList, "Метод вернул null");
        assertNotNull(subTasksList, "Подзадачи не возвращаются.");

        final SameTimeTaskException exception = assertThrows(
                SameTimeTaskException.class,
                () -> taskManager.createSubTask(sub4)
        );
        assertEquals("Задачи пересекаются по времени выполнения.", exception.getMessage());
        assertEquals(3, subTasksList.size(), "Неверное количество подзадач.");
        assertEquals(cSt1, subTasksList.get(0), "Подзадачи не совпадают.");
        assertEquals(cSt2, subTasksList.get(1), "Подзадачи не совпадают.");
        assertEquals(cSt3, subTasksList.get(2), "Подзадачи не совпадают.");
    }

    @Test
    void checkCreateEpic() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

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

        var epicList = taskManager.getAllEpics();
        var checkResult = cEp1.getRelatedSubTasks();
        assertNotNull(epicList, "Эпики не возвращаются.");
        assertEquals(1, epicList.size(), "Неверное количество эпиков.");
        assertEquals(cEp1, epicList.get(0), "Эпики не совпадают.");
        assertEquals(List.of(cSt1, cSt2, cSt3), checkResult, "Подзадачи не принадлежат эпику");
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
        task1.setDuration(Duration.ofMinutes(30));
        var cT1 = taskManager.createTask(task1);

        var task2 = new Task();
        task2.setName("Задача 2");
        task2.setDescription("Описание задачи 2");
        task2.setStartTime(LocalDateTime.parse("01.01.2022 11:00", format));
        task2.setDuration(Duration.ofMinutes(30));
        var cT2 = taskManager.createTask(task2);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        taskManager.createSubTask(sub1);

        assertEquals(TaskStatus.NEW, cT1.getStatus(), "Некорректный статус задачи");
        cT1.setStatus(TaskStatus.DONE);
        taskManager.updateTask(cT1);
        assertEquals(TaskStatus.DONE, cT1.getStatus(), "Некорректный статус задачи");

        cT2.setDuration(Duration.ofMinutes(90));
        final SameTimeTaskException exception = assertThrows(
                SameTimeTaskException.class,
                () -> taskManager.updateTask(cT2)
        );
        assertEquals("Задачи пересекаются по времени выполнения.", exception.getMessage());
    }

    @Test
    void checkUpdateSubTask() {
        var epic = new Epic();
        epic.setName("Эпик 1");
        epic.setDescription("Описание эпика 1");
        var cEp1 = taskManager.createEpic(epic);

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

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        taskManager.createSubTask(sub1);

        var epic2 = new Epic();
        epic2.setName("Эпик 2");
        epic.setDescription("Описание эпика 1");
        var cEp2 = taskManager.createEpic(epic);

        var sub2 = new SubTask(cEp2.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("01.01.2022 14:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        assertEquals(TaskStatus.NEW, cEp1.getStatus(), "Некорректный статус эпика");
        cEp1.setStatus(TaskStatus.DONE);
        taskManager.updateEpic(cEp1);
        assertEquals(TaskStatus.DONE, cEp1.getStatus(), "Некорректный статус эпика");

        cSt2.setStartTime(LocalDateTime.parse("01.01.2022 12:30", format));
        cSt2.setDuration(Duration.ofMinutes(60));
        final SameTimeTaskException exception = assertThrows(
                SameTimeTaskException.class,
                () -> taskManager.updateSubTask(cSt2)
        );
        assertEquals("Задачи пересекаются по времени выполнения.", exception.getMessage());
    }

    @Test
    void checkDeleteTaskById() {
        var task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var tasksList = taskManager.getAllTasks();
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

        var subTasksList = taskManager.getAllSubTasks();
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

        var epicList = taskManager.getAllEpics();
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

        var checkResult = taskManager.getEpicSubTasks(cEp1);

        assertEquals(cSt1.getEpicId(), cEp1.getId(), "Подзадача не привязана к эпику");
        assertEquals(cSt2.getEpicId(), cEp1.getId(), "Подзадача не привязана к эпику");
        assertEquals(cSt3.getEpicId(), cEp1.getId(), "Подзадача не привязана к эпику");
        assertEquals(List.of(cSt1, cSt2, cSt3), checkResult, "Список подзадач эпика с ошибкой");
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
        taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cEp1.getId());
        sub2.setName("Подзадача 2");
        sub2.setDescription("Описание подзадачи 2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cEp1.getId());
        sub3.setName("Подзадача 3");
        sub3.setDescription("Описание подзадачи 3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        taskManager.getTaskById(cT1.getId());
        taskManager.getEpicById(cEp1.getId());
        taskManager.getSubTaskById(cSt3.getId());
        var checkResult = taskManager.getHistoryName();
        assertNotNull(checkResult, "История возвращает null.");
        assertFalse(checkResult.isEmpty(), "Список задач в истории пуст.");
        assertEquals(3, checkResult.size(), "Неверное количество задач в истории.");
        assertEquals(List.of(cT1, cEp1, cSt3), checkResult, "Неправильный порядок задач в истории");
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
        task1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        var cT1 = taskManager.createTask(task1);

        var sub1 = new SubTask(cEp1.getId());
        sub1.setName("Подзадача 1");
        sub1.setDescription("Описание подзадачи 1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
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

        var checkResult = taskManager.getPrioritizedTasks();

        assertNotNull(checkResult, "Метод возвращает null.");
        assertFalse(checkResult.isEmpty(), "Список приоритетных задач пуст.");
        assertEquals(4, checkResult.size(), "Неверное количество задач в отсортированном списке.");
        assertEquals(List.of(cSt1, cT1, cSt2, cSt3), checkResult, "Задачи неправильно отсортированы");
    }
}