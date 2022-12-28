package ru.yandex.taskTracker.service;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {

    private final TaskManager taskManager = Managers.getFileBacked();

    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private HttpServer httpServer;
    Gson gson = new Gson();

    public void startHttpServer() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new SomeHandler());
        httpServer.start(); // запускаем сервер

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void stopHttpServer() {
        httpServer.stop(0);
    }

    private int parseIdFromHttp(HttpExchange ex) {
        try {
            URI url = ex.getRequestURI();
            int result = -1;
            String strUrl = url.toString();
            if (strUrl.contains("?id=")) {
                int index = strUrl.indexOf("?id=");
                result = Integer.parseInt(strUrl.substring(index + 4));
            }
            return result;
        } catch (Exception e) {
            System.out.println("Ошибка. " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    private void writeResponse(HttpExchange ex, String response, int rCode) throws IOException {
        if (response.isBlank()) {
            ex.sendResponseHeaders(400, 0);
        } else {
            byte[] bytes = response.getBytes(DEFAULT_CHARSET);
            ex.sendResponseHeaders(rCode, bytes.length);
            try (OutputStream os = ex.getResponseBody()) {
                os.write(bytes);
            } catch (Exception e) {
                System.out.println("Ошибка. " + e.getMessage());
                e.printStackTrace();
            }
        }
        ex.close();
    }

    class SomeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            System.out.println("Началась обработка /tasks запроса от клиента.");
            String requestPath = ex.getRequestURI().getPath();
            String requestMethod = ex.getRequestMethod();

            String[] parts = requestPath.split("/");
//getAllTasks
            if (parts.length == 3 && "tasks".equals(parts[1]) && "task".equals(parts[2]) && parseIdFromHttp(ex) == -1) {
                if ("GET".equals(requestMethod)) {
                    try {
                        String getAllTasksJson = gson.toJson(taskManager.getAllTasks());
                        writeResponse(ex, getAllTasksJson, 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 400);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//getTaskById
            if (parts.length == 3 && "tasks".equals(parts[1]) && "task".equals(parts[2]) && parseIdFromHttp(ex) != -1) {
                if ("GET".equals(requestMethod)) {
                    try {
                        String getTaskByIdJson = gson.toJson(taskManager.getTaskById(parseIdFromHttp(ex)));
                        writeResponse(ex, getTaskByIdJson, 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 404);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//createdTask
            if (parts.length == 3 && "tasks".equals(parts[1]) && "task".equals(parts[2]) && parseIdFromHttp(ex) == -1) {
                if ("POST".equals(requestMethod)) {
                    try {
                        InputStream is = ex.getRequestBody();
                        String body = new String(is.readAllBytes(), DEFAULT_CHARSET);

                        Task taskFromJson = gson.fromJson(body, Task.class);
                        Task createdTask = taskManager.createTask(taskFromJson);

                        String createdTaskJson = gson.toJson(createdTask);
                        writeResponse(ex, createdTaskJson, 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 400);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//deleteTaskById
            if (parts.length == 3 && "tasks".equals(parts[1]) && "task".equals(parts[2]) && parseIdFromHttp(ex) != -1) {
                if ("DELETE".equals(requestMethod)) {
                    try {
                        taskManager.deleteTaskById(parseIdFromHttp(ex));
                        writeResponse(ex, "Задача c id: " + parseIdFromHttp(ex) + " удалена", 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 404);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//deleteAllTasks
            if (parts.length == 3 && parts[1].equals("tasks") && parts[2].equals("task") && parseIdFromHttp(ex) == -1) {
                if ("DELETE".equals(requestMethod)) {
                    try {
                        taskManager.deleteAllTasks();
                        writeResponse(ex, "Все задачи удалены", 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 400);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//getAllSubTasks
            if (parts.length == 3 && "tasks".equals(parts[1]) && "subtask".equals(parts[2]) && parseIdFromHttp(ex) == -1) {
                if ("GET".equals(requestMethod)) {
                    try {
                        String getAllSubTasksJson = gson.toJson(taskManager.getAllSubTasks());
                        writeResponse(ex, getAllSubTasksJson, 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 400);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//getSubTaskById
            if (parts.length == 3 && "tasks".equals(parts[1]) && "subtask".equals(parts[2]) && parseIdFromHttp(ex) != -1) {
                if ("GET".equals(requestMethod)) {
                    try {
                        String getSubTaskByIdJson = gson.toJson(taskManager.getSubTaskById(parseIdFromHttp(ex)));
                        writeResponse(ex, getSubTaskByIdJson, 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 404);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//createdSubTask
            if (parts.length == 3 && "tasks".equals(parts[1]) && "subtask".equals(parts[2]) && parseIdFromHttp(ex) == -1) {
                if ("POST".equals(requestMethod)) {
                    try {
                        InputStream is = ex.getRequestBody();
                        String body = new String(is.readAllBytes(), DEFAULT_CHARSET);

                        SubTask subTaskFromJson = gson.fromJson(body, SubTask.class);
                        SubTask createdSubTask = taskManager.createSubTask(subTaskFromJson);

                        String createdSubTaskJson = gson.toJson(createdSubTask);
                        writeResponse(ex, createdSubTaskJson, 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 400);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//deleteSubTaskById
            if (parts.length == 3 && "tasks".equals(parts[1]) && "subtask".equals(parts[2]) && parseIdFromHttp(ex) != -1) {
                if ("DELETE".equals(requestMethod)) {
                    try {
                        taskManager.deleteSubTaskById(parseIdFromHttp(ex));
                        writeResponse(ex, "Подзадача c id: " + parseIdFromHttp(ex) + " удалена", 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 404);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//deleteAllSubTasks
            if (parts.length == 3 && "tasks".equals(parts[1]) && "subtask".equals(parts[2]) && parseIdFromHttp(ex) == -1) {
                if ("DELETE".equals(requestMethod)) {
                    try {
                        taskManager.deleteAllSubTasks();
                        writeResponse(ex, "Все подзадачи удалены", 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 400);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//getAllEpics
            if (parts.length == 3 && "tasks".equals(parts[1]) && "epic".equals(parts[2]) && parseIdFromHttp(ex) == -1) {
                if ("GET".equals(requestMethod)) {
                    try {
                        String getAllEpicsJson = gson.toJson(taskManager.getAllEpics());
                        writeResponse(ex, getAllEpicsJson, 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 400);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//getEpicById
            if (parts.length == 3 && "tasks".equals(parts[1]) && "epic".equals(parts[2]) && parseIdFromHttp(ex) != -1) {
                if ("GET".equals(requestMethod)) {
                    try {
                        String getEpicByIdJson = gson.toJson(taskManager.getEpicById(parseIdFromHttp(ex)));
                        writeResponse(ex, getEpicByIdJson, 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 404);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//createEpic
            if (parts.length == 3 && "tasks".equals(parts[1]) && "epic".equals(parts[2]) && parseIdFromHttp(ex) == -1) {
                if ("POST".equals(requestMethod)) {
                    try {
                        InputStream is = ex.getRequestBody();
                        String body = new String(is.readAllBytes(), DEFAULT_CHARSET);

                        Task epicFromJson = gson.fromJson(body, Epic.class);
                        Task createdEpic = taskManager.createTask(epicFromJson);

                        String createdEpicJson = gson.toJson(createdEpic);
                        writeResponse(ex, createdEpicJson, 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 400);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//deleteEpicById
            if (parts.length == 3 && "tasks".equals(parts[1]) && "epic".equals(parts[2]) && parseIdFromHttp(ex) != -1) {
                if ("DELETE".equals(requestMethod)) {
                    try {
                        taskManager.deleteEpicById(parseIdFromHttp(ex)); // НЕ РАБОТАЕТ DELETE ;(((((
                        writeResponse(ex, "Эпик c id: " + parseIdFromHttp(ex) + " удален", 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 404);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//deleteAllEpics
            if (parts.length == 3 && "tasks".equals(parts[1]) && "epic".equals(parts[2]) && parseIdFromHttp(ex) == -1) {
                if ("DELETE".equals(requestMethod)) {
                    try {
                        taskManager.deleteAllEpics(); // НЕ РАБОТАЕТ DELETE ;(((((
                        writeResponse(ex, "Все задачи удалены", 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 400);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//getEpicSubTasks
            if (parts.length == 4 && "tasks".equals(parts[1]) && "subtask".equals(parts[2]) && "epic".equals(parts[3])
                    && parseIdFromHttp(ex) != -1) {
                if ("GET".equals(requestMethod)) {
                    try {
                        String getEpicSubTasksJson = gson.toJson(taskManager.getEpicSubTasks(
                                taskManager.getEpicById(parseIdFromHttp(ex))));
                        writeResponse(ex, getEpicSubTasksJson, 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 404);
                        e.printStackTrace();
                    }
                    return;
                }
            }
//getHistoryName
            if (parts.length == 3 && "tasks".equals(parts[1]) && "history".equals(parts[2]) && parseIdFromHttp(ex) == -1) {
                if ("GET".equals(requestMethod)) {
                    try {
                        String getHistoryNameJson = gson.toJson(taskManager.getHistoryName());
                        writeResponse(ex, getHistoryNameJson, 200); // В РЕСПОНС ЭПИК ВСМЕСТЕ С РЕЛЕЙТЕД САБТАСК
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 400);
                        e.printStackTrace();
                    }
                    return;
                }

            }
//getPrioritizedTasks
            if (parts.length == 2 && "tasks".equals(parts[1])) {
                if ("GET".equals(requestMethod)) {
                    try {
                        String getPrioritizedTasksJson = gson.toJson(taskManager.getPrioritizedTasks());
                        writeResponse(ex, getPrioritizedTasksJson, 200);
                    } catch (Exception e) {
                        writeResponse(ex, "Ошибка. " + e.getMessage(), 400);
                        e.printStackTrace();
                    }
                    return;
                }
            }
            writeResponse(ex, "Такого endpoint не существует", 404);
        }
    }
}