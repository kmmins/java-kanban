package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    /**
     * <p>Получает все задачи отдельно для соответсвующего класса задач.</p>
     *
     * @return возвращает список значений хранящихся в соответсвующей HashMap
     */
    ArrayList<Task> getAllTasks();

    /**
     * <p>Получает все задачи отдельно для соответсвующего класса задач.</p>
     *
     * @return возвращает список значений хранящихся в соответсвующей HashMap
     */
    ArrayList<SubTask> getAllSubTasks();

    /**
     * <p>Получает все задачи отдельно для соответсвующего класса задач.</p>
     *
     * @return возвращает список значений хранящихся в соответсвующей HashMap
     */
    ArrayList<Epic> getAllEpics();

    /**
     * <p>Удаляет все задачи отдельно для соответсвующего класса задач из соответсвующей HashMap.</p>
     */
    void deleteAllTasks();

    /**
     * <p>Удаляет все задачи отдельно для соответсвующего класса задач из соответсвующей HashMap.</p>
     */
    void deleteAllSubTasks();

    /**
     * <p>Удаляет все задачи отдельно для соответсвующего класса задач из соответсвующей HashMap.</p>
     */
    void deleteAllEpics();

    /**
     * <p>Получает конкретную задачу отдельно для соответсвующего класса.</p>
     *
     * @param id идентефикатор присваиваемый задаче соответсвующего класса в момет ее создания
     * @return объект соответсвующего класса задач
     */
    Task getTaskById(int id);

    /**
     * <p>Получает конкретную задачу отдельно для соответсвующего класса.</p>
     *
     * @param id идентефикатор присваиваемый задаче соответсвующего класса в момет ее создания
     * @return объект соответсвующего класса задач
     */
    SubTask getSubTaskById(int id);

    /**
     * <p>Получает конкретную задачу отдельно для соответсвующего класса.</p>
     *
     * @param id идентефикатор присваиваемый задаче соответсвующего класса в момет ее создания
     * @return объект соответсвующего класса задач
     */
    Epic getEpicById(int id);

    /**
     * <p>Добавляет новый обект соответсвующего класса задач в соответсвующую HashMap по ключу id.
     * Присваивает объекту id согласно счетчику, a значения соответсвующих классу полей при помощи get().
     * Поля status для задач SubTask и Epic рассчитываются и обновляются соответсвующе.</p>
     *
     * @param task является сам объект соответсвующего класса задач
     * @see #getEpicSubTasks
     */
    void createTask(Task task);

    /**
     * <p>Добавляет новый обект соответсвующего класса задач в соответсвующую HashMap по ключу id.
     * Присваивает объекту id согласно счетчику, a значения соответсвующих классу полей при помощи get().
     * Поля status для задач SubTask и Epic рассчитываются и обновляются соответсвующе.</p>
     *
     * @param task является сам объект соответсвующего класса задач
     * @see #getEpicSubTasks
     */
    void createSubTask(SubTask task);

    /**
     * <p>Добавляет новый обект соответсвующего класса задач в соответсвующую HashMap по ключу id.
     * Присваивает объекту id согласно счетчику, a значения соответсвующих классу полей при помощи get().
     * Поля status для задач SubTask и Epic рассчитываются и обновляются соответсвующе.</p>
     *
     * @param task является сам объект соответсвующего класса задач
     * @see #getEpicSubTasks
     */
    void createEpic(Epic task);

    /**
     * <p>Обновляет статус обекта соответсвующего класса задач.</p>
     *
     * @param task является сам объект соответсвующего класса задач
     * @see #getEpicById
     */
    void updateTask(Task task);

    /**
     * <p>Обновляет статус обекта соответсвующего класса задач.</p>
     *
     * @param task является сам объект соответсвующего класса задач
     * @see #getEpicById
     */
    void updateSubTask(SubTask task);

    /**
     * <p>Обновляет статус обекта соответсвующего класса задач.</p>
     *
     * @param task является сам объект соответсвующего класса задач
     * @see #getEpicById
     */
    void updateEpic(Epic task);

    /**
     * <p>Удаляет обект соответсвующего класса задач.</p>
     *
     * @param id идентефикатор присваиваемый задаче соответсвующего класса в момет ее создания
     */
    void deleteTaskById(int id);

    /**
     * <p>Удаляет обект соответсвующего класса задач.</p>
     *
     * @param id идентефикатор присваиваемый задаче соответсвующего класса в момет ее создания
     */
    void deleteSubTaskById(int id);

    /**
     * <p>Удаляет обект соответсвующего класса задач.</p>
     *
     * @param id идентефикатор присваиваемый задаче соответсвующего класса в момет ее создания
     */
    void deleteEpicById(int id);

    /**
     * <p>Получет список задач SubTasks являющихся частью соответсвующей задачи Epic </p>
     *
     * @param task является сам объект класса задач Epic
     * @return список epicSubTasks содержащий задачи SubTasks
     */
    ArrayList<SubTask> getEpicSubTasks(Epic task);

    List<Task> getHistoryName();
}