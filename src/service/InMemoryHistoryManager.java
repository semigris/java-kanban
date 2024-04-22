package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> taskHistory;

    public InMemoryHistoryManager(){
        this.taskHistory = new ArrayList<>();
    }
    @Override
    public void add(Task task) {
        if (taskHistory.size() == 10) {
            taskHistory.removeFirst();
        }
        taskHistory.add(task);
    }

    /**
     * Отображение последних 10 просмотренных пользователем задач:
     * getTask(Integer taskId), getSubTask(Integer subTaskId), getEpic(Integer epicId)
     */
    @Override
    public List<Task> getHistory() {
        return taskHistory;
    }
}
