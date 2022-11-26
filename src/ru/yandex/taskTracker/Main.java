package ru.yandex.taskTracker;

import ru.yandex.taskTracker.service.FileBackedTasksManager;
import ru.yandex.taskTracker.service.TestService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scannerIntMain = new Scanner(System.in);
        TestService testService = new TestService();
        FileBackedTasksManager fileBacked = new FileBackedTasksManager();

        outOfHere:
        while (true) {
            System.out.println("\r\n//////////////////////////\r\n/////  Task Tracker  /////\r\n" +
                    "//////////////////////////");

            System.out.println("\r\n1 - Запустить тестирование программы\r\n2 - Сохранить данные" +
                    "\r\n3 - Загрузить данные принудительно\r\n0 - Выйти из программы");
            int commandMain = scannerIntMain.nextInt();
            switch (commandMain) {
                case 1:
                    testService.runTest();
                    break;
                case 2:
                    fileBacked.save();
                    break;
                case 3:
                    //fileBacked.loadFromFile(file);
                    break;
                case 0:
                    System.out.println("Выход из программы...");
                    break outOfHere;
                default:
                    System.out.println("Извините, такой команды пока нет");
                    break;
            }
        }
    }
}