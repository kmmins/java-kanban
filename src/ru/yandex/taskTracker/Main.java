package ru.yandex.taskTracker;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Программа Трекер задач");


        imOutOfHere:
        while (true) {
            printMenu();
            int commandSwitch = scanner.nextInt();
            switch (commandSwitch) {
                case 1:
                    System.out.println("Месячные отчёты считаны");
                    break;
                case 2:
                    System.out.println("Годовой отчёт считан");
                    break;
                case 3:
                    System.out.println("Сверка отчётов...");
                    break;
                case 4:
                    System.out.println("Информация о всех месячных отчётах: ");
                    break;
                case 5:
                    System.out.println("Информация о годовом отчёте: ");
                    break;
                case 6:
                    System.out.println("Выход...");
                    break imOutOfHere;
                default:
                    System.out.println("Извините, такой команды пока нет");
            }
        }
    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("6 - Выход из приложения");
    }
}
