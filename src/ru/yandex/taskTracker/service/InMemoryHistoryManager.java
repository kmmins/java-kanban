package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private CustomLinkedList customLinkedListName = new CustomLinkedList();

    private HashMap<Integer, Node<Task>> hashMapName = new HashMap<>();

    @Override
    public void remove(int id) {
        customLinkedListName.removeNod(hashMapName.get(id));
        hashMapName.remove(id);
    }

    @Override
    public void appendHistory(Task task) {
        if (hashMapName.containsKey(task.getId())) {
            remove(task.getId());
        }
            customLinkedListName.linkLast(task);
            hashMapName.put(task.getId(), customLinkedListName.tail);
    }

    @Override
    public List<Task> getHistoryName() {
        return customLinkedListName.getTasks();
    }

    private class CustomLinkedList {
        public Node<Task> head;
        public Node<Task> tail;

        public void linkLast(Task task) {
            final Node<Task> oldTail = tail;
            final Node<Task> newNode = new Node<>(tail, task, null);
            tail = newNode;
            if (oldTail == null)
                head = newNode;
            else
                oldTail.next = newNode;
        }

        public ArrayList<Task> getTasks() {
            ArrayList<Task> result = new ArrayList<>();
            if (head == null) {
                return result;
            } else {
                Node<Task> nodeI = head;
                result.add(nodeI.data);
                while (nodeI.next != null) {
                    nodeI = nodeI.next;
                    result.add(nodeI.data);
                }
                return result;
            }
        }

        public void removeNod(Node<Task> x) {

            final Node<Task> next = x.next;
            final Node<Task> prev = x.prev;

            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
            }

            if (next == null) {
                tail = prev;
            } else {
                next.prev = prev;
            }
        }
    }
}