package ru.yandex.taskTracker.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<SubTask> relatedSubTasks;
    private LocalDateTime endTime;
    public Epic() {
    }

    public Epic(int id, String name, String description, TaskStatus status, ArrayList<SubTask> relatedSubTasks) {
        super(id, name, description, status, null, Duration.ofMinutes(0));
        this.relatedSubTasks = relatedSubTasks;
    }

    public ArrayList<SubTask> getRelatedSubTasks() {
        return relatedSubTasks;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }
}