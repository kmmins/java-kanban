package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;
import ru.yandex.taskTracker.util.CustomLinkedList;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private CustomLinkedList customList = new CustomLinkedList();

    @Override
    public void appendHistory(Task task) {
        if (customList.hashMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        customList.linkLast(task);
        customList.hashMap.put(task.getId(), customList.tail);
    }

    @Override
    public void remove(int id) {
        CustomLinkedList.Node<Task> value = customList.hashMap.remove(id);
        if (value != null) {
            customList.removeNod(value);
        }
    }

    @Override
    public List<Task> getHistory() {
        return customList.getTasks();
    }
}