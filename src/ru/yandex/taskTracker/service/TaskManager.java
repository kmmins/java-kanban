package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    /**
     * <p>Получает все задачи отдельно для соответствующего класса задач. </p>
     *
     * @return возвращает список значений хранящихся в соответствующей HashMap
     */
    ArrayList<Task> getAllTasks();

    /**
     * <p>Получает все задачи отдельно для соответствующего класса задач. </p>
     *
     * @return возвращает список значений хранящихся в соответствующей HashMap
     */
    ArrayList<SubTask> getAllSubTasks();

    /**
     * <p>Получает все задачи отдельно для соответствующего класса задач. </p>
     *
     * @return возвращает список значений хранящихся в соответствующей HashMap
     */
    ArrayList<Epic> getAllEpics();

    /**
     * <p>Удаляет все задачи отдельно для соответствующего класса задач из соответствующей HashMap. </p>
     */
    void deleteAllTasks();

    /**
     * <p>Удаляет все задачи отдельно для соответствующего класса задач из соответствующей HashMap. </p>
     */
    void deleteAllSubTasks();

    /**
     * <p>Удаляет все задачи отдельно для соответствующего класса задач из соответствующей HashMap. </p>
     */
    void deleteAllEpics();

    /**
     * <p>Получает конкретную задачу отдельно для соответствующего класса. </p>
     *
     * @param id идентификатор присваиваемый задаче соответствующего класса в момент ее создания
     * @return объект соответствующего класса задач
     */
    Task getTaskById(int id);

    /**
     * <p>Получает конкретную задачу отдельно для соответствующего класса </p>
     *
     * @param id идентификатор присваиваемый задаче соответствующего класса в момент ее создания
     * @return объект соответствующего класса задач
     */
    SubTask getSubTaskById(int id);

    /**
     * <p>Получает конкретную задачу отдельно для соответствующего класса. </p>
     *
     * @param id идентификатор присваиваемый задаче соответствующего класса в момент ее создания
     * @return объект соответствующего класса задач
     */
    Epic getEpicById(int id);

    /**
     * <p>Добавляет новый объект соответствующего класса задач в соответсвующую HashMap по ключу id.
     * Присваивает объекту id согласно счетчику, a значения соответствующей классу полей при помощи get().
     * Поля status для задач SubTask и Epic рассчитываются и обновляются соответствующе </p>
     *
     * @param task является сам объект соответствующего класса задач
     * @return созданный объект соответствующего класса задач со сквозной нумерацией Id
     * @see #getEpicSubTasks
     */
    Task createTask(Task task);

    /**
     * <p>Добавляет новый объект соответствующего класса задач в соответсвующую HashMap по ключу id.
     * Присваивает объекту id согласно счетчику, a значения соответствующей классу полей при помощи get().
     * Поля status для задач SubTask и Epic рассчитываются и обновляются соответствующе </p>
     *
     * @param task является сам объект соответствующего класса задач
     * @return созданный объект соответствующего класса задач со сквозной нумерацией Id
     * @see #getEpicSubTasks
     */
    SubTask createSubTask(SubTask task);

    /**
     * <p>Добавляет новый объект соответствующего класса задач в соответсвующую HashMap по ключу id.
     * Присваивает объекту id согласно счетчику, a значения соответствующей классу полей при помощи get().
     * Поля status для задач SubTask и Epic рассчитываются и обновляются соответствующе </p>
     *
     * @param task является сам объект соответствующего класса задач
     * @return созданный объект соответствующего класса задач со сквозной нумерацией Id
     * @see #getEpicSubTasks
     */
    Epic createEpic(Epic task);

    /**
     * <p>Обновляет статус объекта соответствующего класса задач. </p>
     *
     * @param task является сам объект соответствующего класса задач
     * @see #getEpicById
     */
    void updateTask(Task task);

    /**
     * <p>Обновляет статус объекта соответствующего класса задач </p>
     *
     * @param task является сам объект соответствующего класса задач
     * @see #getEpicById
     */
    void updateSubTask(SubTask task);

    /**
     * <p>Обновляет статус объекта соответствующего класса задач. </p>
     *
     * @param task является сам объект соответствующего класса задач
     * @see #getEpicById
     */
    void updateEpic(Epic task);

    /**
     * <p>Удаляет объект соответствующего класса задач. </p>
     *
     * @param id идентификатор присваиваемый задаче соответствующего класса в момент ее создания
     */
    void deleteTaskById(int id);

    /**
     * <p>Удаляет объект соответствующего класса задач. </p>
     *
     * @param id идентификатор присваиваемый задаче соответствующего класса в момент ее создания
     */
    void deleteSubTaskById(int id);

    /**
     * <p>Удаляет объект соответствующего класса задач. </p>
     *
     * @param id идентификатор присваиваемый задаче соответствующего класса в момент ее создания
     */
    void deleteEpicById(int id);

    /**
     * <p>Получет список задач SubTasks являющихся частью соответствующей задачи Epic. </p>
     *
     * @param task является сам объект класса задач Epic
     * @return список epicSubTasks содержащий задачи SubTasks
     */
    ArrayList<SubTask> getEpicSubTasks(Epic task);

    /**
     * <p>Вызывает метод getHistory() экземпляра класса типа HistoryManager. </p>
     *
     * @return возвращает список задач Task содержащий историю вызовов
     */

    List<Task> getHistoryName();

    /** <p>Метод для получения задач отсортированных по времени старта. </p>
     *
     * @return список задач в порядке приоритета
     */
    ArrayList<Task> getPrioritizedTasks();
}