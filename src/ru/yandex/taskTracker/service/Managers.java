package ru.yandex.taskTracker.service;

public class Managers {

    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
