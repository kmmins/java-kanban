package ru.yandex.taskTracker;

import ru.yandex.taskTracker.service.ManagerSaveException;
import ru.yandex.taskTracker.service.TestService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ManagerSaveException {
        Scanner scannerIntMain = new Scanner(System.in);

        outOfHere:
        while (true) {
            System.out.println("\r\n//////////////////////////\r\n/////  Task Tracker  /////\r\n" +
                    "//////////////////////////");

            System.out.println("\r\n1 - Запустить тестирование программы\r\n\0 - Выйти из программы");
            int commandMain = scannerIntMain.nextInt();
            switch (commandMain) {
                case 1:
                    TestService testService = new TestService();
                    testService.runTest();
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