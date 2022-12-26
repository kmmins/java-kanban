package ru.yandex.taskTracker.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {

    KVTaskClient kvt;

    private static final Gson gson = new Gson();

    public HttpTaskManager(String url) throws ManagerSaveException {

        super(null);
        try {
            kvt = new KVTaskClient(url);
            try {
                loadHelper();
            } catch (NullPointerException e) {
                System.out.println("При первом запуске данные о состоянии отсутствуют");
                save();
                loadHelper();
            }
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Произошла ошибка при загрузке состояния с сервера: " + e.getMessage());
        }

    }

    @Override
    public void save() throws ManagerSaveException {
        try {
            String epickey = "epickey";
            String gaE = gson.toJson(getAllEpics());
            kvt.put(epickey, gaE);

            String taskkey = "taskkey";
            String gaT = gson.toJson(getAllTasks());
            kvt.put(taskkey, gaT);

            String subkey = "subkey";
            String gaS = gson.toJson(getAllSubTasks());
            kvt.put(subkey, gaS);

            String hystorykey = "hystorykey";
            List<Task> historyName = getHistoryName();
            List<Integer> historyNameId = new ArrayList<>();
            for (Task element : historyName) {
                historyNameId.add(element.getId());
            }
            String gHn = gson.toJson(historyNameId);
            kvt.put(hystorykey, gHn);

        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Произошла ошибка при загрузке на сервер: " + e.getMessage());
        }
    }

    private void loadHelper() throws IOException, InterruptedException {
        String epicValue = kvt.load("epickey");
        Type listType = new TypeToken<List<Epic>>() {
        }.getType();
        List<Epic> epicFromJson = gson.fromJson(epicValue, listType);
        for (Epic elm : epicFromJson) {
            epicData.put(elm.getId(), elm);
        }

        String taskValue = kvt.load("taskkey");
        listType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> tasksFromJson = gson.fromJson(taskValue, listType);
        for (Task elm : tasksFromJson) {
            taskData.put(elm.getId(), elm);
        }

        String subValue = kvt.load("subkey");
        listType = new TypeToken<List<SubTask>>() {
        }.getType();
        List<SubTask> subFromJson = gson.fromJson(subValue, listType);
        for (SubTask elm : subFromJson) {
            subTaskData.put(elm.getId(), elm);
            Epic eSbT = epicData.get(elm.getEpicId());
            eSbT.getRelatedSubTasks().add(elm);
            updateEpicData(eSbT);
        }
        sortMethod();

        String historyId = kvt.load("hystorykey");
        listType = new TypeToken<List<Integer>>() {
        }.getType();

        List<Integer> fillId = gson.fromJson(historyId, listType);
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
    }
}