package project.Utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.Controllers.Controllable;

public class StageUtil {

    private static double xOffset;
    private static double yOffset;

    public static Stage makeStage(Stage stage, Controllable controller, Parent parent, ActionEvent actionEvent, String name) {
        if (stage == null) {
            controller.getPane().setStyle("-fx-border-color: black;" + "-fx-border-width: 0.6;" + "-fx-border-radius: 3;");
            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle(name);
            stage.setResizable(false);
            if (actionEvent != null) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            }
            Scene scene = new Scene(parent);
            final Stage finalStage = stage;
            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = finalStage.getX() - event.getScreenX();
                    yOffset = finalStage.getY() - event.getScreenY();
                }
            });
            scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    finalStage.setX(event.getScreenX() + xOffset);
                    finalStage.setY(event.getScreenY() + yOffset);
                }
            });
            stage.setScene(scene);
        }
        return stage;
    }
}
