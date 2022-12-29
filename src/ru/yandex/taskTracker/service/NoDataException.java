package ru.yandex.taskTracker.service;

public class NoDataException extends RuntimeException{

    public NoDataException(String message) {
        super(message);
    }
}