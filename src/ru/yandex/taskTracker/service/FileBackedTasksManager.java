package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    private String saveFileName;

    public FileBackedTasksManager(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public void save() {

        Path saveFilePath = Paths.get(saveFileName);
        Path saveDirPath = saveFilePath.getParent();

        if (!Files.exists((saveDirPath))) {
            try {
                Files.createDirectory(saveDirPath);
            } catch (IOException e) {
                System.out.println("Произошла ошибка при создании файла/директории: " + e.getMessage());
                e.printStackTrace();
            }
        }
        try (
                FileWriter writer = new FileWriter(saveFilePath.toFile());
                BufferedWriter fileWriter = new BufferedWriter(writer)) {

            fileWriter.write("id,type,name,status,description,epic\r\n");

            for (Epic element : getAllEpics()) {
                fileWriter.write(element.toString());
                fileWriter.write("\r\n");
            }
            for (Task element : getAllTasks()) {
                fileWriter.write(element.toString());
                fileWriter.write("\r\n");
            }
            for (SubTask element : getAllSubTasks()) {
                fileWriter.write(element.toString());
                fileWriter.write("\r\n");
            }
            fileWriter.write("\r\n");
            fileWriter.write(historyToString(historyManager));

        } catch (
                IOException e) {
            System.out.println("Произошла неизвестная ошибка!");
            e.printStackTrace();
        }
        System.out.println("Сохранение...");
    }

    public static FileBackedTasksManager loadFromFile(File file) {

        FileBackedTasksManager result = new FileBackedTasksManager(file.getPath());

        try (
                FileReader reader = new FileReader(file);
                BufferedReader fileReader = new BufferedReader(reader)
        ) {
            boolean headerRead = false;
            boolean allTasksRead = false;
            while (fileReader.ready()) {
                String value = fileReader.readLine();
                if (!headerRead) {
                    headerRead = true;
                    continue;
                }
                if (value.isEmpty()) {
                    if (!allTasksRead) {
                        allTasksRead = true;
                        continue;
                    }
                }
                if (!allTasksRead) {
                    result.createTaskFromString(value);
                } else {
                    result.fillHistoryFromString(value);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода");
            e.printStackTrace();
        }
        return result;
    }

    public static String historyToString(HistoryManager manager) {
        List<Task> name = manager.getHistoryName();
        StringBuilder sb = new StringBuilder();

        for (Task element : name) {
            sb.append(element.getId()).append(",");
        }
        if (!name.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public void createTaskFromString(String value) {
        int epicId = 0;
        String[] param = value.split(",");

        int id = Integer.parseInt(param[0]);
        TypeTask type = TypeTask.valueOf(param[1]);
        String name = param[2];
        TaskStatus status = TaskStatus.valueOf(param[3]);
        String description = param[4];
        if (param.length == 6) {
            epicId = Integer.parseInt(param[5]);
        }

        if (taskCounterId < id) { // сохранить счетчик родительского класса
            taskCounterId = id;
        }

        if (type.equals(TypeTask.TASK)) {
            taskData.put(id, new Task(id, name, description, status));
        } else if (type.equals(TypeTask.SUBTASK)) {
            SubTask newSbT = new SubTask(id, name, description, status, epicId);
            subTaskData.put(id, newSbT);
            Epic eSbT = epicData.get(newSbT.getEpicId());
            eSbT.getRelatedSubTasks().add(newSbT);
            updateEpicStatus(eSbT);
        } else if (type.equals(TypeTask.EPIC)) {
            epicData.put(id, new Epic(id, name, description, status, new ArrayList<>()));
        }
    }

    public void fillHistoryFromString(String value) {

        String[] paramHistory = value.split(",");
        for (String element : paramHistory) {
            int id = Integer.parseInt(element);
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

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = super.getSubTaskById(id);
        save();
        return subTask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createSubTask(SubTask task) {
        super.createSubTask(task);
        save();
    }

    @Override
    public void createEpic(Epic task) {
        super.createEpic(task);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask task) {
        super.updateSubTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic task) {
        super.updateEpic(task);
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteSubTaskById(int id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }
}