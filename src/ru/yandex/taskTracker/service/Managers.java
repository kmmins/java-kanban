package ru.yandex.taskTracker.service;

import java.nio.file.Paths;

public class Managers {

    /** <p> Выбор реализации работы программы по умолчанию. </>
     *
     * @return возвращает реализацию работы программы установленную по умолчанию
     */
    public static TaskManager getDefault() throws ManagerSaveException {
       return getHttp();
    }

    /** <p> Выбор реализации работы программы с хранением в памяти. </>
     *
     * @return возвращает реализацию работы программы с хранением в памяти
     */
    public static TaskManager getInMemory() {
        return new InMemoryTaskManager();
    }

    /** <p> Выбор реализации работы программы с хранением в специальном файле. </>
     *
     * @return возвращает реализацию работы программы с хранением в специальном файле
     */
    public static TaskManager getFileBacked() throws ManagerSaveException {
        return FileBackedTasksManager.loadFromFile(Paths.get(".resources\\autosave.csv").toFile());
    }

    /** <p> Выбор реализации работы программы с хранением на Http-сервере. </>
     *
     * @return возвращает реализацию работы программы с хранением на Http-сервере
     */
    public static HttpTaskManager getHttp() throws ManagerSaveException {
        return new HttpTaskManager("http://localhost:8078/");
    }

    /** <p> Выбор реализации отслеживания истории по умолчанию. </>
     *
     * @return возвращает реализацию отслеживания истории вызова задач по умолчанию
     */
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}