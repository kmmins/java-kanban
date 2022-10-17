package ru.yandex.taskTracker;
import ru.yandex.taskTracker.service.TestService;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TestService testService = new TestService();

        System.out.println("**********Таск Треккер!**********");

        testService.createTask();
        testService.getAll();
        testService.createTask();
        testService.getAll();
        testService.updateTask();
        testService.getAll();
        testService.delTask();
        testService.getAll();
    }
}

/*Создайте в классе Main метод static void main(String[] args) и внутри него:
Создайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей.
Распечатайте списки эпиков, задач и подзадач, через System.out.println(..)
Измените статусы созданных объектов, распечатайте. Проверьте, что статус задачи и подзадачи сохранился,
а статус эпика рассчитался по статусам подзадач.
И, наконец, попробуйте удалить одну из задач и один из эпиков.*/