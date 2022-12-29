package ru.yandex.taskTracker.tests;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.service.HttpTaskServer;
import ru.yandex.taskTracker.util.GsonClass;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTests {

    HttpTaskServer testServer;

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @BeforeEach
    void startEnvironment() throws IOException {
        testServer = new HttpTaskServer();
        testServer.startHttpServer();
    }

    @AfterEach
    void stopEnvironment() {
        testServer.stop();
    }

    @Test
    void checkEmptyGetAllTasks() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).DELETE().build();

        URI url2 = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).GET().build();

        try {
            client.send(request1, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<Task>>() {
            }.getType();
            List<Task> tasks = GsonClass.gson.fromJson(response2.body(), listType);

            assertTrue(tasks.isEmpty(), "Список задач не пустой.");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkGetTaskById() {
        HttpClient client = HttpClient.newHttpClient();
        Task task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        Task task2 = new Task();
        task2.setName("Задача 2");
        task2.setDescription("Описание задачи 2");
        task2.setStartTime(LocalDateTime.parse("02.01.2022 10:00", format));
        task2.setDuration(Duration.ofMinutes(60));
        URI url = URI.create("http://localhost:8080/tasks/task/");
        String rqTask1 = GsonClass.gson.toJson(task1);
        final HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(rqTask1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(body1).build();
        URI url2 = URI.create("http://localhost:8080/tasks/task/");
        String rqTask2 = GsonClass.gson.toJson(task2);
        final HttpRequest.BodyPublisher body2 = HttpRequest.BodyPublishers.ofString(rqTask2);
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).POST(body2).build();
        URI url3 = URI.create("http://localhost:8080/tasks/task/?id=2");
        HttpRequest request = HttpRequest.newBuilder().uri(url3).GET().build();

        try {
            client.send(request1, HttpResponse.BodyHandlers.ofString());
            client.send(request2, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Task rs = GsonClass.gson.fromJson(response.body(), Task.class);

            assertEquals(2, rs.getId(), "Получен неправильный id задачи");
            assertEquals(task2.getName(), rs.getName(), "Задачи c таким именем не не существует");
            assertEquals(task2.getDescription(), rs.getDescription(), "Задачи с таким описанием нет");
            assertEquals(task2.getStatus(), rs.getStatus(), "Не совпадают статусы задач");
            assertEquals(task2.getStartTime(), rs.getStartTime(), "Не совпадет время начала задач");
            assertEquals(task2.getDuration(), rs.getDuration(), "Не совпадет длительность задач");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkCreateTask() {
        HttpClient client = HttpClient.newHttpClient();
        Task task1 = new Task();
        task1.setName("Задача 1");
        task1.setDescription("Описание задачи 1");
        task1.setStartTime(LocalDateTime.parse("01.01.2022 10:00", format));
        task1.setDuration(Duration.ofMinutes(60));
        URI url = URI.create("http://localhost:8080/tasks/task/");
        String rqTask1 = GsonClass.gson.toJson(task1);
        Task task2 = new Task();
        task2.setName("Задача 2");
        task2.setDescription("Описание задачи 2");
        task2.setStartTime(LocalDateTime.parse("02.01.2022 10:00", format));
        task2.setDuration(Duration.ofMinutes(60));
        URI url2 = URI.create("http://localhost:8080/tasks/task/");
        String rqTask2 = GsonClass.gson.toJson(task2);

        final HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(rqTask1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(body1).build();
        final HttpRequest.BodyPublisher body2 = HttpRequest.BodyPublishers.ofString(rqTask2);
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).POST(body2).build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Task rsTask1 = GsonClass.gson.fromJson(response1.body(), Task.class);
            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            Task rsTask2 = GsonClass.gson.fromJson(response2.body(), Task.class);

            assertEquals(task1.getName(), rsTask1.getName(), "Задачи c таким именем не не существует");
            assertEquals(task1.getDescription(), rsTask1.getDescription(), "Задачи с таким описанием нет");
            assertEquals(task1.getStatus(), rsTask1.getStatus(), "Не совпадают статусы задач");
            assertEquals(task1.getStartTime(), rsTask1.getStartTime(), "Не совпадет время начала задач");
            assertEquals(task1.getDuration(), rsTask1.getDuration(), "Не совпадет длительность задач");
            assertEquals(task2.getName(), rsTask2.getName(), "Задачи c таким именем не не существует");
            assertEquals(task2.getDescription(), rsTask2.getDescription(), "Задачи с таким описанием нет");
            assertEquals(task2.getStatus(), rsTask2.getStatus(), "Не совпадают статусы задач");
            assertEquals(task2.getStartTime(), rsTask2.getStartTime(), "Не совпадет время начала задач");
            assertEquals(task2.getDuration(), rsTask2.getDuration(), "Не совпадет длительность задач");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkDeleteTasksById() {
        HttpClient client = HttpClient.newHttpClient();
        URI url3 = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url3).DELETE().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, response.statusCode(), "Метод вернул код ошибки. " + response.statusCode());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkDeleteAllTasks() {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, response.statusCode(), "Метод вернул код ошибки. " + response.statusCode());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }
}