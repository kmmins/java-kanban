package ru.yandex.taskTracker.service;

import java.io.IOException;
import java.nio.file.Paths;

public class Managers {

    /** <p> Выбор текущей реализации работы программы. </>
     *
     * @return возвращает реализацию работы программы с хранением в памяти
     */
    public static TaskManager getDefault() throws ManagerSaveException {
       return getHttp();
    }

    public static TaskManager getInMemory() {
        return new InMemoryTaskManager();
    }


    /** <p> Выбор текущей реализации работы программы. </>
     *
     * @return возвращает реализацию работы программы с хранением в специальном файле
     */
    public static TaskManager getFileBacked() throws ManagerSaveException {
        return FileBackedTasksManager.loadFromFile(Paths.get(".resources\\autosave.csv").toFile());
    }

    public static HttpTaskManager getHttp() throws ManagerSaveException {
        return new HttpTaskManager("http://localhost:8078/");
    }


    /** <p> Выбор текущей реализации отслеживания истории. </>
     *
     * @return при запуске тестирования программы возвращает реализацию отслеживания истории вызова задач
     */
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}