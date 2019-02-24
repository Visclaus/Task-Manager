package project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.Utils.DateUtil;
import project.Tasks.Task;

public class AddController implements Controllable {

    @FXML
    private AnchorPane pane;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField taskTitle;
    @FXML
    private TextField taskDescription;
    @FXML
    private TextField taskDate;
    @FXML
    private TextField taskContacts;

    private Task task;

    public void cancel_action() {
        resetTask();
        taskTitle.clear();
        taskDescription.clear();
        taskDate.clear();
        taskContacts.clear();
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void add_action() {
        task = new Task();
        task.setTaskName(taskTitle.getText());
        task.setTaskDescription(taskDescription.getText());
        if (DateUtil.parse(taskDate.getText()) == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong data");
            alert.setHeaderText(null);
            alert.setContentText("Your data input doesn't match any patterns! Try enter \"dd.mm.yyyy hh:mm\"");
            alert.showAndWait();
        }
        else {
            task.setTaskDate(taskDate.getText());
            task.setTaskContacts(taskContacts.getText());
            taskTitle.clear();
            taskDescription.clear();
            taskDate.clear();
            taskContacts.clear();
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public AnchorPane getPane() {
        return pane;
    }

    public Task getTask() {
        return task;
    }

    public void resetTask() {
        task = null;
    }
}
