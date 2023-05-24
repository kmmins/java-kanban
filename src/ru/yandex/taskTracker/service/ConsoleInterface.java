package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleInterface {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final TaskManager taskManager;
    private final Scanner scanner;

    public ConsoleInterface() throws ManagerSaveException, SameTimeTaskException {
        taskManager = Managers.getHttp();
        scanner = new Scanner(System.in);
    }

    public void startMenu() {
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
                case 9:
                    getP();
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
        System.out.println("Здесь хранятся подзадачи (subTasks): ");
        System.out.println(taskManager.getAllSubTasks());
        System.out.println("Здесь хранятся эпик задачи (epicTasks): ");
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
            System.out.println("1 - Создать эпик задачу\r\n2 - Создать подзадачу\r\n3 - Создать задачу" +
                    "\r\n0 - Вернутся назад");
            int command;
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    Epic epicTask = new Epic();
                    System.out.println("Введите имя эпик задачи: ");
                    scanner.nextLine();
                    String nameEpic = scanner.nextLine();
                    epicTask.setName(nameEpic);
                    System.out.println("Введите описание эпик задачи: ");
                    epicTask.setDescription(scanner.nextLine());

                    taskManager.createEpic(epicTask);
                    System.out.println("Создана эпик задача: " + epicTask.getName()
                            + ", описание: " + epicTask.getDescription() + ", статус: " + epicTask.getStatus());
                    break;
                case 2:
                    try {
                        System.out.println("К какой эпик задаче относиться создаваемая подзадача? Введите ID: ");
                        SubTask subTask = new SubTask(scanner.nextInt());
                        scanner.nextLine();
                        System.out.println("Введите имя подзадачи: ");
                        String nameSub = scanner.nextLine();
                        subTask.setName(nameSub);
                        System.out.println("Введите описание подзадачи: ");
                        subTask.setDescription(scanner.nextLine());
                        System.out.println("Введите дату начала вида 'dd.MM.yyyy HH:mm': ");
                        subTask.setStartTime(LocalDateTime.parse(scanner.nextLine(), format));
                        System.out.println("Введите продолжительность задачи в мин: ");
                        subTask.setDuration(Duration.ofMinutes(Integer.parseInt(scanner.nextLine())));

                        taskManager.createSubTask(subTask);
                        System.out.println("Создана подзадача: имя " + subTask.getName() + ", описание: "
                                + subTask.getDescription() + ", статус: " + subTask.getStatus());
                    } catch (NullPointerException e) {
                        System.out.println("Ошибка! Такую задачу невозможно создать.\r\n" +
                                "Подзадача обязательно должна быть частью эпик задачи!");

                    } catch (SameTimeTaskException e) {
                        System.out.println("Произошла ошибка при добавлении задачи. " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        Task task = new Task();
                        System.out.println("Введите имя задачи: ");
                        scanner.nextLine();
                        String nameTask = scanner.nextLine();
                        task.setName(nameTask);
                        System.out.println("Введите описание задачи: ");
                        task.setDescription(scanner.nextLine());
                        System.out.println("Введите дату начала вида 'dd.MM.yyyy HH:mm': ");
                        task.setStartTime(LocalDateTime.parse(scanner.nextLine(), format));
                        System.out.println("Введите продолжительность задачи в мин: ");
                        task.setDuration(Duration.ofMinutes(Integer.parseInt(scanner.nextLine())));

                        taskManager.createTask(task);
                        System.out.println("Создана задача: имя " + task.getName() + ", описание: "
                                + task.getDescription() + ", статус: " + task.getStatus());
                    } catch (SameTimeTaskException e) {
                        System.out.println("Произошла ошибка при добавлении задачи. " + e.getMessage());
                    }
                    break;
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
            System.out.println("1 - Изменить статус подзадачи\r\n2 - Изменить статус задачи\r\n0 - Вернутся назад");
            int command;
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Какую подзадачу изменяем? Введите ID: ");
                    int idSubTask = scanner.nextInt();
                    SubTask subTask = taskManager.getSubTaskById(idSubTask);
                    System.out.println("Изменить статус подзадачи\r\n1 - Новая задача\r\n2 - В процессе выполнения\r\n"
                            + "3 - Выполнено");
                    int statusSub = scanner.nextInt();
                    switch (statusSub) {
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
                case 2:
                    System.out.println("Какую задачу изменяем? Введите ID: ");
                    int idTask = scanner.nextInt();
                    Task task = taskManager.getTaskById(idTask);
                    System.out.println("Изменить статус подзадачи\r\n1 - Новая задача\r\n2 - В процессе выполнения\r\n"
                            + "3 - Выполнено");
                    int statusTask = scanner.nextInt();
                    switch (statusTask) {
                        case 1:
                            task.setStatus(TaskStatus.NEW);
                            taskManager.updateTask(task);
                            break;
                        case 2:
                            task.setStatus(TaskStatus.IN_PROGRESS);
                            taskManager.updateTask(task);
                            break;
                        case 3:
                            task.setStatus(TaskStatus.DONE);
                            taskManager.updateTask(task);
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
            System.out.println("1 - Удалить эпик задачу\r\n2 - Удалить подзадачу\r\n3 - Удалить задачу" +
                    "\r\n0 - Вернутся назад");
            int command;
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    try {
                        System.out.println("Какую эпик задачу удалить? Введите ID: ");
                        int idEpicTask = scanner.nextInt();
                        taskManager.deleteEpicById(idEpicTask);
                        break;
                    } catch (NullPointerException e) {
                        System.out.println("Ошибка! Нет такого id эпик задачи");
                        continue;
                    }
                case 2:
                    try {
                        System.out.println("Какую подзадачу удалить? Введите ID: ");
                        int idSubTask = scanner.nextInt();
                        taskManager.deleteSubTaskById(idSubTask);
                        break;
                    } catch (NullPointerException e) {
                        System.out.println("Ошибка! Нет такого id подзадачи");
                        continue;
                    }
                case 3:
                    try {
                        System.out.println("Какую задачу удалить? Введите ID: ");
                        int idTask = scanner.nextInt();
                        taskManager.deleteTaskById(idTask);
                        break;
                    } catch (NullPointerException e) {
                        System.out.println("Ошибка! Нет такого id задачи");
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
            System.out.println("Какую задачу нужно получить?\r\n1 - Эпик\r\n2 - Подзадачу\r\n3 - Задачу" +
                    "\r\n0 - Вернутся назад");
            int command = scanner.nextInt();

            switch (command) {
                case 1:
                    try {
                        System.out.println("Введите id: ");
                        int idEpic = scanner.nextInt();
                        System.out.println("Эпик задача получена: ");
                        System.out.println(taskManager.getEpicById(idEpic));
                        break;
                    } catch (NullPointerException e) {
                        System.out.println("Ошибка! Нет такого id эпик задачи");
                    }
                    continue;
                case 2:
                    try {
                        System.out.println("Введите id: ");
                        int idSub = scanner.nextInt();
                        System.out.println("Подзадача получена: ");
                        System.out.println(taskManager.getSubTaskById(idSub));
                        break;
                    } catch (NullPointerException e) {
                        System.out.println("Ошибка! Нет такого id подзадачи");
                    }
                    continue;
                case 3:
                    try {
                        System.out.println("Введите id: ");
                        int idTasks = scanner.nextInt();
                        System.out.println("Задача получена: ");
                        System.out.println(taskManager.getTaskById(idTasks));
                        break;
                    } catch (NullPointerException e) {
                        System.out.println("Ошибка! Нет такого id задачи");
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
        System.out.println("Для какой эпик задачи нужно получить подзадачи? Введите ID: ");
        int idEpicTask = scanner.nextInt();
        output = taskManager.getEpicSubTasks(taskManager.getEpicById(idEpicTask));
        System.out.println("Для эпик задачи: " + taskManager.getEpicById(idEpicTask)
                + " Получены подзадачи :" + output);
    }

    public void getH() {
        System.out.println("История задач: " + taskManager.getHistoryName() + " храниться тут");
    }

    public void getP() {
        System.out.println("Список задач в порядке приоритета: " + taskManager.getPrioritizedTasks() + ".");
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
        System.out.println("9 - Получить список задач в порядке приоритета");
        System.out.println("0 - Вернутся назад");
    }
}