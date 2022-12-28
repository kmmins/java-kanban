package ru.yandex.taskTracker.service;

public class FirstTimeStartException extends RuntimeException{

    public FirstTimeStartException(String message) {
        super(message);
    }
}