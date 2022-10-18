package ru.yandex.taskTracker.model;

public class Epic extends Task {

    ///////////////////////constructors////////////////////////////////////////////////////////////////////////////////
    public Epic() {
    }

    public Epic(int id, String name, String description, String status) {
        super(id, name, description, status);
    }
}