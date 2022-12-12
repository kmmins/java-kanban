package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class TestService {
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private TaskManager taskManager = Managers.getFileBacked();

    private Scanner scanner = new Scanner(System.in);

    public TestService() throws ManagerSaveException {
    }

    public void runTest() {
        outOfHere:
        while (true) {
            printTestMenu();
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    getAll();
                    break;
                case 2:
                    delAll();
                    break;
                case 3:
                    getById();
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
                    getSubTaskByEpic();
                    break;
                case 8:
                    getH();
                    break;
                case 0:
                    System.out.println("Выход из меню...");
                    break outOfHere;
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
        outOfHere:
        while (true) {
            System.out.println("1 - создать большую задачу\r\n2 - создать маленькую задачу\r\n0 - Вернутся назад");
            int command;
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    Epic epicTask = new Epic();
                    System.out.println("Введите имя большой задачи: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    epicTask.setName(name);
                    System.out.println("Введите описание большой задачи: ");
                    epicTask.setDescription(scanner.nextLine());

                    taskManager.createEpic(epicTask);
                    System.out.println("Создана большая задача: " + epicTask.getName()
                            + ", описание: " + epicTask.getDescription() + ", статус: " + epicTask.getStatus());
                    break;
                case 2:
                    try {
                        System.out.println("К какой большой задаче относиться создаваемая маленькая задача? Введите ID: ");
                        SubTask subTask = new SubTask(scanner.nextInt());
                        scanner.nextLine();
                        System.out.println("Введите имя маленькой задачи: ");
                        subTask.setName(scanner.nextLine());
                        System.out.println("Введите описание маленькой задачи: ");
                        subTask.setDescription(scanner.nextLine());
                        System.out.println("Введите дату начала вида 'dd.MM.yyyy HH:mm': ");
                        subTask.setStartTime(LocalDateTime.parse(scanner.nextLine(), format));
                        System.out.println("Введите продолжительность задачи в мин: ");
                        subTask.setDuration(Duration.ofMinutes(Integer.parseInt(scanner.nextLine())));

                        taskManager.createSubTask(subTask);
                        System.out.println("Создана маленькая задача: имя " + subTask.getName() + ", описание: "
                                + subTask.getDescription() + ", статус: " + subTask.getStatus());
                        break;
                    } catch (NullPointerException e) {
                        System.out.println("Ошибка! Такую задачу невозможно создать.\r\n" +
                                "Маленькая задача обязательно должна быть частью большой задачи!");
                        continue;
                    }
                case 0:
                    System.out.println("Выход из меню...");
                    break outOfHere;
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
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Какую маленькую задачу изменяем? Введите ID: ");
                    int idSubTask = scanner.nextInt();
                    SubTask subTask = taskManager.getSubTaskById(idSubTask);
                    System.out.println("Изменить статус задачи\r\n1 - Новая задача\r\n2 - В процессе выполнения\r\n"
                            + "3 - Выполнено");
                    int status = scanner.nextInt();
                    switch (status) {
                        case 1:
                            subTask.setStatus(TaskStatus.NEW);
                            taskManager.updateSubTask(subTask);
                            break;
                        case 2:
                            subTask.setStatus(TaskStatus.IN_PROGRESS);
                            taskManager.updateSubTask(subTask);
                            break;
                        case 3:
                            subTask.setStatus(TaskStatus.DONE);
                            taskManager.updateSubTask(subTask);
                            break;
                        default:
                            System.out.println("Извините, такой команды пока нет");
                            break;
                    }
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
            System.out.println("1 - удалить большую задачу\r\n2 - удалить маленькую задачу\r\n0 - Вернутся назад");
            int command;
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    try {
                        System.out.println("Какую большую задачу удалить? Введите ID: ");
                        int idEpicTask = scanner.nextInt();
                        taskManager.deleteEpicById(idEpicTask);
                        break;
                    } catch (NullPointerException e) {
                        System.out.println("Ошибка! Нет такого id большой задачи");
                        continue;
                    }
                case 2:
                    try {
                        System.out.println("Какую маленькую задачу удалить? Введите ID: ");
                        int idSubTask = scanner.nextInt();
                        taskManager.deleteSubTaskById(idSubTask);
                        break;
                    } catch (NullPointerException e) {
                        System.out.println("Ошибка! Нет такого id маленькой задачи");
                        continue;
                    }
                case 0:
                    System.out.println("Выход из меню...");
                    break imOutOfHere;
                default:
                    System.out.println("Извините, такой команды пока нет");
                    break;
            }
        }
    }

    public void getById() {
        imOutOfHere:
        while (true) {
            System.out.println("Какую задачу нужно получить?\r\n1 - Большую\r\n2 - Маленькую\r\n0 - Вернутся назад");
            int command = scanner.nextInt();

            switch (command) {
                case 1:
                    try {
                        System.out.println("Введите id: ");
                        int idSmallOrBig = scanner.nextInt();
                        System.out.println("Большие задачи: ");
                        System.out.println(taskManager.getEpicById(idSmallOrBig));
                        break;
                    } catch (NullPointerException e) {
                        System.out.println("Ошибка! Нет такого id большой задачи");
                    }
                    continue;
                case 2:
                    try {
                        System.out.println("Введите id: ");
                        int idSmallOrBig = scanner.nextInt();
                        System.out.println("Маленькие задачи: ");
                        System.out.println(taskManager.getSubTaskById(idSmallOrBig));
                        break;
                    } catch (NullPointerException e) {
                        System.out.println("Ошибка! Нет такого id маленькой задачи");
                    }
                    continue;
                case 0:
                    System.out.println("Выход из меню...");
                    break imOutOfHere;
                default:
                    System.out.println("Извините, такой команды пока нет");
                    break;
            }
        }
    }

    public void getSubTaskByEpic() {
        ArrayList<SubTask> output;
        System.out.println("Для какой большой задачи нужно получить маленькие задачи? Введите ID: ");
        int idEpicTask = scanner.nextInt();
        output = taskManager.getEpicSubTasks(taskManager.getEpicById(idEpicTask));
        System.out.println("Для большой задачи: " + taskManager.getEpicById(idEpicTask)
                + " Получены маленькие задачи :" + output);
    }

    public void getH() {
        System.out.println("История задач: " + taskManager.getHistoryName() + " храниться тут");
    }

    public static void printTestMenu() {
        System.out.println("Выберете действие: ");
        System.out.println("1 - Получение списка всех задач");
        System.out.println("2 - Удаление всех задач");
        System.out.println("3 - Получение задачи по идентификатору");
        System.out.println("4 - Создание задачи");
        System.out.println("5 - Обновление задачи");
        System.out.println("6 - Удаление задачи по идентификатору");
        System.out.println("7 - Получение списка всех подзадач определённого эпика");
        System.out.println("8 - Получить историю");
        System.out.println("0 - Вернутся назад");
    }
}