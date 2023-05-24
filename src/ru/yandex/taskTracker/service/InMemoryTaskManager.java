package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected HistoryManager historyManager = Managers.getDefaultHistory();
    protected int taskCounterId = 0;
    private Set<Task> sortedTasks;
    protected HashMap<Integer, Task> taskData = new HashMap<>();
    protected HashMap<Integer, SubTask> subTaskData = new HashMap<>();
    protected HashMap<Integer, Epic> epicData = new HashMap<>();

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskData.values());
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTaskData.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epicData.values());
    }

    @Override
    public void deleteAllTasks() {
        taskData.clear();
        sortMethod();
    }

    @Override
    public void deleteAllSubTasks() {
        subTaskData.clear();
        sortMethod();
    }

    @Override
    public void deleteAllEpics() {
        subTaskData.clear();
        epicData.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = taskData.get(id);
        historyManager.appendHistory(task);
        return task;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = subTaskData.get(id);
        historyManager.appendHistory(subTask);
        return subTask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epicData.get(id);
        historyManager.appendHistory(epic);
        return epic;
    }

    @Override
    public Task createTask(Task task) throws SameTimeTaskException {
        taskCounterId += 1;
        Task createdTask = new Task(taskCounterId, task.getName(), task.getDescription(), task.getStatus(),
                task.getStartTime(), task.getDuration());
        taskData.put(taskCounterId, createdTask);
        sortMethod();
        validate();
        return createdTask;
    }

    @Override
    public SubTask createSubTask(SubTask task) throws SameTimeTaskException {
        taskCounterId += 1;
        SubTask createdSubTask = new SubTask(taskCounterId, task.getName(), task.getDescription(),
                task.getStatus(), task.getEpicId(), task.getStartTime(), task.getDuration());

        subTaskData.put(taskCounterId, createdSubTask);

        Epic eSbT = epicData.get(createdSubTask.getEpicId());
        eSbT.getRelatedSubTasks().add(createdSubTask);
        updateEpicData(eSbT);
        sortMethod();
        validate();

        return createdSubTask;
    }

    @Override
    public Epic createEpic(Epic task) {
        taskCounterId += 1;
        Epic createdEpic = new Epic(taskCounterId, task.getName(), task.getDescription(),
                evaluateEpicStatus(task), new ArrayList<>());
        epicData.put(taskCounterId, createdEpic);
        return createdEpic;
    }

    @Override
    public void updateTask(Task task) throws SameTimeTaskException {
        taskData.put(task.getId(), task);
        sortMethod();
        validate();
    }

    @Override
    public void updateSubTask(SubTask task) throws SameTimeTaskException {
        subTaskData.put(task.getId(), task);
        updateEpicData(epicData.get(task.getEpicId()));
        sortMethod();
        validate();
    }

    @Override
    public void updateEpic(Epic task) {
        epicData.put(task.getId(), task);
    }

    @Override
    public void deleteTaskById(int id) {
        taskData.remove(id);
        historyManager.remove(id);
        sortMethod();
    }

    @Override
    public void deleteSubTaskById(int id) {
        SubTask someSubTask = getSubTaskById(id);

        subTaskData.remove(id);

        Epic someEpicTask = epicData.get(someSubTask.getEpicId());
        someEpicTask.getRelatedSubTasks().remove(someSubTask);
        updateEpicData(someEpicTask);
        historyManager.remove(id);
        sortMethod();
    }

    @Override
    public void deleteEpicById(int id) {
        for (SubTask element : epicData.get(id).getRelatedSubTasks()) {
            subTaskData.remove(element.getId());
            historyManager.remove(element.getId());
        }
        epicData.remove(id);
        historyManager.remove(id);
    }

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

    @Override
    public List<Task> getHistoryName() {
        return historyManager.getHistory();
    }

    protected TaskStatus evaluateEpicStatus(Epic task) {
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

    protected LocalDateTime findEpicStartTime(Epic task) {
        ArrayList<SubTask> subTasksEpic = getEpicSubTasks(task);
        LocalDateTime min = null;

        for (SubTask element : subTasksEpic) {
            if (min == null || element.getStartTime().isBefore(min)) {
                min = element.getStartTime();
            }
        }
        return min;
    }

    protected LocalDateTime findEpicEndTime(Epic task) {
        ArrayList<SubTask> subTasksEpic = getEpicSubTasks(task);
        LocalDateTime max = null;

        for (SubTask element : subTasksEpic) {
            if (max == null || element.getEndTime().isAfter(max)) {
                max = element.getEndTime();
            }
        }
        return max;
    }

    protected Duration findEpicDuration(Epic task) {
        ArrayList<SubTask> subTasksEpic = getEpicSubTasks(task);
        Duration sum = Duration.ofMinutes(0);

        for (SubTask element : subTasksEpic) {
            sum = sum.plus(element.getDuration());
        }
        return sum;
    }

    protected void updateEpicData(Epic task) {
        task.setStatus(evaluateEpicStatus(task));
        task.setStartTime(findEpicStartTime(task));
        task.setDuration(findEpicDuration(task));
        task.setEndTime(findEpicEndTime(task));
    }

    protected void sortMethod() {
        ArrayList<Task> atask = getAllTasks();
        ArrayList<SubTask> asubtask = getAllSubTasks();

        Comparator<Task> sortByStartTime = (task1, task2) -> {
            if (task1.getStartTime() != null && task2.getStartTime() != null) {
                return task1.getStartTime().compareTo(task2.getStartTime());

            } else if (task1.getStartTime() != null && task2.getStartTime() == null) {
                return -1;

            } else if (task1.getStartTime() == null && task2.getStartTime() != null) {
                return 1;
            }
            return 0;
        };
        sortedTasks = new TreeSet<>(sortByStartTime);
        sortedTasks.addAll(atask);
        sortedTasks.addAll(asubtask);
    }

    @Override
    public ArrayList<Task> getPrioritizedTasks() {
        return new ArrayList<>(sortedTasks);
    }

    private void validate() throws SameTimeTaskException {
        ArrayList<Task> priorTasks = getPrioritizedTasks();
        int listSize = priorTasks.size();

        for (int i = 0; i < listSize - 1; i++) {

            if (priorTasks.get(i).getEndTime() != null && priorTasks.get(i + 1).getStartTime() != null &&
                    priorTasks.get(i).getEndTime().isAfter(priorTasks.get(i + 1).getStartTime())) {
                throw new SameTimeTaskException("Задачи пересекаются по времени выполнения.");
            }
        }
    }
}