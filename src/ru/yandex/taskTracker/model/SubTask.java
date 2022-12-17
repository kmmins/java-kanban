package ru.yandex.taskTracker.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {

    private final int epicId;

    public SubTask(int epicId) {
        this.epicId = epicId;
    }

    public SubTask(int id, String name, String description, TaskStatus status, int epicId, LocalDateTime startTime, Duration duration) {
        super(id, name, description, status, startTime, duration);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}