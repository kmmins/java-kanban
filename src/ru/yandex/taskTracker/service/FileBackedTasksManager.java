package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    public void save() {

        Path saveDirPath = Path.of(".resources");
        Path saveFilePath = Paths.get(".resources\\autosave.csv");

        if(!Files.exists((saveDirPath))) {
            try {
                Files.createDirectory(saveDirPath);

            } catch (IOException e) {
                System.out.println("Произошла ошибка при создании файла/директории: " + e.getMessage());
                e.printStackTrace();
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try (
                FileWriter writer = new FileWriter(saveFilePath.toFile());
                BufferedWriter fileWriter = new BufferedWriter(writer)) {

            fileWriter.write("id,type,name,status,description,epic3");

        } catch (
                IOException e) {
            System.out.println("Произошла неизвестная ошибка!");
        }
    }

    /*public static FileBackedTasksManager load(File saveFilePath) throws IOException {
        Files.readString(saveFilePath);
        return null;
    }*/

    public String saveTaskToString(Task task) {
        String saveString = null;

        return saveString;
    }

    public Task createTaskFromString() {
        Task saveTask = null;

        return saveTask;
    }

    public static String historyToString(HistoryManager manager) {
        String manager1 = "0";
        return manager1;
    }

    public static List<Integer> historyFromString(String value) {
        String value1 = "1";
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public ArrayList<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return super.getAllSubTasks();
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return super.getAllEpics();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
    }

    @Override
    public Task getTaskById(int id) {
        return super.getTaskById(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        return super.getSubTaskById(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return super.getEpicById(id);
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
    }

    @Override
    public void createSubTask(SubTask task) {
        super.createSubTask(task);
    }

    @Override
    public void createEpic(Epic task) {
        super.createEpic(task);
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
    }

    @Override
    public void updateSubTask(SubTask task) {
        super.updateSubTask(task);
    }

    @Override
    public void updateEpic(Epic task) {
        super.updateEpic(task);
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        super.deleteSubTaskById(id);
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
    }

    @Override
    public ArrayList<SubTask> getEpicSubTasks(Epic task) {
        return super.getEpicSubTasks(task);
    }

    @Override
    public List<Task> getHistoryName() {
        return super.getHistoryName();
    }
}


