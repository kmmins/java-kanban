package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.Task;

import java.util.List;

public interface HistoryManager {

    /**
     * <p>Добавляет задачи в список отслеживаемых </p>
     *
     * @param task является сам объект
     */
    void appendHistory(Task task);

    /** <p>Удаляет задачи из просмотра </p>
     *
     * @param id
     */
    void remove(int id);

    /**
     * <p>Получает историю последних 10 обращений к методам getSubTaskById() и getEpicById() </p>
     *
     * @return возвращает список задач Task последних 10 обращений к getSubTaskById() и getEpicById()
     */
    List<Task> getHistoryName();
}