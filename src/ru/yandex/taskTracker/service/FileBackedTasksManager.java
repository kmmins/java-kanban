package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Task aaa = super.getTaskById(id);
        save();
        return aaa;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask bbb = super.getSubTaskById(id);
        save();
        return bbb;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic ccc = super.getEpicById(id);
        save();
        return ccc;
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static FileBackedTasksManager loadFromFile(File file) {

        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Task taskFromString() {
        Task saveTask = null;

        return saveTask;
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

    public static List<Integer> historyFromString(String value) {
        String value1 = "1";
        return null;
    }
}


