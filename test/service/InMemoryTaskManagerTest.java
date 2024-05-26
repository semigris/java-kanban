package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    TaskManager taskManager;
    Task task1;
    Task task2;
    Task task3;
    Epic epic1;
    SubTask subTask1;
    SubTask subTask2;

    @BeforeEach
    void init(){
        taskManager = Managers.getDefault();

        task1 = new Task("Task 1", "Description of Task 1", Status.NEW);
        taskManager.createTask(task1);
        task2 = new Task("Task 2", "Description of Task 2", Status.NEW);
        taskManager.createTask(task2);
        task3 = new Task("Task 3", "Description of Task 3", Status.NEW);
        taskManager.createTask(task3);
        epic1 = new Epic("Epic 1", "Description of Epic 1");
        taskManager.createEpic(epic1);
        subTask1 = new SubTask("SubTask 1.1", "Description of SubTask 1.1", Status.NEW, epic1.getId());
        taskManager.createSubTask(subTask1);
        subTask2 = new SubTask("SubTask 1.2", "Description of SubTask 1.2", Status.DONE, epic1.getId());
        taskManager.createSubTask(subTask2);
    }

    /**
     * Создание объектов менеджера задач
     */
    @Test
    void shouldCreateTask() {
        Task task = new Task("Test task", "Test task description", Status.NEW);
        taskManager.createTask(task);
        Task taskById = taskManager.getTask(task.getId());
        assertEquals(task, taskById, "Созданная задача и найденная по id не совпадают");
        assertNotNull(taskById, "Задача по id не найдена");
    }

    @Test
    void shouldCreateEpic() {
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.createEpic(epic);
        Task epicById = taskManager.getEpic(epic.getId());
        assertEquals(epic, epicById, "Созданный эпик и найденный по id не совпадают");
        assertNotNull(epicById, "Эпик по id не найден");
    }

    @Test
    void shouldCreateSubTask() {
        assertEquals(Status.IN_PROGRESS, taskManager.getEpic(epic1.getId()).getStatus(), "Исходный статус эпика должен быть IN_PROGRESS");
        SubTask subTask = new SubTask("Test subTask", "Test subTask description",  Status.NEW, epic1.getId());
        taskManager.createSubTask(subTask);
        Task subTaskById = taskManager.getSubTask(subTask.getId());
        assertEquals(subTask, subTaskById, "Созданная подзадача и найденная по id не совпадают");
        assertNotNull(subTaskById, "Подзадача по id не найдена");
        assertEquals(Status.IN_PROGRESS, taskManager.getEpic(epic1.getId()).getStatus(), "Статус эпика изменился после добавления новой подзадачи");
    }

    /**
     * Получение списка всех объектов менеджера задач
     */
    @Test
    void shouldGetAllTasks() {
        List<Task> expectedTaskList = new ArrayList<>();
        expectedTaskList.add(task1);
        expectedTaskList.add(task2);
        expectedTaskList.add(task3);
        assertEquals(expectedTaskList.size(), taskManager.getAllTasks().size(),"Размер списоков задач не совпадает");
        assertEquals(expectedTaskList, taskManager.getAllTasks(),  "Списки задач не совпадают");
    }

    @Test
    void shouldGetAllEpics() {
        List<Epic> expectedEpicList = new ArrayList<>();
        expectedEpicList.add(epic1);
        assertEquals(expectedEpicList.size(), taskManager.getAllEpics().size(),"Размер списоков эпиков не совпадает");
        assertEquals(expectedEpicList, taskManager.getAllEpics(),  "Списки эпиков не совпадают");
    }

    @Test
    void shouldGetAllSubTasks() {
        List<SubTask> expectedSubTaskList = new ArrayList<>();
        expectedSubTaskList.add(subTask1);
        expectedSubTaskList.add(subTask2);
        assertEquals(expectedSubTaskList.size(), taskManager.getAllSubTasks().size(),"Размер списка подзадач не совпадает");
        assertEquals(expectedSubTaskList, taskManager.getAllSubTasks(),  "Списки подзадач не совпадают");
    }

    /**
     * Удаление всех объектов менеджера задач
     */
    @Test
    void shouldRemoveAllTasks() {
        assertEquals(3, taskManager.getAllTasks().size());
        taskManager.removeAllTasks();
        assertEquals(0, taskManager.getAllTasks().size(), "Список задач не очищен");
    }

    @Test
    void shouldRemoveAllEpics() {
        assertEquals(1, taskManager.getAllEpics().size());
        assertEquals(2, taskManager.getAllSubTasks().size());
        taskManager.removeAllEpics();
        assertEquals(0, taskManager.getAllEpics().size(), "Список эпиков не очищен");
        assertEquals(0, taskManager.getAllSubTasks().size(), "Список подзадач после удаления всех эпиков не очищен");
    }

    @Test
    void shouldRemoveAllSubTasks() {
        assertEquals(2, taskManager.getAllSubTasks().size());
        taskManager.removeAllSubTasks();
        assertEquals(0, taskManager.getAllSubTasks().size(), "Список подзадач не очищен");
    }

    /**
     * Обновление объектов менеджера задач
     */
    @Test
    void shouldUpdateTask() {
        Task updateTask = new Task(task1.getId(), "NewTask 1", "NewDescription of Task 1", Status.DONE);
        taskManager.updateTask(updateTask);
        assertEquals(updateTask, taskManager.getTask(task1.getId()), "Задача не обновилась");
    }

    @Test
    void shouldUpdateEpic() {
        Epic updateEpic = new Epic(epic1.getId(), "NewEpic 1", "NewDescription of Epic 1");
        List<Integer> subTaskIdsList = taskManager.getEpic(epic1.getId()).getSubTasksIds();
        taskManager.updateEpic(updateEpic);
        assertEquals(updateEpic.getId(), taskManager.getEpic(epic1.getId()).getId(), "Эпик не обновился");
        assertEquals(subTaskIdsList, taskManager.getEpic(epic1.getId()).getSubTasksIds(), "Эпик не обновился");
        assertEquals(updateEpic.getName(), taskManager.getEpic(epic1.getId()).getName(), "Эпик не обновился");
        assertEquals(updateEpic.getDescription(), taskManager.getEpic(epic1.getId()).getDescription(), "Эпик не обновился");
    }

    @Test
    void shouldUpdateSubTask() {
        assertEquals(Status.IN_PROGRESS, taskManager.getEpic(epic1.getId()).getStatus(), "Исходный статус эпика должен быть IN_PROGRESS");
        SubTask updateSubTask = new SubTask(subTask1.getId(), "NewSubTask 1.1", "NewDescription of SubTask 1.1", Status.DONE, epic1.getId());
        taskManager.updateSubTask(updateSubTask);
        assertEquals(updateSubTask, taskManager.getSubTask(subTask1.getId()), "Подзадача не обновилась");
        assertEquals(Status.DONE, taskManager.getEpic(epic1.getId()).getStatus(), "Статус эпика не изменился после обновления статуса подзадачи");
    }

    /**
     * Удаление по идентификатору объектов менеджера задач
     */
    @Test
    void shouldRemoveTaskFirst() {
        List<Task> expectedTaskList = new ArrayList<>();
        expectedTaskList.add(task2);
        expectedTaskList.add(task3);
        taskManager.removeTask(task1.getId());
        assertEquals(expectedTaskList, taskManager.getAllTasks(),  "Списки задач после удаления Task 1 не совпадают");
        assertNull(taskManager.getTask(task1.getId()), "Задача Task 1 не удалена по id");
    }

    @Test
    void shouldRemoveTaskMiddle() {
        List<Task> expectedTaskList = new ArrayList<>();
        expectedTaskList.add(task1);
        expectedTaskList.add(task3);
        taskManager.removeTask(task2.getId());
        assertEquals(expectedTaskList, taskManager.getAllTasks(),  "Списки задач после удаления Task 2 не совпадают");
        assertNull(taskManager.getTask(task2.getId()), "Задача Task 2 не удалена по id");
    }

    @Test
    void shouldRemoveTaskLast() {
        List<Task> expectedTaskList = new ArrayList<>();
        expectedTaskList.add(task1);
        expectedTaskList.add(task2);
        taskManager.removeTask(task3.getId());
        assertEquals(expectedTaskList, taskManager.getAllTasks(),  "Списки задач после удаления Task 3 не совпадают");
        assertNull(taskManager.getTask(task3.getId()), "Задача Task 3 не удалена по id");
    }

    @Test
    void shouldRemoveEpic() {
        assertEquals(2, taskManager.getAllSubTasks().size());
        taskManager.removeEpic(epic1.getId());
        assertNull(taskManager.getEpic(epic1.getId()), "Эпик по id не удален");
        assertEquals(0, taskManager.getAllSubTasks().size(), "Список подзадач после удаления эпика не очищен");
    }

    @Test
    void shouldRemoveSubTask() {
        assertEquals(Status.IN_PROGRESS, taskManager.getEpic(epic1.getId()).getStatus(), "Исходный статус эпика должен быть IN_PROGRESS");
        taskManager.removeSubTask(subTask2.getId());
        assertNull(taskManager.getSubTask(subTask2.getId()), "Подзадача по id не удалена");
        assertEquals(Status.NEW, taskManager.getEpic(epic1.getId()).getStatus(), "Статус эпика не изменился после удаления подзадачи");
    }

    /**
     * Получение списка всех подзадач определённого эпика
     */
    @Test
    void shouldGetEpicSubTasks() {
        List<SubTask> expectedSubTaskList = new ArrayList<>();
        expectedSubTaskList.add(subTask1);
        expectedSubTaskList.add(subTask2);
        taskManager.getEpicSubTasks(epic1.getId());
        assertEquals(expectedSubTaskList.size(), taskManager.getAllSubTasks().size(),"Размер списоков подзадач не совпадает");
        assertEquals(expectedSubTaskList, taskManager.getAllSubTasks(),  "Списки подзадач не совпадают");
    }
}