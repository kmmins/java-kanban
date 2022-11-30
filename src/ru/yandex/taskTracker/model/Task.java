package ru.yandex.taskTracker.model;

import ru.yandex.taskTracker.service.CSVTaskFormat;

public class Task {
    private int id;
    private String name;
    private String description;
    protected TaskStatus status = TaskStatus.NEW;

    public Task() {
    }

    public Task(int id, String name, String description, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return CSVTaskFormat.taskToString(this);
    }
}