package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int taskCounterId = 0;
    private int subTaskCounterId = 0;
    private int epicCounterId = 0;

    private HashMap<Integer, Task> taskData = new HashMap<>();
    private HashMap<Integer, SubTask> subTaskData = new HashMap<>();
    private HashMap<Integer, Epic> epicData = new HashMap<>();

    /**
     * <p>Получает все задачи отдельно для соответсвующего класса задач.</p>
     *
     * @return возвращает список значений хранящихся в соответсвующей HashMap
     */
    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskData.values());
    }

    /**
     * <p>Получает все задачи отдельно для соответсвующего класса задач.</p>
     *
     * @return возвращает список значений хранящихся в соответсвующей HashMap
     */
    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTaskData.values());
    }

    /**
     * <p>Получает все задачи отдельно для соответсвующего класса задач.</p>
     *
     * @return возвращает список значений хранящихся в соответсвующей HashMap
     */
    @Override
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> result = new ArrayList<>();
        for (Integer key : epicData.keySet()) {
            result.add(getEpicById(key));
        }
        return result;
    }

    /**
     * <p>Удаляет все задачи отдельно для соответсвующего класса задач из соответсвующей HashMap.</p>
     */
    @Override
    public void deleteAllTasks() {
        taskData.clear();
    }

    /**
     * <p>Удаляет все задачи отдельно для соответсвующего класса задач из соответсвующей HashMap.</p>
     */
    @Override
    public void deleteAllSubTasks() {
        subTaskData.clear();
    }

    /**
     * <p>Удаляет все задачи отдельно для соответсвующего класса задач из соответсвующей HashMap.</p>
     */
    @Override
    public void deleteAllEpics() {
        subTaskData.clear();
        epicData.clear();
    }

    /**
     * <p>Получает конкретную задачу отдельно для соответсвующего класса.</p>
     *
     * @param id идентефикатор присваиваемый задаче соответсвующего класса в момет ее создания
     * @return объект соответсвующего класса задач
     */
    @Override
    public Task getTaskById(int id) {
        return taskData.get(id);
    }

    /**
     * <p>Получает конкретную задачу отдельно для соответсвующего класса.</p>
     *
     * @param id идентефикатор присваиваемый задаче соответсвующего класса в момет ее создания
     * @return объект соответсвующего класса задач
     */
    @Override
    public SubTask getSubTaskById(int id) {
        return subTaskData.get(id);
    }

    /**
     * <p>Получает конкретную задачу отдельно для соответсвующего класса.</p>
     *
     * @param id идентефикатор присваиваемый задаче соответсвующего класса в момет ее создания
     * @return объект соответсвующего класса задач
     */
    @Override
    public Epic getEpicById(int id) {
        return epicData.get(id);
    }

    /**
     * <p>Добавляет новый обект соответсвующего класса задач в соответсвующую HashMap по ключу id.
     * Присваивает объекту id согласно счетчику, a значения соответсвующих классу полей при помощи get().
     * Поля status для задач SubTask и Epic рассчитываются и обновляются соответсвующе.</p>
     *
     * @param task является сам объект соответсвующего класса задач
     *
     * @see #getEpicSubTasks
     * @see #evaluateEpicStatus
     * @see #updateEpicStatus
     */
    @Override
    public void createTask(Task task) {
        taskCounterId += 1;
        taskData.put(taskCounterId, new Task(taskCounterId, task.getName(), task.getDescription(), task.getStatus()));
    }

    /**
     * <p>Добавляет новый обект соответсвующего класса задач в соответсвующую HashMap по ключу id.
     * Присваивает объекту id согласно счетчику, a значения соответсвующих классу полей при помощи get().
     * Поля status для задач SubTask и Epic рассчитываются и обновляются соответсвующе.</p>
     *
     * @param task является сам объект соответсвующего класса задач
     *
     * @see #getEpicSubTasks
     * @see #evaluateEpicStatus
     * @see #updateEpicStatus
     */
    @Override
    public void createSubTask(SubTask task) {
        subTaskCounterId += 1;
        SubTask newSbT = new SubTask(subTaskCounterId, task.getName(), task.getDescription(),
                task.getStatus(), task.getEpicId());

        subTaskData.put(subTaskCounterId, newSbT);

        Epic eSbT = getEpicById(newSbT.getEpicId());
        eSbT.getRelatedSubTasks().add(newSbT);
        updateEpicStatus(eSbT);
    }

    /**
     * <p>Добавляет новый обект соответсвующего класса задач в соответсвующую HashMap по ключу id.
     * Присваивает объекту id согласно счетчику, a значения соответсвующих классу полей при помощи get().
     * Поля status для задач SubTask и Epic рассчитываются и обновляются соответсвующе.</p>
     *
     * @param task является сам объект соответсвующего класса задач
     *
     * @see #getEpicSubTasks
     * @see #evaluateEpicStatus
     * @see #updateEpicStatus
     */
    @Override
    public void createEpic(Epic task) {
        epicCounterId += 1;
        epicData.put(epicCounterId, new Epic(epicCounterId, task.getName(), task.getDescription(),
                evaluateEpicStatus(task), new ArrayList<>()));
    }

    /**
     *<p>Обновляет статус обекта соответсвующего класса задач.</p>
     *
     * @param task является сам объект соответсвующего класса задач
     *
     * @see #getEpicById
     * @see #updateEpicStatus
     */
    @Override
    public void updateTask(Task task) {
        taskData.put(task.getId(), task);
    }

    /**
     *<p>Обновляет статус обекта соответсвующего класса задач.</p>
     *
     * @param task является сам объект соответсвующего класса задач
     *
     * @see #getEpicById
     * @see #updateEpicStatus
     */
    @Override
    public void updateSubTask(SubTask task) {
        subTaskData.put(task.getId(), task);
        updateEpicStatus(getEpicById(task.getEpicId()));
    }

    /**
     *<p>Обновляет статус обекта соответсвующего класса задач.</p>
     *
     * @param task является сам объект соответсвующего класса задач
     *
     * @see #getEpicById
     * @see #updateEpicStatus
     */
    @Override
    public void updateEpic(Epic task) {
        epicData.put(task.getId(), task);
    }

    /**
     *<p>Удаляет обект соответсвующего класса задач.</p>
     *
     * @param id идентефикатор присваиваемый задаче соответсвующего класса в момет ее создания
     */
    @Override
    public void deleteTaskById(int id) {
        taskData.remove(id);
    }

    /**
     *<p>Удаляет обект соответсвующего класса задач.</p>
     *
     * @param id идентефикатор присваиваемый задаче соответсвующего класса в момет ее создания
     */
    @Override
    public void deleteSubTaskById(int id) {
        SubTask someSubTask = getSubTaskById(id);

        subTaskData.remove(id);

        Epic someEpicTask = getEpicById(someSubTask.getEpicId());
        someEpicTask.getRelatedSubTasks().remove(someSubTask);
        updateEpicStatus(someEpicTask);
    }

    /**
     *<p>Удаляет обект соответсвующего класса задач.</p>
     *
     * @param id идентефикатор присваиваемый задаче соответсвующего класса в момет ее создания
     */
    @Override
    public void deleteEpicById(int id) {
        for (SubTask element : getEpicById(id).getRelatedSubTasks()) {
            subTaskData.remove(element.getId());
        }
        epicData.remove(id);
    }

    /**
     * <p>Получет список задач SubTasks являющихся частью соответсвующей задачи Epic  </p>
     *
     * @param task является сам объект класса задач Epic
     * @return список epicSubTasks содержащий задачи SubTasks
     */
    @Override
    public ArrayList<SubTask> getEpicSubTasks(Epic task) {
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        for (SubTask element : getAllSubTasks()) {
            if (element.getEpicId() == task.getId()) {
                epicSubTasks.add(element);
            }
        }
        return epicSubTasks;
    }

    /**
     * <p>Расчитывает статус Epic задачи на основе статутсов зависимых от этого Epic-a задач SubTasks.</p>
     *
     * @param task является сам объект класса задач Epic
     * @return одно из трех значений типа String: "NEW", "IN_PROGRESS", "DONE"
     *
     * @see #getEpicSubTasks
     */
    private TaskStatus evaluateEpicStatus(Epic task) {
        int newCount = 0;
        int doneCount = 0;
        ArrayList<SubTask> epicSubTasks = getEpicSubTasks(task);
        for (SubTask element : epicSubTasks) {
            if (element.getStatus().equals(TaskStatus.NEW)) {
                newCount += 1;
            }
            if (element.getStatus().equals(TaskStatus.DONE)) {
                doneCount += 1;
            }
        }
        if (epicSubTasks.size() == 0 || newCount == epicSubTasks.size()) {
            return TaskStatus.NEW;
        }
        if (doneCount == epicSubTasks.size()) {
            return TaskStatus.DONE;
        }
        return TaskStatus.IN_PROGRESS;
    }

    /**
     *<p>Обновляет статус Епик задачи используя метод evaluateEpicStatus.</p>
     *
     * @param task является сам объект класса задач Epic
     *
     * @see #evaluateEpicStatus
     */
    private void updateEpicStatus(Epic task) {
        task.setStatus(evaluateEpicStatus(task));
    }
}