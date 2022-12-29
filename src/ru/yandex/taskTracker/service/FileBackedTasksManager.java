package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final String saveFileName;

    public FileBackedTasksManager(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    /**
     * <P>Метод сохраняет текущее состояние трекера задач в файл "autosave.csv". </>
     */
    protected void save() throws ManagerSaveException {

        Path saveFilePath = Paths.get(saveFileName);
        Path saveDirPath = saveFilePath.getParent();

        if (!Files.exists((saveDirPath))) {
            try {
                Files.createDirectory(saveDirPath);
            } catch (IOException e) {
                throw new ManagerSaveException("Произошла ошибка при создании директории: " + e.getMessage());
            }
        }
        try (
                FileWriter writer = new FileWriter(saveFilePath.toFile());
                BufferedWriter fileWriter = new BufferedWriter(writer)) {

            fileWriter.write(CsvTaskFormat.getHeader());
            fileWriter.write("\r\n");

            for (Epic element : getAllEpics()) {
                fileWriter.write(CsvTaskFormat.epicToString(element));
                fileWriter.write("\r\n");
            }
            for (Task element : getAllTasks()) {
                fileWriter.write(CsvTaskFormat.taskToString(element));
                fileWriter.write("\r\n");
            }
            for (SubTask element : getAllSubTasks()) {
                fileWriter.write(CsvTaskFormat.subToString(element));
                fileWriter.write("\r\n");
            }
            fileWriter.write("\r\n");
            fileWriter.write(CsvTaskFormat.historyToString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка при записи в файл: " + e.getMessage());
        }
    }

    /**
     * <P>Метод считывает текущее состояние трекера из файла автосохранения. </>
     *
     * @param file содержащий текущее состояние программы - "autosave.csv"
     * @return экземпляр класса FileBackedTasksManager
     */
    public static FileBackedTasksManager loadFromFile(File file) throws ManagerSaveException {

        FileBackedTasksManager result = new FileBackedTasksManager(file.getPath());

        if (!Files.exists(file.toPath())) {
            result.save();
        }
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
            throw new ManagerSaveException("Отсутствуют данные о состоянии трекера задач.\r\n" +
                    "Не найден файл автосохранения: " + e.getMessage());
        }
        return result;
    }

    /**
     * <P>Метод создает задачу соответствующего типа из данных, хранящихся в файле автосохранения. </>
     *
     * @param value строка считанная из файла "autosave.csv", содержащая данные добавленных в трекер задач
     */
    public void createTaskFromString(String value) {
        Task parseResult = CsvTaskFormat.parseTask(value);

        if (taskCounterId < parseResult.getId()) { // позволяет сохранить сквозную нумерацию счетчика родительского класса
            taskCounterId = parseResult.getId();
        }

        if (parseResult instanceof Epic) {
            epicData.put(parseResult.getId(), (Epic) parseResult);
        } else if (parseResult instanceof SubTask) {
            SubTask newSbT = (SubTask) parseResult;
            subTaskData.put(parseResult.getId(), newSbT);
            Epic eSbT = epicData.get(newSbT.getEpicId());
            eSbT.getRelatedSubTasks().add(newSbT);
            updateEpicData(eSbT);
            sortMethod();
        } else {
            taskData.put(parseResult.getId(), parseResult);
            sortMethod();
        }
    }

    /**
     * <P>Метод определяет по id тип задачи и передает ее в память программы для хранения истории. </>
     *
     * @param value строка содержащая id задач, отвечающая за хранения истории, считанная из файла "autosave.csv"
     */
    public void fillHistoryFromString(String value) {
        List<Integer> fillId = CsvTaskFormat.parseHistory(value);
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
    public Task createTask(Task task) throws SameTimeTaskException {
        Task superCreatedTask = super.createTask(task);
        save();
        return superCreatedTask;
    }

    @Override
    public SubTask createSubTask(SubTask task) throws SameTimeTaskException {
        SubTask superCreatedSubTask = super.createSubTask(task);
        save();
        return superCreatedSubTask;
    }

    @Override
    public Epic createEpic(Epic task) {
        Epic superEpic = super.createEpic(task);
        save();
        return superEpic;
    }

    @Override
    public void updateTask(Task task) throws SameTimeTaskException {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask task) throws SameTimeTaskException {
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