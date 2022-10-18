package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int taskCounterId = 0;
    private int subTaskCounterId = 0;
    private int epicCounterId = 0;

    private HashMap<Integer, Task> taskData = new HashMap<>();
    private HashMap<Integer, SubTask> subTaskData = new HashMap<>();
    private HashMap<Integer, Epic> epicData = new HashMap<>();

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskData.values());
    }

    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTaskData.values());
    }

    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> result = new ArrayList<>();
        for (Integer key : epicData.keySet()) {
            result.add(getEpicById(key));
        }
        return result;
    }

    public void deleteAllTasks() {
        taskData.clear();
    }

    public void deleteAllSubTasks() {
        subTaskData.clear();
    }

    public void deleteAllEpics() {
        subTaskData.clear();
        epicData.clear();
    }

    public Task getTaskById(int id) {
        return taskData.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTaskData.get(id);
    }

    public Epic getEpicById(int id) {
        Epic task = epicData.get(id);
        return new Epic(task.getId(), task.getName(), task.getDescription(), evaluateEpicStatus(task), getEpicSubTasks(task));
    }

    public void createTask(Task task) {
        taskCounterId += 1;
        taskData.put(taskCounterId, new Task(taskCounterId, task.getName(), task.getDescription(), task.getStatus()));
    }

    public void createSubTask(SubTask task) {
        subTaskCounterId += 1;
        subTaskData.put(subTaskCounterId, new SubTask(subTaskCounterId, task.getName(), task.getDescription(),
                task.getStatus(), task.getEpicId()));
    }

    public void createEpic(Epic task) {
        epicCounterId += 1;
        epicData.put(epicCounterId, new Epic(epicCounterId, task.getName(), task.getDescription(),
                evaluateEpicStatus(task), new ArrayList<>()));
    }

    public void updateTask(Task task) {
        taskData.put(task.getId(), task);
    }

    public void updateSubTask(SubTask task) {
        subTaskData.put(task.getId(), task);
    }

    public void updateEpic(Epic task) {
        epicData.put(task.getId(), task);
    }

    public void deleteTaskById(int id) {
        taskData.remove(id);
    }

    public void deleteSubTaskById(int id) {
        subTaskData.remove(id);
    }

    public void deleteEpicById(int id) {
        for (SubTask element : getEpicById(id).getRelatedSubTasks()) {
            subTaskData.remove(element.getId());
        }
        epicData.remove(id);
    }

    public ArrayList<SubTask> getEpicSubTasks(Epic task) {
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        for (SubTask element : getAllSubTasks()) {
            if (element.getEpicId() == task.getId()) {
                epicSubTasks.add(element);
            }
        }
        return epicSubTasks;
    }

    private String evaluateEpicStatus(Epic task) {
        int newCount = 0;
        int doneCount = 0;
        ArrayList<SubTask> epicSubTasks = getEpicSubTasks(task);
        for (SubTask element : epicSubTasks) {
            if (element.getStatus().equals("NEW")) {
                newCount += 1;
            }
            if (element.getStatus().equals("DONE")) {
                doneCount += 1;
            }
        }
        if (epicSubTasks.size() == 0 || newCount == epicSubTasks.size()) {
            return "NEW";
        }
        if (doneCount == epicSubTasks.size()) {
            return "DONE";
        }
        return "IN_PROGRESS";
    }
}