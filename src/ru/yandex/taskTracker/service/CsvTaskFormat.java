package ru.yandex.taskTracker.service;

import ru.yandex.taskTracker.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvTaskFormat extends InMemoryTaskManager implements TaskManager {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    /**
     * <P> Метод преобразует в строку данные об истории вызовов, хранящейся в памяти. </>
     *
     * @param manager возвращающий кастомный список истории вызовов задач
     * @return строку с id задач из истории хранящейся в памяти
     */
    public static String historyToString(HistoryManager manager) {
        List<Task> name = manager.getHistory();
        StringBuilder sb = new StringBuilder();

        for (Task element : name) {
            sb.append(element.getId()).append(",");
        }
        if (!name.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * <P> Метод производит сбор параметров задачи из строки. </>
     *
     * @param value строка содержащая задачу считанная из файла "autosave.csv"
     * @return задачу соответствущего типа
     */
    public static Task parseTask(String value) {

        int epicId = 0;
        String[] param = value.split(",");

        int id = Integer.parseInt(param[0]);
        TypeTask type = TypeTask.valueOf(param[1]);
        String name = param[2];
        TaskStatus status = TaskStatus.valueOf(param[3]);
        String description = param[4];
        LocalDateTime startTime = null;
        if (!param[5].isEmpty()) {
            startTime = LocalDateTime.parse(param[5], formatter);
        }
        Duration duration = Duration.ofMinutes(Long.parseLong(param[6]));
        if (param.length == 8) {
            epicId = Integer.parseInt(param[7]);
        }

        if (type.equals(TypeTask.TASK)) {
            return new Task(id, name, description, status, startTime, duration);
        } else if (type.equals(TypeTask.SUBTASK)) {
            return new SubTask(id, name, description, status, epicId, startTime, duration);
        } else if (type.equals(TypeTask.EPIC)) {
            return new Epic(id, name, description, status, new ArrayList<>());
        } else {
            throw new IllegalArgumentException("Неизвестный тип задачи в переданных данных");
        }
    }

    /**
     * <P> Метод собирает id задач хранящихся в истории. </>
     *
     * @param value строка содержащая id задач хранящихся в истории
     * @return список id задач из истории
     */
    public static List<Integer> parseHistory(String value) {
        String[] paramHistory = value.split(",");
        List<Integer> idResult = new ArrayList<>();
        for (String element : paramHistory) {
            idResult.add(Integer.parseInt(element));
        }
        return idResult;
    }

    /**
     * <P> Метод собирает строку из параметров задачи соответствующего типа. </>
     *
     * @param task соответствующего типа
     * @return собранную строку
     */
    public static String taskToString(Task task) {

        return task.getId() + "," +
                TypeTask.TASK + "," +
                task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," +
                task.getStartTime().format(formatter) + "," +
                task.getDuration().toMinutes() + ",";
    }

    /**
     * <P> Метод собирает строку из параметров задачи соответствующего типа. </>
     *
     * @param task соответствующего типа
     * @return собранную строку
     */
    public static String subToString(SubTask task) {

        return task.getId() + "," +
                TypeTask.SUBTASK + "," +
                task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," +
                task.getStartTime().format(formatter) + "," +
                task.getDuration().toMinutes() + "," +
                (task.getEpicId());
    }

    /**
     * <P> Метод собирает строку из параметров задачи соответствующего типа. </>
     *
     * @param task соответствующего типа
     * @return собранную строку
     */
    public static String epicToString(Epic task) {

        return task.getId() + "," +
                TypeTask.EPIC + "," +
                task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," +
                "," +
                task.getDuration().toMinutes() + ",";
    }

    /**
     * <P> Заголовок для CSV-файла. </>
     *
     * @return первую строку для autosave.csv
     */
    public static String getHeader() {
        return "id,type,name,status,description,startTime,duration,epic";
    }
}