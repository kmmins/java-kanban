package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int taskCounterId = 0;
    private int subTaskCounterId = 0;
    private int epicCounterId = 0;

    private final List<Task> historyName = new ArrayList<>();

    private HashMap<Integer, Task> taskData = new HashMap<>();
    private HashMap<Integer, SubTask> subTaskData = new HashMap<>();
    private HashMap<Integer, Epic> epicData = new HashMap<>();

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskData.values());
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTaskData.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epicData.values());
    }

    @Override
    public void deleteAllTasks() {
        taskData.clear();
    }

    @Override
    public void deleteAllSubTasks() {
        subTaskData.clear();
    }

    @Override
    public void deleteAllEpics() {
        subTaskData.clear();
        epicData.clear();
    }

    @Override
    public Task getTaskById(int id) {
        return taskData.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = subTaskData.get(id);
        appendHistory(subTask);
        return subTask;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Epic getEpicById(int id) {
        Epic epic = epicData.get(id);
        appendHistory(epic);
        return epic;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void createTask(Task task) {
        taskCounterId += 1;
        taskData.put(taskCounterId, new Task(taskCounterId, task.getName(), task.getDescription(), task.getStatus()));
    }

    @Override
    public void createSubTask(SubTask task) {
        subTaskCounterId += 1;
        SubTask newSbT = new SubTask(subTaskCounterId, task.getName(), task.getDescription(),
                task.getStatus(), task.getEpicId());

        subTaskData.put(subTaskCounterId, newSbT);

        Epic eSbT = epicData.get(newSbT.getEpicId());
        eSbT.getRelatedSubTasks().add(newSbT);
        updateEpicStatus(eSbT);
    }

    @Override
    public void createEpic(Epic task) {
        epicCounterId += 1;
        epicData.put(epicCounterId, new Epic(epicCounterId, task.getName(), task.getDescription(),
                evaluateEpicStatus(task), new ArrayList<>()));
    }

    @Override
    public void updateTask(Task task) {
        taskData.put(task.getId(), task);
    }


    @Override
    public void updateSubTask(SubTask task) {
        subTaskData.put(task.getId(), task);
        updateEpicStatus(epicData.get(task.getEpicId()));
    }

    @Override
    public void updateEpic(Epic task) {
        epicData.put(task.getId(), task);
    }

    @Override
    public void deleteTaskById(int id) {
        taskData.remove(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        SubTask someSubTask = getSubTaskById(id);

        subTaskData.remove(id);

        Epic someEpicTask = epicData.get(someSubTask.getEpicId());
        someEpicTask.getRelatedSubTasks().remove(someSubTask);
        updateEpicStatus(someEpicTask);
    }

    @Override
    public void deleteEpicById(int id) {
        for (SubTask element : epicData.get(id).getRelatedSubTasks()) {
            subTaskData.remove(element.getId());
        }
        epicData.remove(id);
    }

    @Override
    public ArrayList<SubTask> getEpicSubTasks(Epic task) {
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        for (SubTask element : getAllSubTasks()) {
            if (element.getEpicId() == task.getId()) {
                epicSubTasks.add(element);
            }
        }
        return epicSubTasks;
    }

    @Override
    public List<Task> getHistory() {
        return historyName;
    }

    private TaskStatus evaluateEpicStatus(Epic task) {
        int newCount = 0;
        int doneCount = 0;
        ArrayList<SubTask> epicSubTasks = getEpicSubTasks(task);
        for (SubTask element : epicSubTasks) {
            if (element.getStatus().equals(TaskStatus.NEW)) {
                newCount += 1;
            }
            if (element.getStatus().equals(TaskStatus.DONE)) {
                doneCount += 1;
            }
        }
        if (epicSubTasks.size() == 0 || newCount == epicSubTasks.size()) {
            return TaskStatus.NEW;
        }
        if (doneCount == epicSubTasks.size()) {
            return TaskStatus.DONE;
        }
        return TaskStatus.IN_PROGRESS;
    }

    private void updateEpicStatus(Epic task) {
        task.setStatus(evaluateEpicStatus(task));
    }

    private void appendHistory(Task task) {
        historyName.add(task);
        if (historyName.size() > 10) {
            historyName.remove(0);
        }
    }
}