package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.Task;

import java.util.List;

public interface HistoryManager {

    /**
     * <p>Добавляет последние полученные задачи по id в порядке вызовов в историю просмотров </p>
     *
     * @param task является сам объект
     */
    void appendHistory(Task task);

    /** <p>Удаляет вызовы задач из истории просмотров с одинаковыми id </p>
     *
     * @param id идентификатор присваиваемый задаче
     */
    void remove(int id);

    /**
     * <p>Получает историю последних полученных задачи по идентификатору </p>
     *
     * @return возвращает список задач Task по id из истории
     */
    List<Task> getHistoryName();
}