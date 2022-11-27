package ru.yandex.taskTracker.service;

import java.nio.file.Paths;

public class Managers  {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getFileBacked(){
        return FileBackedTasksManager.loadFromFile(Paths.get(".resources\\autosave.csv").toFile());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}