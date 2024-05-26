package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static class Node {
        Task task;
        Node next;
        Node prev;

        Node(Node prev, Task task, Node next) {
            this.task = task;
            this.next = next;
            this.prev = prev;
        }
    }

    HashMap<Integer, Node> taskHistory = new HashMap<>();
    Node first;
    Node last;

    @Override
    public void add(Task task) {
        Node node = taskHistory.get(task.getId());
        if (node != null) {
            removeNode(node);
        }
        taskHistory.put(task.getId(), linkLast(task));
    }

    /**
     * Отображение последних просмотренных пользователем задач без дублирования:
     * getTask(Integer taskId), getSubTask(Integer subTaskId), getEpic(Integer epicId)
     */
    @Override
    public List<Task> getHistory() {
        ArrayList<Task> tasksList = new ArrayList<>();
        Node currentNode = first;
        while(currentNode != null){
            tasksList.add(currentNode.task);
            currentNode = currentNode.next;
        }
        return tasksList;
    }

    @Override
    public void remove(int id) {
        Node node = taskHistory.remove(id);
        if (node != null) {
            removeNode(node);
        }
    }

    Node linkLast(Task task) {
        final Node lastNode = last;
        Node newNode = new Node(lastNode, task, null);
        last = newNode;
        if(lastNode == null)
            first = newNode;
        else
            lastNode.next = newNode;
        return newNode;
    }

    void removeNode(Node node) {
        final Node nextNode = node.next;
        final Node prevNode = node.prev;

        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
        }

        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        taskHistory.remove(node.task.getId());
    }
}
