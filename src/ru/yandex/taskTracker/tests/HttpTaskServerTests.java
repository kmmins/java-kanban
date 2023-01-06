package ru.yandex.taskTracker.tests;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.taskTracker.model.Epic;
import ru.yandex.taskTracker.model.SubTask;
import ru.yandex.taskTracker.model.Task;
import ru.yandex.taskTracker.model.TaskStatus;
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
        HttpClient client = HttpClient.newHttpClient();
//задачи
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
        Task task3 = new Task();
        task3.setName("Задача 3");
        task3.setDescription("Описание задачи 3");
        task3.setStartTime(LocalDateTime.parse("03.01.2022 10:00", format));
        task3.setDuration(Duration.ofMinutes(60));
        String rqTask1 = GsonClass.gson.toJson(task1);
        String rqTask2 = GsonClass.gson.toJson(task2);
        String rqTask3 = GsonClass.gson.toJson(task3);
        URI url1 = URI.create("http://localhost:8080/tasks/task/");
//эпик
        Epic epic1 = new Epic();
        epic1.setName("Эпик 1");
        epic1.setDescription("Описание эпика 1");
        Epic epic2 = new Epic();
        epic2.setName("Эпик 2");
        epic2.setDescription("Описание эпика 2");
        Epic epic3 = new Epic();
        epic3.setName("Эпик 3");
        epic3.setDescription("Описание эпика 3");
        String rqEpic1 = GsonClass.gson.toJson(epic1);
        String rqEpic2 = GsonClass.gson.toJson(epic2);
        String rqEpic3 = GsonClass.gson.toJson(epic3);
        URI url2 = URI.create("http://localhost:8080/tasks/epic/");
//подзадачи
        SubTask subTask1 = new SubTask(4);
        subTask1.setName("Подзадача 1");
        subTask1.setDescription("Описание подзадачи 1");
        subTask1.setStartTime(LocalDateTime.parse("01.02.2022 10:00", format));
        subTask1.setDuration(Duration.ofMinutes(60));
        SubTask subTask2 = new SubTask(4);
        subTask2.setName("Подзадача 2");
        subTask2.setDescription("Описание подзадачи 2");
        subTask2.setStartTime(LocalDateTime.parse("02.02.2022 10:00", format));
        subTask2.setDuration(Duration.ofMinutes(60));
        SubTask subTask3 = new SubTask(4);
        subTask3.setName("Подзадача 3");
        subTask3.setDescription("Описание подзадачи 3");
        subTask3.setStartTime(LocalDateTime.parse("03.02.2022 10:00", format));
        subTask3.setDuration(Duration.ofMinutes(60));
        String rqSub1 = GsonClass.gson.toJson(subTask1);
        String rqSub2 = GsonClass.gson.toJson(subTask2);
        String rqSub3 = GsonClass.gson.toJson(subTask3);
        URI url3 = URI.create("http://localhost:8080/tasks/subtask/");
//реквесты задач
        final HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(rqTask1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).POST(body1).build();
        final HttpRequest.BodyPublisher body2 = HttpRequest.BodyPublishers.ofString(rqTask2);
        HttpRequest request2 = HttpRequest.newBuilder().uri(url1).POST(body2).build();
        final HttpRequest.BodyPublisher body3 = HttpRequest.BodyPublishers.ofString(rqTask3);
        HttpRequest request3 = HttpRequest.newBuilder().uri(url1).POST(body3).build();
//реквесты эпиков
        final HttpRequest.BodyPublisher body4 = HttpRequest.BodyPublishers.ofString(rqEpic1);
        HttpRequest request4 = HttpRequest.newBuilder().uri(url2).POST(body4).build();
        final HttpRequest.BodyPublisher body5 = HttpRequest.BodyPublishers.ofString(rqEpic2);
        HttpRequest request5 = HttpRequest.newBuilder().uri(url2).POST(body5).build();
        final HttpRequest.BodyPublisher body6 = HttpRequest.BodyPublishers.ofString(rqEpic3);
        HttpRequest request6 = HttpRequest.newBuilder().uri(url2).POST(body6).build();
