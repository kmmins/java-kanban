package ru.yandex.taskTracker.model;

public class SubTask extends Task {

    private int epicId;

    public SubTask(int epicId) {
        super(TypeTask.SUBTASK);
        this.epicId = epicId;
    }

    public SubTask(int id, String name, String description, TaskStatus status, int epicId) {
        super(id, name, description, status, TypeTask.SUBTASK);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}