package ru.yandex.taskTracker.util;

import ru.yandex.taskTracker.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomLinkedList {

    public Node<Task> head;
    public Node<Task> tail;

    /**
     * p>Вспомогательная HashMap помогает отслеживать повторяющиеся id вызванных задач хранящихся в CustomLinkedList </p>
     */
    public HashMap<Integer, Node<Task>> hashMap = new HashMap<>();

    /**
     * <p>Метод связывает задачу в CustomLinkedList </p>
     *
     * @param task задача полученная по id
     */
    public void linkLast(Task task) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(tail, task, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
    }

    /**
     * <p>Получение списка задача из отслеживаемой истории хранящейся в CustomLinkedList </p>
     *
     * @return список связанных задач истории просмотров
     */
    public ArrayList<Task> getTasks() {
        ArrayList<Task> result = new ArrayList<>();
        if (head == null) {
            return result;
        } else {
            Node<Task> nodeIter = head;
            while (true) {
                result.add(nodeIter.data);
                nodeIter = nodeIter.next;
                if (nodeIter == null)
                    break;
            }
            return result;
        }
    }

    /**
     * <p>Метод вырезает узел из CustomLinkedList </p>
     *
     * @param nodeX узел который необходимо вырезать из CustomLinkedList
     */
    public void removeNod(Node<Task> nodeX) {

        final Node<Task> next = nodeX.next;
        final Node<Task> prev = nodeX.prev;
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

    /**
     * <p>Узел, в связанном списке CustomLinkedList </p>
     *
     * @param <T> принимает объект типа Task
     */
    public class Node<T> {

        public T data;
        public Node<T> next;
        public Node<T> prev;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}