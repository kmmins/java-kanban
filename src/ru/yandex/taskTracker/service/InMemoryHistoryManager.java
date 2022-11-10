package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.model.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> historyName = new ArrayList<>();

    private CustomLinkedList historyNameNew = new CustomLinkedList();

    private HashMap<Integer, Node<Task>> name = new HashMap<>();

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Task> getHistoryName() {
        return historyNameNew.getTasks();
    }

    @Override
    public void appendHistory(Task task) {
        historyName.add(task);
        if (historyName.size() > 10) {
            historyName.remove(0);
        }
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
    }
}