//реквесты саб
        final HttpRequest.BodyPublisher body7 = HttpRequest.BodyPublishers.ofString(rqSub1);
        HttpRequest request7 = HttpRequest.newBuilder().uri(url3).POST(body7).build();
        final HttpRequest.BodyPublisher body8 = HttpRequest.BodyPublishers.ofString(rqSub2);
        HttpRequest request8 = HttpRequest.newBuilder().uri(url3).POST(body8).build();
        final HttpRequest.BodyPublisher body9 = HttpRequest.BodyPublishers.ofString(rqSub3);
        HttpRequest request9 = HttpRequest.newBuilder().uri(url3).POST(body9).build();
        try {
//респонсы задач
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            GsonClass.gson.fromJson(response1.body(), Task.class);
            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            GsonClass.gson.fromJson(response2.body(), Task.class);
            HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
            GsonClass.gson.fromJson(response3.body(), Task.class);
//респонсы эпиков
            HttpResponse<String> response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());
            GsonClass.gson.fromJson(response4.body(), Epic.class);
            HttpResponse<String> response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());
            GsonClass.gson.fromJson(response5.body(), Epic.class);
            HttpResponse<String> response6 = client.send(request6, HttpResponse.BodyHandlers.ofString());
            GsonClass.gson.fromJson(response6.body(), Epic.class);
