package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    List<Task> getHistory();

    /**
     * TASK
     */
    void createTask(Task task);

    List<Task> getAllTasks();

    void removeAllTasks();

    Task getTask(Integer taskId);

    void updateTask(Task task);

    void removeTask(Integer taskId);

    /**
     * EPIC
     */
    void createEpic(Epic epic);

    List<Epic> getAllEpics();

    void removeAllEpics();

    Epic getEpic(Integer epicId);

    void updateEpic(Epic epic);

    void removeEpic(Integer epicId);

    ArrayList<SubTask> getEpicSubTasks(Integer epicId);

    /**
     * SUBTASK
     */
    void createSubTask(SubTask subtask);

    List<SubTask> getAllSubTasks();

    void removeAllSubTasks();

    Task getSubTask(Integer subTaskId);

    void updateSubTask(SubTask subTask);

    void removeSubTask(Integer subTaskId);
}
