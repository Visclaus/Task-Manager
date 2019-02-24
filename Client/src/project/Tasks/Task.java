package project.Tasks;

import javafx.beans.property.SimpleStringProperty;
import project.Utils.DateUtil;

import java.util.Date;

public class Task {
    private SimpleStringProperty taskName;
    private SimpleStringProperty taskDescription;
    private SimpleStringProperty taskContacts;
    private SimpleStringProperty taskDate;
    private boolean ready = true;

    public Task(SimpleStringProperty taskName, SimpleStringProperty taskDescription, Date taskDate, SimpleStringProperty taskContacts) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskContacts = taskContacts;
        this.taskDate = new SimpleStringProperty(DateUtil.format(taskDate));
    }

    public Task() {
        taskName = new SimpleStringProperty("");
        taskDescription = new SimpleStringProperty("");
        taskContacts = new SimpleStringProperty("");
        this.taskDate = new SimpleStringProperty("");
    }

    public String getTaskName() {
        return taskName.get();
    }

    public String getTaskDescription() {
        return taskDescription.get();
    }

    public String getTaskDate() {
        return taskDate.get();
    }

    public String getTaskContacts() {
        return taskContacts.get();
    }

    public void setTaskName(String taskName) {
        this.taskName.set(taskName);
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription.set(taskDescription);
    }

    public void setTaskDate(String taskDate) {
        this.taskDate.set(taskDate);
    }

    public void setTaskContacts(String taskContacts) {
        this.taskContacts.set(taskContacts);
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public SimpleStringProperty taskNameProperty() {
        return taskName;
    }

    public SimpleStringProperty taskDescriptionProperty() {
        return taskDescription;
    }

    public SimpleStringProperty taskDateProperty() {
        return taskDate;
    }

    public SimpleStringProperty taskContactsProperty() {
        return taskContacts;
    }

    @Override
    public String toString() {
        return taskName.getValue() + "<taskfile>" +
                taskDescription.getValue() + "<taskfile>" +
                taskDate.getValue() + "<taskfile>" +
                taskContacts.getValue();
    }

    public String myToString() {
        return "(" + taskName.getValue() + ")" + " should be done, because it's already " + taskDate.getValue();
    }
}

