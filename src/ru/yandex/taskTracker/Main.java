package ru.yandex.taskTracker;

import ru.yandex.taskTracker.service.ManagerSaveException;
import ru.yandex.taskTracker.service.ConsoleInterface;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ManagerSaveException {
        Scanner scannerIntMain = new Scanner(System.in);

        outOfHere:
        while (true) {
            System.out.println("\r\n//////////////////////////\r\n/////  Task Tracker  /////\r\n" +
                    "//////////////////////////");

            System.out.println("1 - Запустить консольное меню\r\n\0 - Выйти из программы");
            int commandMain = scannerIntMain.nextInt();
            switch (commandMain) {
                case 1:
                    ConsoleInterface run = new ConsoleInterface();
                    run.startMenu();
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