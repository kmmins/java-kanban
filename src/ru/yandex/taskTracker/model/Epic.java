package ru.yandex.taskTracker.model;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<SubTask> relatedSubTasks;

    public Epic() {
        super(TypeTask.EPIC);
    }

    public Epic(int id, String name, String description, TaskStatus status, ArrayList<SubTask> relatedSubTasks) {
        super(id, name, description, status, TypeTask.EPIC);
        this.relatedSubTasks = relatedSubTasks;
    }
    public ArrayList<SubTask> getRelatedSubTasks() {
        return relatedSubTasks;
    }
}