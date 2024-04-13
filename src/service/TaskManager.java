package service;

import model.*;
import java.util.ArrayList;
import java.util.HashMap;

import static model.Status.*;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subTasks;
    int id = 0;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }

    /**
     * TASK
     */
    public void createTask(Task task) {
        tasks.put(id, task);
        task.setId(id++);
        task.setStatus(NEW);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    /**
     * EPIC
     */
    public void createEpic(Epic epic) {
        epics.put(id, epic);
        epic.setId(id++);
        epic.setStatus(NEW);
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void removeAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        if (saved == null) {
            return;
        }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
    }

    public void removeEpic(int id) {
        epics.remove(id);
    }

    public ArrayList<SubTask> getEpicSubTasks(int epicId) {
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
            for (Integer taskId : epics.get(epicId).getSubTasksIds()) {
                epicSubTasks.add(subTasks.get(taskId));
            }
        return epicSubTasks;
    }

    /**
     * SUBTASK
     */
    public void createSubTask(SubTask subtask) {
        subTasks.put(id, subtask);
        int epicId = subtask.getEpicId();
        subtask.setEpicId(epicId);
        subtask.setId(id);
        Epic epicWithSubtask = epics.get(subtask.getEpicId());
        epicWithSubtask.getSubTasksIds().add(id++);
        epicStatus(epics.get(epicId));
    }

    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public void removeAllSubTasks() {
        subTasks.clear();
    }

    public Task getSubTask(int id) {
        return subTasks.get(id);
    }

    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        epicStatus(epics.get(subTask.getEpicId()));
    }

    public void removeSubTask(int id) {
        int epicId = subTasks.get(id).getEpicId();
        subTasks.remove(id);
    }

    private void epicStatus(Epic epic) {
        ArrayList<Integer> listOfIds = epic.getSubTasksIds();
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

