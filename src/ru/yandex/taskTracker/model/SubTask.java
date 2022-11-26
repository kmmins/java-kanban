package ru.yandex.taskTracker.model;

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
        StringBuilder stBuilder = new StringBuilder();

        stBuilder.append(getId()).append(",");
        stBuilder.append(TypeTask.SUBTASK).append(",");
        stBuilder.append(getName()).append(",");
        stBuilder.append(getStatus()).append(",");
        stBuilder.append(getDescription()).append(",");
        stBuilder.append((getEpicId()));

        return stBuilder.toString();
    }
}