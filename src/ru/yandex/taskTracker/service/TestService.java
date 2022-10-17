package ru.yandex.taskTracker.service;
import com.sun.source.tree.NewArrayTree;
import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import java.util.Scanner;

public class TestService {

    TaskManager taskManager = new TaskManager();


    Scanner scanner1 = new Scanner(System.in);
    Scanner scanner2 = new Scanner(System.in);

    public void getAll() {
        System.out.println("Tasks");
        System.out.println(taskManager.getAllTasks());
        System.out.println("SubTasks");
        System.out.println(taskManager.getAllSubTasks());
        System.out.println("Epics");
        System.out.println(taskManager.getAllEpics());
    }


    public void createTask() {

        imOutOfHere:
        while (true) {
            System.out.println("1 - создать subTask\r\n2 - создать epicTask\r\n0 - Вернутся назад");
            int command;
            command = scanner1.nextInt();
            switch (command) {
                case 1:
                    System.out.println("К какому epicTask относиться создаваемая subTask? ");
                    SubTask subTask = new SubTask(scanner1.nextInt());


                    System.out.println("Введите имя subTask: ");
                    subTask.setName(scanner2.nextLine());
                    System.out.println("Введите описание subTask: ");
                    subTask.setDescription(scanner2.nextLine());

                    taskManager.createSubTask(subTask);
                    System.out.println("Создана subTask: имя " + subTask.getName() + ", описание: " + subTask.getDescription()
                            + ", статус: " + subTask.getStatus());
                    break;
                case 2:
                    Epic epicTask = new Epic();
                    System.out.println("Введите имя epicTask: ");
                    String name = scanner2.nextLine();
                    epicTask.setName(name);
                    System.out.println("Введите описание epicTask: ");
                    epicTask.setDescription(scanner2.nextLine());

                    taskManager.createEpic(epicTask);
                    System.out.println("Создана epicTask: " + epicTask.getName() + ", описание: " + epicTask.getDescription()
                            + ", статус: " + epicTask.getStatus());
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

    public void updateTask() {

        imOutOfHere:
        while (true) {
            System.out.println("1 - изменить subTask\r\n0 - Вернутся назад");
            int command;
            command = scanner1.nextInt();
            switch (command) {
                case 1:
                    System.out.println("К какую subTask изменяем? ");
                    int iD = scanner1.nextInt();
                    SubTask subTask = taskManager.getSubTaskById(iD);
                    System.out.println("Введите новый статус: (NEW, IN_PROGRESS, DONE)");
                    subTask.setStatus(scanner2.nextLine());
                    taskManager.updateSubTask(subTask);
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


    public void delTask() {

        imOutOfHere:
        while (true) {
            System.out.println("1 - удалить subTask\r\n2 - удалить epicTask\r\n0 - Вернутся назад");
            int command;
            command = scanner1.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Какуой subTask удалить? ");
                    int iDsubTask = scanner1.nextInt();
                    taskManager.deleteSubTaskById(iDsubTask);

                    break;
                case 2:
                    System.out.println("Какуой epicTask удалить? ");
                    int iDepicTask = scanner1.nextInt();
                    taskManager.deleteEpicById(iDepicTask);
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
}

/*Создайте в классе Main метод static void main(String[] args) и внутри него:
Создайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей.
Распечатайте списки эпиков, задач и подзадач, через System.out.println(..)
Измените статусы созданных объектов, распечатайте. Проверьте, что статус задачи и подзадачи сохранился,
а статус эпика рассчитался по статусам подзадач.
И, наконец, попробуйте удалить одну из задач и один из эпиков.*/