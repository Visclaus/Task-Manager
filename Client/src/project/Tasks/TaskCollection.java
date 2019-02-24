package project.Tasks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.Tasks.Task;

public class TaskCollection {
    private ObservableList<Task> collection;

    public TaskCollection() {
        collection = FXCollections.observableArrayList();
    }

    public void addTask(Task task) {
        collection.add(task);
    }

    public void deleteTask(Task task) {
        collection.remove(task);
    }

    public ObservableList<Task> getCollection() {
        return collection;
    }
}

