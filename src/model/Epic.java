package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subTasksIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
    }

    public Epic(Integer id, String name, String description) {
        super(id, name, description, Status.NEW);
    }

    public List<Integer> getSubTasksIds() {
        return subTasksIds;
    }

    public void setSubTasks(List<Integer> subTasksIds) {
        this.subTasksIds = subTasksIds;
    }

    @Override
    public String toString() {
        return "Epic: " + "id = " + super.getId() +
                ", subTasksIds = " + subTasksIds +
                ", name = " + super.getName() +
                ", description = " + super.getDescription() +
                ", status = " + super.getStatus();
    }
}
