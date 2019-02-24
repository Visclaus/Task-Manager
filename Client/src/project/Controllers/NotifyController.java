package project.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import project.Tasks.Task;
import project.Utils.StageUtil;

import java.io.IOException;

public class NotifyController  implements Controllable {

    @FXML
    private Button cancelButton;
    @FXML
    private Label messageLabel;
    @FXML
    private AnchorPane pane;

    private Task task;
    private Stage editStage;
    private Parent editParent;
    private EditController editController;

    public void setTask(Task task) {
        this.task = task;
    }

    public void setMessageLabel() {
        if (task != null) {
            messageLabel.setText(task.myToString());
        }
    }

    public void edit_action(ActionEvent actionEvent) {
        close_action();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../FXML/edit.fxml"));
            if (editParent == null & editController == null) {
                editParent = fxmlLoader.load();
                editController = fxmlLoader.getController();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        editStage = StageUtil.makeStage(editStage, editController, editParent, actionEvent, "Edit task");
        editController.setTask(task);
        editStage.show();
    }

    public void close_action() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public AnchorPane getPane() {
        return pane;
    }
}
