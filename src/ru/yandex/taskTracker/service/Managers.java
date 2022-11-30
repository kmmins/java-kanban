package ru.yandex.taskTracker.service;

import java.nio.file.Paths;

public class Managers  {

    /** <p> Выбор текущей реализации работы программы.</>
     *
     * @return возвращает реализацию работы программы с хранением в памяти
     */
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    /** <p> Выбор текущей реализации работы программы.</>
     *
     * @return возвращает реализацию работы программы с хранением в специальном файле
     */
    public static TaskManager getFileBacked() throws ManagerSaveException {

        return FileBackedTasksManager.loadFromFile(Paths.get(".resources\\autosave.csv").toFile());

    }

    /** <p> Выбор текущей реализации отслеживания истории.</>
     *
     * @return при запуске тестирования программы возвращает реализацию отслеживания истории вызова задач
     */
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}