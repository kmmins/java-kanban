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
        StringBuilder sb = new StringBuilder();

        sb.append(getId()).append(",");
        sb.append(TypeTask.SUBTASK).append(",");
        sb.append(getName()).append(",");
        sb.append(getStatus()).append(",");
        sb.append(getDescription()).append(",");
        sb.append((getEpicId()));

        return sb.toString();
    }
}