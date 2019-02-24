package project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.Tasks.TaskManagerClient;

public class LogInController  implements Controllable {

    public Button btnCancel;
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private Label logInResponse;

    private TaskManagerClient client = null;
    private String logedUser;

    public String getLogedUser() {
        return logedUser;
    }

    TaskManagerClient getClient() {
        return client;
    }

    public void cancel_action() {
        Stage stage = (Stage) login.getScene().getWindow();
        logInResponse.setText("Please enter your account");
        password.clear();
        login.clear();
        stage.close();
    }

    public void logIn_action() {
        if (login.getText().isEmpty() || password.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Empty fields!");
            alert.setTitle(null);
            alert.setContentText("Please enter your Login and Password!");
            alert.showAndWait();
        } else {
            client = new TaskManagerClient(login.getText(), password.getText(), "enterAccount");
            if (client.getServerResponse().equals("INCORRECT")) logInResponse.setText("Wrong login or password");
            if (client.getServerResponse().equals("CORRECT")) {
                logedUser = login.getText();
                cancel_action();
            }
        }
    }

    @Override
    public AnchorPane getPane() {
        return pane;
    }
}
