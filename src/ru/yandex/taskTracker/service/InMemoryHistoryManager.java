package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;
import ru.yandex.taskTracker.util.CustomLinkedList;

import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private CustomLinkedList customLinkedListName = new CustomLinkedList();

    private HashMap<Integer, CustomLinkedList.Node<Task>> hashMapName = new HashMap<>();

    @Override
    public void appendHistory(Task task) {
        if (hashMapName.containsKey(task.getId())) {
            remove(task.getId());
        }
        customLinkedListName.linkLast(task);
        hashMapName.put(task.getId(), customLinkedListName.tail);
    }

    @Override
    public void remove(int id) {
        CustomLinkedList.Node<Task> value = hashMapName.remove(id);
        if (value != null) {
            customLinkedListName.removeNod(value);
        }
    }

    @Override
    public List<Task> getHistoryName() {
        return customLinkedListName.getTasks();
    }
}