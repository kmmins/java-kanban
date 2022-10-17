package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;

import java.util.Scanner;

public class TestService {

    TaskManager taskManager = new TaskManager();
    Scanner scannerInt = new Scanner(System.in);
    Scanner scannerLine = new Scanner(System.in);

    public void runTest() {
        imOutOfHere:
        while (true) {
            printTestMenu();
            int command = scannerInt.nextInt();
            switch (command) {
                case 1:
                    getAll();
                    break;
                case 2:
                    delAll();
                    break;
                case 3:
                    break;
                case 4:
                    createTask();
                    break;
                case 5:
                    updateTask();
                    break;
                case 6:
                    delTask();
                    break;
                case 7:
                    break;
                case 0:
                    System.out.println("Выход из меню...");
                    break imOutOfHere;
                default:
                    System.out.println("Извините, такой команды пока нет");
            }
        }
    }

    public void getAll() {
        System.out.println("Здесь хранятся задачи (tasks): ");
        System.out.println(taskManager.getAllTasks());
        System.out.println("Здесь хранятся маленькие задачи (subTasks): ");
        System.out.println(taskManager.getAllSubTasks());
        System.out.println("Здесь хранятся большие задачи (epicTasks): ");
        System.out.println(taskManager.getAllEpics());
    }

    public void delAll() {
        taskManager.deleteAllTasks();
        taskManager.deleteAllSubTasks();
        taskManager.deleteAllEpics();
        System.out.println("Все задачи удалены");
    }

    public void createTask() {
        imOutOfHere:
        while (true) {
            System.out.println("1 - создать маленькую задачи\r\n2 - создать большую задачу\r\n0 - Вернутся назад");
            int command;
            command = scannerInt.nextInt();
            switch (command) {
                case 1:
                    System.out.println("К какой большой задаче относиться создаваемая маленькая задача? Введите ID: ");
                    SubTask subTask = new SubTask(scannerInt.nextInt());

                    System.out.println("Введите имя маленькой задачи: ");
                    subTask.setName(scannerLine.nextLine());
                    System.out.println("Введите описание маленькой задачи: ");
                    subTask.setDescription(scannerLine.nextLine());

                    taskManager.createSubTask(subTask);
                    System.out.println("Создана маленькая задача: имя " + subTask.getName() + ", описание: "
                            + subTask.getDescription() + ", статус: " + subTask.getStatus());
                    break;
                case 2:
                    Epic epicTask = new Epic();
                    System.out.println("Введите имя большой задачи: ");
                    String name = scannerLine.nextLine();
                    epicTask.setName(name);
                    System.out.println("Введите описание большой задачи: ");
                    epicTask.setDescription(scannerLine.nextLine());

                    taskManager.createEpic(epicTask);
                    System.out.println("Создана большая задача: " + epicTask.getName()
                            + ", описание: " + epicTask.getDescription() + ", статус: " + epicTask.getStatus());
                    break;
                case 0:
                    System.out.println("Выход из меню...");
                    break imOutOfHere;
                default:
                    System.out.println("Извините, такой команды пока нет");
                    break;
            }
        }
    }

    public void updateTask() {
        imOutOfHere:
        while (true) {
            System.out.println("1 - изменить статус маленькой задачи\r\n0 - Вернутся назад");
            int command;
            command = scannerInt.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Какую маленькую задачу изменяем? Введите ID: ");
                    int idSubTask = scannerInt.nextInt();
                    SubTask subTask = taskManager.getSubTaskById(idSubTask);
                    System.out.println("Изменить статус задачи\r\n(1 - Новая задача, 2 - В процессе выполнения, "
                            + "3 - Выполнено)");
                    int status = scannerInt.nextInt();
                    switch (status) {
                        case 1: // (NEW)
                            subTask.setStatus("NEW");
                            break;
                        case 2: // (IN_PROGRESS)
                            subTask.setStatus("IN_PROGRESS");
                            break;
                        case 3: // (DONE)
                            subTask.setStatus("DONE");
                            break;
                        default:
                            System.out.println("Извините, такой команды пока нет");
                            break;
                    }
                    //subTask.setStatus(scannerLine.nextLine());
                    //taskManager.updateSubTask(subTask);
                    break;
                case 0:
                    System.out.println("Выход из меню...");
                    break imOutOfHere;
                default:
                    System.out.println("Извините, такой команды пока нет");
                    break;
            }
        }
    }

    public void delTask() {
        imOutOfHere:
        while (true) {
            System.out.println("1 - удалить маленькую задачу\r\n2 - удалить большую задачу\r\n0 - Вернутся назад");
            int command;
            command = scannerInt.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Какую маленькую задачу удалить? Введите ID: ");
                    int idSubTask = scannerInt.nextInt();
                    taskManager.deleteSubTaskById(idSubTask);
                    break;
                case 2:
                    System.out.println("Какуой большую задачу удалить? Введите ID: ");
                    int idEpicTask = scannerInt.nextInt();
                    taskManager.deleteEpicById(idEpicTask);
                    break;
                case 0:
                    System.out.println("Выход...");
                    break imOutOfHere;
                default:
                    System.out.println("Извините, такой команды пока нет");
                    break;
            }
        }
    }

    public static void printTestMenu() {
        System.out.println("Выберете действие: ");
        System.out.println("1 - Получение списка всех задач");
        System.out.println("2 - Удаление всех задач");
        System.out.println("3 - Получение задачи по идентификатору (ПОКА НЕ ГОТОВО)");
        System.out.println("4 - Создание задачи");
        System.out.println("5 - Обновление задачи");
        System.out.println("6 - Удаление задачи по идентификатору");
        System.out.println("7 - Получение списка всех подзадач определённого эпика (ПОКА НЕ ГОТОВО)");
        System.out.println("0 - Вернутся назад");
    }
}

/*Создайте в классе Main метод static void main(String[] args) и внутри него:
Создайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей.
Распечатайте списки эпиков, задач и подзадач, через System.out.println(..)
Измените статусы созданных объектов, распечатайте. Проверьте, что статус задачи и подзадачи сохранился,
а статус эпика рассчитался по статусам подзадач.
И, наконец, попробуйте удалить одну из задач и один из эпиков.*/