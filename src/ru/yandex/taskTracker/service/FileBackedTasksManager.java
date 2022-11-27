package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

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

    public void save() {

        Path saveDirPath = Path.of(".resources");
        Path saveFilePath = Paths.get(".resources\\autosave.csv");

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

            for (Task element : getAllTasks()) {
                fileWriter.write(element.toString());
                fileWriter.write("\r\n");
            }
            for (SubTask element : getAllSubTasks()) {
                fileWriter.write(element.toString());
                fileWriter.write("\r\n");
            }
            for (Epic element : getAllEpics()) {
                fileWriter.write(element.toString());
                fileWriter.write("\r\n");
            }

            fileWriter.write("\r\n");
            fileWriter.write(historyToString(historyManager));

        } catch (
                IOException e) {
            System.out.println("Произошла неизвестная ошибка!");
        }
        System.out.println("Сохранение...");
    }

    public static FileBackedTasksManager loadFromFile(File file) {

        return null;
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

    public Task taskFromString(String value) {
        Task task = null;

        String[] lines = value.split("\r?\n");

        int epicId;
        for (int i = 1; i < lines.length - 2; i++) { // отрезали пустую строку и строку истории
            String line = lines[i];
            String[] param = line.split(",");

            int id = Integer.parseInt(param[0]);
            TypeTask type = TypeTask.valueOf(param[1]);
            String name = param[2];
            TaskStatus status = TaskStatus.valueOf(param[3]);
            String description = param[4];
            if (param.length == 6) {
                epicId = Integer.parseInt(param[5]);
            } else epicId = -1;

            task = new Task(id, name, description, status);
        }

        return task;
    }

    public static List<Integer> historyFromString(String value) {

        List<Integer> historyFromString = new ArrayList<>();

        String[] lines = value.split("\r?\n");
        String lineHistory = lines[lines.length - 1];

        String[] paramHistory = lineHistory.split(",");
        for (String s : paramHistory) {
            int ph = Integer.parseInt(s);
            historyFromString.add(ph);
        }

        return historyFromString;
    }
}


