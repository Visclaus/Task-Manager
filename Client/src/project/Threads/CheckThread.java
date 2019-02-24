package project.Threads;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import project.Controllers.NotifyController;
import project.Tasks.Task;
import project.Tasks.TaskCollection;
import project.Utils.StageUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckThread extends Thread {

    private TaskCollection col;
    private boolean keepRunning;

    public CheckThread(TaskCollection col) {
        this.col = col;
        keepRunning = true;
    }

    public void setKeepRunning(boolean keepRunning) {
        this.keepRunning = keepRunning;
    }

    @Override
    public void run() {
        while (keepRunning) {
            for (Task task : col.getCollection()) {
                if (LocalDateTime.now().isAfter(LocalDateTime.parse(task.getTaskDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))) & task.isReady()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Parent notifyParent = null;
                            Stage notifyStage = null;
                            NotifyController notifyController = null;
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            try {
                                fxmlLoader.setLocation(getClass().getResource("../FXML/notify.fxml"));
                                notifyParent = fxmlLoader.load();
                                notifyController = fxmlLoader.getController();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            notifyStage = StageUtil.makeStage(notifyStage, notifyController, notifyParent, null, "Notification");
                            assert notifyController != null;
                            notifyController.setTask(task);
                            notifyController.setMessageLabel();
                            notifyStage.show();
                            task.setReady(false);
                        }
                    });
                }
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
