package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    TaskManager taskManager;
    Task task1;
    Task task2;
    Task task3;
    Epic epic1;
    Epic epic2;
    SubTask subTask1;
    SubTask subTask2;
    SubTask subTask3;
    List<Task> expectedHistory;
    @BeforeEach
    void init() {
        taskManager = Managers.getDefault();

        task1 = new Task("Task 1", "Description of Task 1", Status.NEW);
        taskManager.createTask(task1);
        task2 = new Task("Task 2", "Description of Task 2", Status.NEW);
        taskManager.createTask(task2);
        task3 = new Task("Task 3", "Description of Task 3", Status.NEW);
        taskManager.createTask(task3);
        epic1 = new Epic("Epic 1", "Description of Epic 1");
        taskManager.createEpic(epic1);
        epic2 = new Epic("Epic 2", "Description of Epic 1");
        taskManager.createEpic(epic2);
        subTask1 = new SubTask("SubTask 1.1", "Description of SubTask 1.1", Status.NEW, epic1.getId());
        taskManager.createSubTask(subTask1);
        subTask2 = new SubTask("SubTask 1.2", "Description of SubTask 1.2", Status.DONE, epic1.getId());
        taskManager.createSubTask(subTask2);
        subTask3 = new SubTask("SubTask 2.1", "Description of SubTask 2.1", Status.DONE, epic2.getId());
        taskManager.createSubTask(subTask3);
    }

    /**
     * Получение истории просмотров
     * Если какую-либо задачу посещали несколько раз, то в истории должен остаться только её последний просмотр.
     */
    @Test
    void shouldGetHistory() {
        prepareExpectedHistoryList();
        List<Task> history;
        taskManager.getTask(task1.getId());
        taskManager.getEpic(epic1.getId());
        taskManager.getSubTask(subTask1.getId());
        taskManager.getAllTasks(); // не должен попасть в список
        taskManager.getTask(task1.getId());
        history = taskManager.getHistory();
        assertEquals(expectedHistory, history, "Список просмотренных задач не совпадает");
    }

    void prepareExpectedHistoryList () {
        expectedHistory = new ArrayList<>();
        expectedHistory.add(taskManager.getEpic(epic1.getId()));
        expectedHistory.add(taskManager.getSubTask(subTask1.getId()));
        expectedHistory.add(taskManager.getTask(task1.getId()));
    }

    /**
     * Обновление и удаление записей из истории просмотров
     */
    @Test
    void shouldUpdateAndRemoveTaskHistory() {
        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());
        assertEquals(2, taskManager.getHistory().size(), "Просмотр задач не записан в историю");
        taskManager.removeTask(task1.getId());
        assertEquals(1, taskManager.getHistory().size(), "Задача после удаления не удалена из истории");
        taskManager.removeAllTasks();
        assertEquals(0, taskManager.getHistory().size(), "После удаления всех задач история не очищена");
    }

    @Test
    void shouldUpdateAndRemoveEpicHistory() {
        taskManager.getEpic(epic1.getId());
        taskManager.getSubTask(subTask1.getId());
        taskManager.getEpic(epic2.getId());
        taskManager.getSubTask(subTask3.getId());
        assertEquals(4, taskManager.getHistory().size(), "Просмотр эпиков и подзадач не записан в историю");
        taskManager.removeEpic(epic1.getId());
        assertEquals(2, taskManager.getHistory().size(), "Эпик с подзадачей после удаления не удалены из истории");
        taskManager.removeAllEpics();
        assertEquals(0, taskManager.getHistory().size(), "После удаления всех эпиков история не очищена");
    }

    @Test
    void shouldUpdateAndRemoveSubTaskHistory() {
        taskManager.getSubTask(subTask1.getId());
        taskManager.getSubTask(subTask2.getId());
        assertEquals(2, taskManager.getHistory().size(), "Просмотр подзадач не записан в историю");
        taskManager.removeSubTask(subTask1.getId());
        assertEquals(1, taskManager.getHistory().size(), "Подзадача после удаления не удалена из истории");
        taskManager.removeAllSubTasks();
        assertEquals(0, taskManager.getHistory().size(), "После удаления всех подзадач история не очищена");
    }
}