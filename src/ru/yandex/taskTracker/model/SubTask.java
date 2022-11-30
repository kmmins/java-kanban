package ru.yandex.taskTracker.model;

import ru.yandex.taskTracker.service.CSVTaskFormat;

public class SubTask extends Task {

    private int epicId;

    public SubTask(int epicId) {
        this.epicId = epicId;
    }

    public SubTask(int id, String name, String description, TaskStatus status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return CSVTaskFormat.subToString(this);
    }
}