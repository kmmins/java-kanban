package ru.yandex.taskTracker;

import ru.yandex.taskTracker.service.TestService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scannerIntMain = new Scanner(System.in);
        TestService testService = new TestService();

        imOutOfHere:
        while (true) {
            System.out.println("\r\n///////////////////\r\n//  Таск Трекер  //\r\n///////////////////");

            System.out.println("\r\nЗапустить тестирование программы?\r\n1 - Да, 0 - Выйти из программы");
            int commandMain = scannerIntMain.nextInt();
            switch (commandMain) {
                case 1:
                    testService.runTest();
                    break;
                case 0:
                    System.out.println("Выход из программы...");
                    break imOutOfHere;
                default:
                    System.out.println("Извините, такой команды пока нет");
                    break;
            }
        }

        /*testService.createTask();
        testService.getAll();
        testService.createTask();
        testService.getAll();
        testService.updateTask();
        testService.getAll();
        testService.delTask();
        testService.getAll();*/
    }
}