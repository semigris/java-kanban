package model;

public class SubTask extends Task {
    private int epicId;

    public SubTask(Integer id, String name, String description, Status status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "SubTask: " + "id = " + super.getId() +
                ", epicId = " + epicId +
                ", name = " + super.getName() +
                ", description = " + super.getDescription() +
                ", status = " + super.getStatus();
    }
}
