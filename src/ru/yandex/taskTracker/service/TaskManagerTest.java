package ru.yandex.taskTracker.service;

import org.junit.jupiter.api.Test;
import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Test // 2 abstract
    void checkSubTuskRelatedAndEpicStatus(){

        var epic1 = new Epic();
        epic1.setName("Эпик_1");
        epic1.setDescription("Описание_1");
        var cE1 = taskManager.createEpic(epic1);

        var sub1 = new SubTask(cE1.getId());
        sub1.setName("Саб_1");
        sub1.setDescription("Саб_описание_1");
        sub1.setStartTime(LocalDateTime.parse("01.01.2022 12:00", format));
        sub1.setDuration(Duration.ofMinutes(60));
        var cSt1 = taskManager.createSubTask(sub1);

        var sub2 = new SubTask(cE1.getId());
        sub2.setName("Саб_2");
        sub2.setDescription("Саб_описание_2");
        sub2.setStartTime(LocalDateTime.parse("02.01.2022 12:00", format));
        sub2.setDuration(Duration.ofMinutes(60));
        var cSt2 = taskManager.createSubTask(sub2);

        var sub3 = new SubTask(cE1.getId());
        sub3.setName("Саб_3");
        sub3.setDescription("Саб_описание_3");
        sub3.setStartTime(LocalDateTime.parse("03.01.2022 12:00", format));
        sub3.setDuration(Duration.ofMinutes(60));
        var cSt3 = taskManager.createSubTask(sub3);

        cSt1.setStatus(TaskStatus.DONE);
        cSt2.setStatus(TaskStatus.IN_PROGRESS);
        cSt3.setStatus(TaskStatus.NEW);

        taskManager.updateSubTask(cSt1);
        taskManager.updateSubTask(cSt2);
        taskManager.updateSubTask(cSt3);

        assertEquals(cE1.getId(), cSt1.getEpicId(), "Подзадача не привязана к эпику");
        assertEquals(cE1.getId(), cSt2.getEpicId(), "Подзадача не привязана к эпику");
        assertEquals(cE1.getId(), cSt3.getEpicId(), "Подзадача не привязана к эпику");
        assertEquals(cE1.getRelatedSubTasks(), List.of(cSt1, cSt2, cSt3), "Список подзадач эпика с ошибкой");
        assertEquals(TaskStatus.IN_PROGRESS, cE1.getStatus(), "Некорректно рассчитан статус эпика");
    }

    @Test
    void check1(){

    }
}