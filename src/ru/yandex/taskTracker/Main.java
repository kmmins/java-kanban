package ru.yandex.taskTracker;

import ru.yandex.taskTracker.service.*;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ManagerSaveException {
        Scanner scannerIntMain = new Scanner(System.in);

        outOfHere:
        while (true) {
            System.out.println("\r\n//////////////////////////\r\n/////  Task Tracker  /////\r\n" +
                    "//////////////////////////");


            System.out.println("1 - Запустить консольное меню\r\n2 - Запуск KVServer\r\n3 - Запуск HttpTaskServer" +
                    "\r\n0 - Выйти из программы");
            int commandMain = scannerIntMain.nextInt();
            switch (commandMain) {
                case 1:
                    ConsoleInterface run = new ConsoleInterface();
                    run.startMenu();
                    break;
                case 2:
                    try {
                        System.out.println("Запуск...");
                        new KVServer().start();
                    } catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Запуск...");
                    try {
                        new HttpTaskServer().startHttpServer();
                    }
                    catch (IOException e){
                        System.out.println(e.getMessage());
                    }
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