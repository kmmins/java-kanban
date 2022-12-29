package ru.yandex.taskTracker.service;

import com.google.gson.reflect.TypeToken;
import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.util.GsonClass;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {

    private final KVTaskClient kvt;

    public HttpTaskManager(String url) throws ManagerSaveException {

        super(null);
        try {
            kvt = new KVTaskClient(url);
            try {
                loadHelper();
            } catch (NoDataException e) {
                System.out.println("KVServer не содержит данных о состоянии трекера задач. " + e.getMessage());
                save();
                loadHelper();
            }
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Произошла ошибка при загрузке состояния с сервера. " + e.getMessage());
        }
    }

    @Override
    public void save() throws ManagerSaveException {
        try {
            String epickey = "epickey";
            String gaE = GsonClass.gson.toJson(getAllEpics());
            kvt.put(epickey, gaE);

            String taskkey = "taskkey";
            String gaT = GsonClass.gson.toJson(getAllTasks());
            kvt.put(taskkey, gaT);

            String subkey = "subkey";
            String gaS = GsonClass.gson.toJson(getAllSubTasks());
            kvt.put(subkey, gaS);

            String hystorykey = "hystorykey";
            List<Task> historyName = getHistoryName();
            List<Integer> historyNameId = new ArrayList<>();
            for (Task element : historyName) {
                historyNameId.add(element.getId());
            }
            String gHn = GsonClass.gson.toJson(historyNameId);
            kvt.put(hystorykey, gHn);

        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Произошла ошибка при отправке данных на сервер: " + e.getMessage());
        }
    }

    private void loadHelper() throws IOException {
        Type listType;
        try {
            String epicValue = kvt.load("epickey");
            listType = new TypeToken<List<Epic>>() {
            }.getType();
            List<Epic> epicFromJson = GsonClass.gson.fromJson(epicValue, listType);
            for (Epic elm : epicFromJson) {
                epicData.put(elm.getId(), elm);
            }

        } catch (InterruptedException e) {
            throw new ManagerSaveException("Произошла ошибка при получении от сервера данных по ключу 'epickey': "
                    + e.getMessage());
        }

        try {
            String taskValue = kvt.load("taskkey");
            listType = new TypeToken<List<Task>>() {
            }.getType();
            List<Task> tasksFromJson = GsonClass.gson.fromJson(taskValue, listType);
            for (Task elm : tasksFromJson) {
                taskData.put(elm.getId(), elm);
            }

        } catch (InterruptedException e) {
            throw new ManagerSaveException("Произошла ошибка при получении от сервера данных по ключу 'taskkey': "
                    + e.getMessage());
        }
        try {
            String subValue = kvt.load("subkey");
            listType = new TypeToken<List<SubTask>>() {
            }.getType();
            List<SubTask> subFromJson = GsonClass.gson.fromJson(subValue, listType);
            for (SubTask elm : subFromJson) {
                subTaskData.put(elm.getId(), elm);
                Epic eSbT = epicData.get(elm.getEpicId());
                eSbT.getRelatedSubTasks().add(elm);
                updateEpicData(eSbT);
            }

        } catch (InterruptedException e) {
            throw new ManagerSaveException("Произошла ошибка при получении от сервера данных по ключу 'subkey': "
                    + e.getMessage());
        }
        sortMethod();

        try {
            String historyId = kvt.load("hystorykey");

            listType = new TypeToken<List<Integer>>() {
            }.getType();

            List<Integer> fillId = GsonClass.gson.fromJson(historyId, listType);
            for (int id : fillId) {
                if (taskData.containsKey(id)) {
                    Task task = taskData.get(id);
                    historyManager.appendHistory(task);
                } else if (subTaskData.containsKey(id)) {
                    SubTask task = subTaskData.get(id);
                    historyManager.appendHistory(task);
                } else if (epicData.containsKey(id)) {
                    Epic task = epicData.get(id);
                    historyManager.appendHistory(task);
                }
            }
        } catch (InterruptedException e) {
            throw new ManagerSaveException("Произошла ошибка при получении от сервера данных 'hystorykey': "
                    + e.getMessage());
        }
    }
}