//респонсы саб
            HttpResponse<String> response7 = client.send(request7, HttpResponse.BodyHandlers.ofString());
            GsonClass.gson.fromJson(response7.body(), SubTask.class);
            HttpResponse<String> response8 = client.send(request8, HttpResponse.BodyHandlers.ofString());
            GsonClass.gson.fromJson(response8.body(), SubTask.class);
            HttpResponse<String> response9 = client.send(request9, HttpResponse.BodyHandlers.ofString());
            GsonClass.gson.fromJson(response9.body(), SubTask.class);
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @AfterEach
    void stopEnvironment() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/task/");
        URI url2 = URI.create("http://localhost:8080/tasks/epic/");
        URI url3 = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).DELETE().build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).DELETE().build();
        HttpRequest request3 = HttpRequest.newBuilder().uri(url3).DELETE().build();
        try {
            client.send(request1, HttpResponse.BodyHandlers.ofString());
            client.send(request2, HttpResponse.BodyHandlers.ofString());
            client.send(request3, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
        testServer.stop();
    }

    @Test
    void checkGetAllTasks() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<Task>>() {
            }.getType();
            List<Task> tasks = GsonClass.gson.fromJson(response1.body(), listType);

            assertFalse(tasks.isEmpty(), "Список задач пустой.");
            assertEquals(3, tasks.size(), "Получено не верное значение количества задач");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkGetAllEpics() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<Epic>>() {
            }.getType();
            List<Epic> epics = GsonClass.gson.fromJson(response1.body(), listType);

            assertFalse(epics.isEmpty(), "Список эпиков пустой.");
            assertEquals(3, epics.size(), "Получено не верное значение количества эпиков");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkGetAllSubTasks() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<SubTask>>() {
            }.getType();
            List<SubTask> subTasks = GsonClass.gson.fromJson(response1.body(), listType);

            assertFalse(subTasks.isEmpty(), "Список подзадач пустой.");
            assertEquals(3, subTasks.size(), "Получено не верное значение количества подзадач");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkCreateTask() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<Task>>() {
            }.getType();
            List<Task> tasks = GsonClass.gson.fromJson(response1.body(), listType);
            Task rsTask1 = tasks.get(0);
            Task rsTask2 = tasks.get(1);
            Task rsTask3 = tasks.get(2);

            assertEquals("Задача 1", rsTask1.getName(), "Задачи c таким именем не существует");
            assertEquals(TaskStatus.NEW, rsTask1.getStatus(), "Не совпадают статусы задач");
            assertEquals("Описание задачи 1", rsTask1.getDescription(), "Задачи с таким описанием нет");
            assertEquals(LocalDateTime.parse("01.01.2022 10:00", format), rsTask1.getStartTime(),
                    "Не совпадет время начала задач");
            assertEquals(Duration.ofMinutes(60), rsTask1.getDuration(), "Не совпадет длительность задач");
            assertEquals(TaskStatus.NEW, rsTask2.getStatus(), "Не совпадают статусы задач");
            assertEquals("Задача 2", rsTask2.getName(), "Задачи c таким именем не не существует");
            assertEquals("Описание задачи 2", rsTask2.getDescription(), "Задачи с таким описанием нет");
            assertEquals(LocalDateTime.parse("02.01.2022 10:00", format), rsTask2.getStartTime(),
                    "Не совпадет время начала задач");
            assertEquals(Duration.ofMinutes(60), rsTask2.getDuration(), "Не совпадет длительность задач");
            assertEquals(TaskStatus.NEW, rsTask3.getStatus(), "Не совпадают статусы задач");
            assertEquals("Задача 3", rsTask3.getName(), "Задачи c таким именем не не существует");
            assertEquals("Описание задачи 3", rsTask3.getDescription(), "Задачи с таким описанием нет");
            assertEquals(LocalDateTime.parse("03.01.2022 10:00", format), rsTask3.getStartTime(),
                    "Не совпадет время начала задач");
            assertEquals(Duration.ofMinutes(60), rsTask3.getDuration(), "Не совпадет длительность задач");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkCreateEpic() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<Epic>>() {
            }.getType();
            List<Epic> epics = GsonClass.gson.fromJson(response1.body(), listType);
            Epic rsEpic1 = epics.get(0);
            Epic rsEpic2 = epics.get(1);
            Epic rsEpic3 = epics.get(2);

            assertEquals("Эпик 1", rsEpic1.getName(), "Эпик c таким именем не существует");
            assertEquals(TaskStatus.NEW, rsEpic1.getStatus(), "Не совпадают статусы эпиков");
            assertEquals("Описание эпика 1", rsEpic1.getDescription(), "Эпика с таким описанием нет");
            assertEquals("Эпик 2", rsEpic2.getName(), "Эпик c таким именем не существует");
            assertEquals(TaskStatus.NEW, rsEpic2.getStatus(), "Не совпадают статусы эпиков");
            assertEquals("Описание эпика 2", rsEpic2.getDescription(), "Эпика с таким описанием нет");
            assertEquals("Эпик 3", rsEpic3.getName(), "Эпик c таким именем не существует");
            assertEquals(TaskStatus.NEW, rsEpic3.getStatus(), "Не совпадают статусы эпиков");
            assertEquals("Описание эпика 3", rsEpic3.getDescription(), "Эпика с таким описанием нет");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkCreateSubTask() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<SubTask>>() {
            }.getType();
            List<SubTask> subTasks = GsonClass.gson.fromJson(response1.body(), listType);
            SubTask rsSub1 = subTasks.get(0);
            SubTask rsSub2 = subTasks.get(1);
            SubTask rsSub3 = subTasks.get(2);

            assertEquals("Подзадача 1", rsSub1.getName(), "Подзадача c таким именем не существует");
            assertEquals(TaskStatus.NEW, rsSub1.getStatus(), "Не совпадают статусы подзадач");
            assertEquals("Описание подзадачи 1", rsSub1.getDescription(), "Подзадачи с таким описанием нет");
            assertEquals(LocalDateTime.parse("01.02.2022 10:00", format), rsSub1.getStartTime(),
                    "Не совпадет время начала подзадач");
            assertEquals(Duration.ofMinutes(60), rsSub1.getDuration(), "Не совпадет длительность подзадач");

            assertEquals("Подзадача 2", rsSub2.getName(), "Подзадача c таким именем не существует");
            assertEquals(TaskStatus.NEW, rsSub2.getStatus(), "Не совпадают статусы подзадач");
            assertEquals("Описание подзадачи 2", rsSub2.getDescription(), "Подзадачи с таким описанием нет");
            assertEquals(LocalDateTime.parse("02.02.2022 10:00", format), rsSub2.getStartTime(),
                    "Не совпадет время начала подзадач");
            assertEquals(Duration.ofMinutes(60), rsSub2.getDuration(), "Не совпадет длительность подзадач");

            assertEquals("Подзадача 3", rsSub3.getName(), "Подзадача c таким именем не существует");
            assertEquals(TaskStatus.NEW, rsSub3.getStatus(), "Не совпадают статусы подзадач");
            assertEquals("Описание подзадачи 3", rsSub3.getDescription(), "Подзадачи с таким описанием нет");
            assertEquals(LocalDateTime.parse("03.02.2022 10:00", format), rsSub3.getStartTime(),
                    "Не совпадет время начала подзадач");
            assertEquals(Duration.ofMinutes(60), rsSub3.getDuration(), "Не совпадет длительность подзадач");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkUpdateTask() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<Task>>() {
            }.getType();
            List<Task> tasks = GsonClass.gson.fromJson(response1.body(), listType);
            Task rsTask3 = tasks.get(2);
            rsTask3.setName("Задача 3 обновлена");
            rsTask3.setStatus(TaskStatus.DONE);
            rsTask3.setDescription("Описание задачи 3 обновлено");
            rsTask3.setStartTime(LocalDateTime.parse("03.01.2022 10:30", format));
            rsTask3.setDuration(Duration.ofMinutes(30));
            String rqTask3Upd = GsonClass.gson.toJson(rsTask3);
            URI url2 = URI.create("http://localhost:8080/tasks/task/?id=3");
            final HttpRequest.BodyPublisher body2 = HttpRequest.BodyPublishers.ofString(rqTask3Upd);
            HttpRequest request2 = HttpRequest.newBuilder().uri(url2).POST(body2).build();
            client.send(request2, HttpResponse.BodyHandlers.ofString());

            assertEquals("Задача 3 обновлена", rsTask3.getName(), "Задачи c таким именем не существует");
            assertEquals(TaskStatus.DONE, rsTask3.getStatus(), "Не совпадают статусы задач");
            assertEquals("Описание задачи 3 обновлено", rsTask3.getDescription(), "Задачи с таким описанием нет");
            assertEquals(LocalDateTime.parse("03.01.2022 10:30", format), rsTask3.getStartTime(), "Не совпадет время начала задач");
            assertEquals(Duration.ofMinutes(30), rsTask3.getDuration(), "Не совпадет длительность задач");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkUpdateEpic() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
        URI url2 = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<SubTask>>() {
            }.getType();
            List<SubTask> subTasks = GsonClass.gson.fromJson(response1.body(), listType);
            SubTask rsSub1 = subTasks.get(0);
            SubTask rsSub2 = subTasks.get(1);
            SubTask rsSub3 = subTasks.get(2);
            rsSub1.setStatus(TaskStatus.DONE);
            rsSub1.setDuration(Duration.ofMinutes(45));
            rsSub2.setStatus(TaskStatus.DONE);
            rsSub2.setDuration(Duration.ofMinutes(45));
            rsSub3.setStatus(TaskStatus.DONE);
            rsSub3.setDuration(Duration.ofMinutes(30));
            String rqSub1Upd = GsonClass.gson.toJson(rsSub1);
            String rqSub2Upd = GsonClass.gson.toJson(rsSub2);
            String rqSub3Upd = GsonClass.gson.toJson(rsSub3);
            URI url1_1 = URI.create("http://localhost:8080/tasks/subtask/?id=7");
            URI url1_2 = URI.create("http://localhost:8080/tasks/subtask/?id=8");
            URI url1_3 = URI.create("http://localhost:8080/tasks/subtask/?id=9");
            final HttpRequest.BodyPublisher body1_1 = HttpRequest.BodyPublishers.ofString(rqSub1Upd);
            final HttpRequest.BodyPublisher body1_2 = HttpRequest.BodyPublishers.ofString(rqSub2Upd);
            final HttpRequest.BodyPublisher body1_3 = HttpRequest.BodyPublishers.ofString(rqSub3Upd);
            HttpRequest request1_1 = HttpRequest.newBuilder().uri(url1_1).POST(body1_1).build();
            HttpRequest request1_2 = HttpRequest.newBuilder().uri(url1_2).POST(body1_2).build();
            HttpRequest request1_3 = HttpRequest.newBuilder().uri(url1_3).POST(body1_3).build();
            client.send(request1_1, HttpResponse.BodyHandlers.ofString());
            client.send(request1_2, HttpResponse.BodyHandlers.ofString());
            client.send(request1_3, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            listType = new TypeToken<List<Epic>>() {
            }.getType();
            List<Epic> epics = GsonClass.gson.fromJson(response2.body(), listType);
            Epic rsEpic1 = epics.get(0);
            Epic rsEpic3 = epics.get(2);
            rsEpic3.setName("Эпик 3 обновлен");
            rsEpic3.setStatus(TaskStatus.IN_PROGRESS);
            rsEpic3.setDescription("Описание эпика 3 обновлено");
            String rqEpic3Upd = GsonClass.gson.toJson(rsEpic3);
            URI url2_1 = URI.create("http://localhost:8080/tasks/epic/?id=6");
            final HttpRequest.BodyPublisher body2_1 = HttpRequest.BodyPublishers.ofString(rqEpic3Upd);
            HttpRequest request2_1 = HttpRequest.newBuilder().uri(url2_1).POST(body2_1).build();
            client.send(request2_1, HttpResponse.BodyHandlers.ofString());

            assertEquals(TaskStatus.DONE, rsEpic1.getStatus(), "Статус эпика не изменился при изменении связанных подзадач");
            assertEquals(Duration.ofMinutes(120), rsEpic1.getDuration(), "Длительность эпика рассчитана не верно");
            assertEquals("Эпик 3 обновлен", rsEpic3.getName(), "Эпик c таким именем не существует");
            assertEquals(TaskStatus.IN_PROGRESS, rsEpic3.getStatus(), "Не совпадают статусы эпиков");
            assertEquals("Описание эпика 3 обновлено", rsEpic3.getDescription(), "Эпика с таким описанием нет");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkUpdateSubTask() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<SubTask>>() {
            }.getType();
            List<SubTask> subTasks = GsonClass.gson.fromJson(response1.body(), listType);
            SubTask rsSub3 = subTasks.get(2);
            rsSub3.setName("Подзадача 3 обновлена");
            rsSub3.setStatus(TaskStatus.DONE);
            rsSub3.setDescription("Описание подзадачи 3 обновлено");
            rsSub3.setStartTime(LocalDateTime.parse("03.02.2022 10:30", format));
            rsSub3.setDuration(Duration.ofMinutes(30));
            String rqSub3Upd = GsonClass.gson.toJson(rsSub3);
            URI url2 = URI.create("http://localhost:8080/tasks/subtask/?id=9");
            final HttpRequest.BodyPublisher body2 = HttpRequest.BodyPublishers.ofString(rqSub3Upd);
            HttpRequest request2 = HttpRequest.newBuilder().uri(url2).POST(body2).build();
            client.send(request2, HttpResponse.BodyHandlers.ofString());

            assertEquals("Подзадача 3 обновлена", rsSub3.getName(), "Задачи c таким именем не существует");
            assertEquals(TaskStatus.DONE, rsSub3.getStatus(), "Не совпадают статусы задач");
            assertEquals("Описание подзадачи 3 обновлено", rsSub3.getDescription(), "Задачи с таким описанием нет");
            assertEquals(LocalDateTime.parse("03.02.2022 10:30", format), rsSub3.getStartTime(), "Не совпадет время начала задач");
            assertEquals(Duration.ofMinutes(30), rsSub3.getDuration(), "Не совпадет длительность задач");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkGetTaskById() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/task/?id=2");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Task rsTask2 = GsonClass.gson.fromJson(response1.body(), Task.class);

            assertEquals(2, rsTask2.getId(), "Получен неправильный id задачи");
            assertEquals("Задача 2", rsTask2.getName(), "Задачи c таким именем не существует");
            assertEquals("Описание задачи 2", rsTask2.getDescription(), "Задачи с таким описанием нет");
            assertEquals(TaskStatus.NEW, rsTask2.getStatus(), "Не совпадают статусы задач");
            assertEquals(LocalDateTime.parse("02.01.2022 10:00", format), rsTask2.getStartTime(),
                    "Не совпадет время начала задач");
            assertEquals(Duration.ofMinutes(60), rsTask2.getDuration(), "Не совпадет длительность задач");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkGetEpicById() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/epic/?id=5");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Epic rsEpic2 = GsonClass.gson.fromJson(response1.body(), Epic.class);

            assertEquals(5, rsEpic2.getId(), "Получен неправильный id эпика");
            assertEquals("Эпик 2", rsEpic2.getName(), "Эпик c таким именем не существует");
            assertEquals("Описание эпика 2", rsEpic2.getDescription(), "Эпика с таким описанием нет");
            assertEquals(TaskStatus.NEW, rsEpic2.getStatus(), "Не совпадают статусы эпиков");
            assertNull(rsEpic2.getStartTime(), "Эпик без подзадач не может иметь StartTime");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkGetSubTaskById() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/subtask/?id=8");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            SubTask rs = GsonClass.gson.fromJson(response1.body(), SubTask.class);

            assertEquals(8, rs.getId(), "Получен неправильный id подзадачи");
            assertEquals("Подзадача 2", rs.getName(), "Подзадачи c таким именем не существует");
            assertEquals("Описание подзадачи 2", rs.getDescription(), "Подзадачи с таким описанием нет");
            assertEquals(TaskStatus.NEW, rs.getStatus(), "Не совпадают статусы подзадач");
            assertEquals(LocalDateTime.parse("02.02.2022 10:00", format), rs.getStartTime(),
                    "Не совпадет время начала подзадач");
            assertEquals(Duration.ofMinutes(60), rs.getDuration(), "Не совпадет длительность подзадач");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkDeleteTasksById() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).DELETE().build();
        URI url2 = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<Task>>() {
            }.getType();
            List<Task> tasks = GsonClass.gson.fromJson(response2.body(), listType);

            assertEquals(200, response1.statusCode(), "Метод вернул код ошибки. " + response1.statusCode());
            assertEquals(2, tasks.size(), "Не верное количество задач после удаления");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkDeleteEpicById() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/epic/?id=4");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).DELETE().build();
        URI url2 = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<Epic>>() {
            }.getType();
            List<Epic> epics = GsonClass.gson.fromJson(response2.body(), listType);

            assertEquals(200, response1.statusCode(), "Метод вернул код ошибки. " + response1.statusCode());
            assertEquals(2, epics.size(), "Не верное количество эпиков после удаления");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkDeleteSubTasksById() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/subtask/?id=7");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).DELETE().build();
        URI url2 = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<SubTask>>() {
            }.getType();
            List<SubTask> subTasks = GsonClass.gson.fromJson(response2.body(), listType);

            assertEquals(200, response1.statusCode(), "Метод вернул код ошибки. " + response1.statusCode());
            assertEquals(2, subTasks.size(), "Не верное количество подзадач после удаления");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkDeleteAllTasks() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).DELETE().build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<Task>>() {
            }.getType();
            List<Task> tasks = GsonClass.gson.fromJson(response2.body(), listType);

            assertEquals(200, response1.statusCode(), "Метод вернул код ошибки. " + response1.statusCode());
            assertTrue(tasks.isEmpty(), "Список задач после удаления не пустой");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkDeleteAllEpic() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).DELETE().build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<Epic>>() {
            }.getType();
            List<Epic> epics = GsonClass.gson.fromJson(response2.body(), listType);

            assertEquals(200, response1.statusCode(), "Метод вернул код ошибки. " + response1.statusCode());
            assertTrue(epics.isEmpty(), "Список эпиков после удаления не пустой");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkDeleteAllSubTasks() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).DELETE().build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(url1).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<SubTask>>() {
            }.getType();
            List<SubTask> subTasks = GsonClass.gson.fromJson(response2.body(), listType);

            assertEquals(200, response1.statusCode(), "Метод вернул код ошибки. " + response1.statusCode());
            assertTrue(subTasks.isEmpty(), "Список подзадач после удаления не пустой");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkGetEpicSubTasks() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/epic/?id=4");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
        URI url2 = URI.create("http://localhost:8080/tasks/subtask/epic/?id=4");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Epic rsEpic1 = GsonClass.gson.fromJson(response1.body(), Epic.class);
            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<SubTask>>() {
            }.getType();
            List<SubTask> epicSubTasks = GsonClass.gson.fromJson(response2.body(), listType);
            var s1 = epicSubTasks.get(0);
            var s2 = epicSubTasks.get(1);
            var s3 = epicSubTasks.get(2);

            assertEquals(rsEpic1.getId(), s1.getEpicId(), "Подзадача не привязана к эпику");
            assertEquals(rsEpic1.getId(), s2.getEpicId(), "Подзадача не привязана к эпику");
            assertEquals(rsEpic1.getId(), s3.getEpicId(), "Подзадача не привязана к эпику");
            assertEquals(List.of(s1, s2, s3), epicSubTasks, "Список подзадач эпика с ошибкой");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkGetHistoryName() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/epic/?id=5");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
        URI url2 = URI.create("http://localhost:8080/tasks/task/?id=2");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).GET().build();
        URI url3 = URI.create("http://localhost:8080/tasks/subtask/?id=8");
        HttpRequest request3 = HttpRequest.newBuilder().uri(url3).GET().build();
        URI url4 = URI.create("http://localhost:8080/tasks/subtask/?id=8");
        HttpRequest request4 = HttpRequest.newBuilder().uri(url4).GET().build();
        URI url5 = URI.create("http://localhost:8080/tasks/history");
        HttpRequest request5 = HttpRequest.newBuilder().uri(url5).GET().build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            var e5 = GsonClass.gson.fromJson(response1.body(), Epic.class);
            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            var t2 = GsonClass.gson.fromJson(response2.body(), Task.class);
            HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
            var s8 = GsonClass.gson.fromJson(response3.body(), SubTask.class);
            client.send(request4, HttpResponse.BodyHandlers.ofString()); // повторный вызов s8
            HttpResponse<String> response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<Task>>() {
            }.getType();
            List<Task> result = GsonClass.gson.fromJson(response5.body(), listType);

            assertFalse(result.isEmpty(), "Список задач, хранящийся в Истории пустой.");
            assertEquals(e5.getId(), result.get(0).getId(), "Индексы вызванных задач и история не совпадают");
            assertEquals(t2.getId(), result.get(1).getId(), "Индексы вызванных задач и история не совпадают");
            assertEquals(s8.getId(), result.get(2).getId(), "Индексы вызванных задач и история не совпадают");
            assertEquals(3, result.size(), "В историю сохраняются повторные вызовы задач");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }

    @Test
    void checkGetPrioritizedTasks() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create("http://localhost:8080/tasks/");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<Task>>() {
            }.getType();
            List<Task> checkResult = GsonClass.gson.fromJson(response1.body(), listType);

            assertNotNull(checkResult, "Метод возвращает null.");
            assertFalse(checkResult.isEmpty(), "Список приоритетных задач пуст.");
            assertEquals(6, checkResult.size(), "Неверное количество задач в отсортированном списке.");
            assertTrue(
                    checkResult.get(0).getStartTime().isBefore(checkResult.get(1).getStartTime()) &&
                            checkResult.get(1).getStartTime().isBefore(checkResult.get(2).getStartTime()) &&
                            checkResult.get(2).getStartTime().isBefore(checkResult.get(3).getStartTime()) &&
                            checkResult.get(3).getStartTime().isBefore(checkResult.get(4).getStartTime()) &&
                            checkResult.get(4).getStartTime().isBefore(checkResult.get(5).getStartTime()),
                    "Время старта каждой следующей задачи должно быть позже предыдущей");
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка. " + e.getMessage());
        }
    }
}