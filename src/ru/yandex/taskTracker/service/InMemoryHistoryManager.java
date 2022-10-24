package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static final List<Task> historyName = new ArrayList<>();

    @Override
    public List<Task> getHistoryName() {
        return historyName;
    }

    @Override
    public void appendHistory(Task task) {
        historyName.add(task);
        if (historyName.size() > 10) {
            historyName.remove(0);
        }
    }
}