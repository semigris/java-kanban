import service.TaskManager;

import model.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

//        Task task1 = new Task("Task 1", "Description of Task 1", Status.NEW);
//        taskManager.createTask(task1);
//        int task1ID = task1.getId();
//        System.out.println("Task №1 added");
//        Task task2 = new Task("Task 2", "Description of Task 2", Status.NEW);
//        taskManager.createTask(task2);
//        int task2ID = task2.getId();
//        System.out.println("Task №2 added");
//
//        Epic epic1 = new Epic("Epic 1", "Description of Epic 1");
//        taskManager.createEpic(epic1);
//        int epic1ID = epic1.getId();
//        System.out.println("Empty Epic №1 added");
//        Epic epic2 = new Epic("Epic 2", "Description of Epic 2");
//        taskManager.createEpic(epic2);
//        int epic2ID = epic2.getId();
//        System.out.println("Empty Epic №2 added");
//
//        SubTask subTask1 = new SubTask("SubTask 1.1", "Description of SubTask 1.1", Status.NEW, epic1ID);
//        taskManager.createSubTask(subTask1);
//        int subTask1ID = subTask1.getId();
//        System.out.println("SubTask 1 to Epic 1 added");
//        SubTask subTask2 = new SubTask("SubTask 1.2", "Description of SubTask 1.2", Status.NEW, epic1ID);
//        taskManager.createSubTask(subTask2);
//        int subTask2ID = subTask2.getId();
//        System.out.println("SubTask 1 to Epic 2 added");
//        SubTask subTask3 = new SubTask("SubTask 2.1", "Description of SubTask 2.1", Status.DONE, epic2ID);
//        taskManager.createSubTask(subTask3);
//        int subTask3ID = subTask3.getId();
//        System.out.println("SubTask 2 to Epic 1 added");
//
//        System.out.println("\n-- TASKS LIST --");
//        for (Task key : taskManager.getAllTasks()) {
//            System.out.println(key);
//        }
//        System.out.println("-- EPIC LIST --");
//        for (Epic key : taskManager.getAllEpics()) {
//            System.out.println(key);
//        }
//        System.out.println("-- SUBTASKS LIST --");
//        for (SubTask key : taskManager.getAllSubTasks()) {
//            System.out.println(key);
//        }
//
//        System.out.println("\nTask by id:");
//        System.out.println(taskManager.getTask(task1ID));
//        System.out.println("Epic by id:");
//        System.out.println(taskManager.getEpic(epic1ID));
//        System.out.println("Subtask by id:");
//        System.out.println(taskManager.getSubTask(subTask1ID));
//        System.out.println("Epic subtasks:");
//        System.out.println(taskManager.getEpicSubTasks(epic1.getId()));
//        System.out.println("\n");
//
//        Task updateTask = new Task(task1ID, "NewTask 1", "NewDescription of Task 1", Status.NEW);
//        Task updateTask2 = new Task(task2ID, "NewTask 2", "NewDescription of Task 2", Status.IN_PROGRESS);
//        Epic updateEpic = new Epic(epic1ID, "NewEpic 1", "NewDescription of Epic 1");
//        Epic updateEpic2 = new Epic(epic2ID, "NewEpic 2", "NewDescription of Epic 2");
//        SubTask updateSubTask = new SubTask(subTask1ID, "NewSubTask 1.1", "NewDescription of SubTask 1.1", Status.DONE, epic1ID);
//        SubTask updateSubTask2 = new SubTask(subTask2ID, "NewSubTask 1.2", "NewDescription of SubTask 1.2", Status.DONE, epic1ID);
//        SubTask updateSubTask3 = new SubTask(subTask3ID, "NewSubTask 2.1", "NewDescription of SubTask 2.1", Status.IN_PROGRESS, epic2ID);
//
//        System.out.println("Update task");
//        taskManager.updateTask(updateTask);
//        taskManager.updateTask(updateTask2);
//        System.out.println("Update epic");
//        taskManager.updateEpic(updateEpic);
//        taskManager.updateEpic(updateEpic2);
//        System.out.println("Update subTask");
//        taskManager.updateSubTask(updateSubTask);
//        taskManager.updateSubTask(updateSubTask2);
//        taskManager.updateSubTask(updateSubTask3);
//
//        System.out.println("\n-- TASKS LIST --");
//        for (Task key : taskManager.getAllTasks()) {
//            System.out.println(key);
//        }
//        System.out.println("-- EPIC LIST --");
//        for (Epic key : taskManager.getAllEpics()) {
//            System.out.println(key);
//        }
//        System.out.println("-- SUBTASKS LIST --");
//        for (SubTask key : taskManager.getAllSubTasks()) {
//            System.out.println(key);
//        }
//
//        System.out.println("\nRemove one task (Task 1. id: 5)");
//        taskManager.removeTask(task1ID);
//        System.out.println("Remove one epic (Epic 2)");
//        taskManager.removeEpic(epic2ID);
//
//        System.out.println("\n-- TASKS LIST --");
//        for (Task key : taskManager.getAllTasks()) {
//            System.out.println(key);
//        }
//        System.out.println("-- EPIC LIST --");
//        for (Epic key : taskManager.getAllEpics()) {
//            System.out.println(key);
//        }
//        System.out.println("-- SUBTASKS LIST --");
//        for (SubTask key : taskManager.getAllSubTasks()) {
//            System.out.println(key);
//        }
    }
}
