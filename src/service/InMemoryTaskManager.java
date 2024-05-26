package service;

import model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static model.Status.*;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, SubTask> subTasks;
    private final HistoryManager historyManager;
    int id = 0;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    /**
     * TASK
     * Создание задачи
     */
    @Override
    public void createTask(Task task) {
        tasks.put(id, task);
        task.setId(id++);
        task.setStatus(NEW);
    }

    /**
     * Получение списка всех задач
     */
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * Удаление всех задач
     */
    @Override
    public void removeAllTasks() {
        for (int taskId : tasks.keySet()){
            historyManager.remove(taskId);
        }
        tasks.clear();
    }

    /**
     * Получение задачи по идентификатору
     */
    @Override
    public Task getTask(Integer taskId) {
        Task task = tasks.get(taskId);
        if (task != null)
            historyManager.add(task);
        return task;
    }

    /**
     * Обновление задачи
     */
    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    /**
     * Удаление задачи по идентификатору
     */
    @Override
    public void removeTask(Integer taskId) {
        historyManager.remove(taskId);
        tasks.remove(taskId);
    }

    /**
     * EPIC
     * Создание эпика
     */
    @Override
    public void createEpic(Epic epic) {
        epics.put(id, epic);
        epic.setId(id++);
        epic.setStatus(NEW);
    }

    /**
     * Получение списка всех эпиков
     */
    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    /**
     * Удаление всех эпиков
     */
    @Override
    public void removeAllEpics() {
        for (int epicId : epics.keySet()){
            historyManager.remove(epicId);
        }
        for (int subTasksId : subTasks.keySet()){
            historyManager.remove(subTasksId);
        }
        epics.clear();
        subTasks.clear();
    }

    /**
     * Получение эпика по идентификатору
     */
    @Override
    public Epic getEpic(Integer epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null)
            historyManager.add(epic);
        return epic;
    }

    /**
     * Обновление эпика
     */
    @Override
    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        if (saved == null) {
            return;
        }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
    }

    /**
     * Удаление эпика по идентификатору
     */
    @Override
    public void removeEpic(Integer epicId) {
        List<Integer> epicSubTasksIds = epics.get(epicId).getSubTasksIds();
        historyManager.remove(epicId);
        for (Integer subTaskId : epicSubTasksIds){
            historyManager.remove(subTaskId);
            subTasks.remove(subTaskId);
        }
        epics.remove(epicId);
    }

    /**
     * Получение списка всех подзадач определённого эпика
     */
    @Override
    public ArrayList<SubTask> getEpicSubTasks(Integer epicId) {
        if (epics.get(epicId) == null) {
            return new ArrayList<>();
        }
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
            for (Integer taskId : epics.get(epicId).getSubTasksIds()) {
                epicSubTasks.add(subTasks.get(taskId));
            }
        return epicSubTasks;
    }

    /**
     * SUBTASK
     * Создание подзадачи
     */
    @Override
    public void createSubTask(SubTask subtask) {
        subTasks.put(id, subtask);
        int epicId = subtask.getEpicId();
        subtask.setEpicId(epicId);
        subtask.setId(id);
        Epic epicWithSubTasks = epics.get(subtask.getEpicId());
        epicWithSubTasks.getSubTasksIds().add(id++);
        epicStatus(epics.get(epicId));
    }

    /**
     * Получение списка всех подзадач
     */
    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    /**
     * Удаление всех подзадач
     */
    @Override
    public void removeAllSubTasks() {
        for (int subTasksId : subTasks.keySet()){
            historyManager.remove(subTasksId);
        }
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubTasksIds().clear();
            epic.setStatus(NEW);
        }
    }

    /**
     * Получение подзадачи по идентификатору
     */
    @Override
    public Task getSubTask(Integer subTaskId) {
        Task subTask = subTasks.get(subTaskId);
        if (subTask != null)
            historyManager.add(subTask);
        return subTask;
    }

    /**
     * Обновление подзадачи
     */
    @Override
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        epicStatus(epics.get(subTask.getEpicId()));
    }

    /**
     * Удаление подзадачи по идентификатору
     */
    @Override
    public void removeSubTask(Integer subTaskId) {
        int epicId = subTasks.get(subTaskId).getEpicId();
        List<Integer> subTasksIds = epics.get(epicId).getSubTasksIds();
        subTasksIds.remove(subTaskId);
        historyManager.remove(subTaskId);
        subTasks.remove(subTaskId);
        epicStatus(epics.get(epicId));
    }

    /**
     * Простановка статуса эпика на основе статуса его подзадач
     */
    private void epicStatus(Epic epic) {
        List<Integer> listOfIds = epic.getSubTasksIds();
        int statusNew = 0;
        int statusDone = 0;
        for (Integer subTaskId : listOfIds) {
            Status status = subTasks.get(subTaskId).getStatus();
            if (status == NEW) {
                statusNew++;
            } else if (status == DONE) {
                statusDone++;
            }
        }
        if (listOfIds.isEmpty() || statusNew == listOfIds.size()) {
            epic.setStatus(NEW);
        } else if (statusDone == listOfIds.size()) {
            epic.setStatus(DONE);
        } else
            epic.setStatus(IN_PROGRESS);
    }
}

