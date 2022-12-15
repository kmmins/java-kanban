package ru.yandex.taskTracker.service;

public class SameTimeTaskException extends RuntimeException{
    public SameTimeTaskException(String message) {
        super(message);
    }
}