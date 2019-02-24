package project.Controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import project.Tasks.*;
import project.Threads.CheckThread;
import project.Utils.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Controller {

    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button editBtn;
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, SimpleStringProperty> taskName;
    @FXML
    private TableColumn<Task, SimpleStringProperty> taskDescription;
    @FXML
    private TableColumn<Task, SimpleStringProperty> taskDate;
    @FXML
    private TableColumn<Task, SimpleStringProperty> taskContacts;

    private TaskCollection col;
    private File savedFile;
    private CheckThread checkTaskTime;
    private String logedUser = "";

    private Parent editParent;
    private Parent addParent;
    private Parent logInParent;

    private EditController editController;
    private AddController addController;
    private LogInController logInController;

    private Stage editStage;
    private Stage addStage;
    private Stage logInStage;

    @FXML
    private void initialize() {
        FXMLLoader editLoader = new FXMLLoader();
        FXMLLoader addLoader = new FXMLLoader();
        FXMLLoader logInLoader = new FXMLLoader();
        col = new TaskCollection();
        checkTaskTime = new CheckThread(col);
        checkTaskTime.start();
        taskName.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskDescription.setCellValueFactory(new PropertyValueFactory<>("taskDescription"));
        taskDate.setCellValueFactory(new PropertyValueFactory<>("taskDate"));
        taskContacts.setCellValueFactory(new PropertyValueFactory<>("taskContacts"));
        taskTable.setItems(col.getCollection());
        try {
            editLoader.setLocation(getClass().getResource("../FXML/edit.fxml"));
            addLoader.setLocation(getClass().getResource("../FXML/add.fxml"));
            logInLoader.setLocation(getClass().getResource("../FXML/logIn.fxml"));
            editParent = editLoader.load();
            addParent = addLoader.load();
            logInParent = logInLoader.load();
            editController = editLoader.getController();
            addController = addLoader.getController();
            logInController = logInLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAction(ActionEvent actionEvent) {
        addStage = StageUtil.makeStage(addStage, addController, addParent, actionEvent, "New task");
        addStage.showAndWait();
        if (addController.getTask() != null) {
            col.addTask(addController.getTask());
            addController.resetTask();
        }
    }

    public void editAction(ActionEvent actionEvent) {
        editStage = StageUtil.makeStage(editStage, editController, editParent, actionEvent, "Edit task");
        editController.setTask(taskTable.getSelectionModel().getSelectedItem());
        editStage.show();
    }

    public void enterAccAction() {
        logInStage = StageUtil.makeStage(logInStage, logInController, logInParent, null, "Enter your account");
        logInStage.showAndWait();
        TaskManagerClient client = logInController.getClient();
        if (client != null) {
            if (client.getTasks() != null) {
                col.getCollection().clear();
                for (int i = 0; i < client.getTasks().length; i++) {
                    String[] params = client.getTasks()[i].split("<taskfile>");
                    col.addTask(new Task(new SimpleStringProperty(params[0]), new SimpleStringProperty(params[1]), DateUtil.parse(params[2]), new SimpleStringProperty(params[3])));
                }
            }
        }
        logedUser = logInController.getLogedUser();
    }

    public  void synchronizeTasksAction(){
        String[] tasksToSend = new String[col.getCollection().size()];
        if(!logedUser.equals("")){
            for (int i = 0; i < col.getCollection().size(); i++) {
                tasksToSend[i] = col.getCollection().get(i).toString();
            }
            new TaskManagerClient(logedUser,"synchronizeTasks", tasksToSend);
        }
    }

    public void deleteAction() {
        col.deleteTask(taskTable.getSelectionModel().getSelectedItem());
    }

    public void openAction() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Document");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        savedFile = fileChooser.showOpenDialog(addBtn.getScene().getWindow());
        if (savedFile != null) {
            col.getCollection().clear();
            FileReader fr = new FileReader(savedFile);
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] params = line.split("<taskfile>");
                col.addTask(new Task(new SimpleStringProperty(params[0]), new SimpleStringProperty(params[1]), DateUtil.parse(params[2]), new SimpleStringProperty(params[3])));
            }
            fr.close();
        }
    }

    public void saveAsAction() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Document");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        savedFile = fileChooser.showSaveDialog(addBtn.getScene().getWindow());
        if (savedFile != null) {
            PrintWriter writer = new PrintWriter(savedFile);
            for (Task task : col.getCollection()) {
                writer.println(task.toString());
            }
            writer.close();
        }
    }

    public void saveAction() throws IOException {
        if (savedFile != null) {
            PrintWriter writer = new PrintWriter(savedFile);
            for (Task task : col.getCollection()) {
                writer.println(task.toString());
            }
            writer.close();
        }
    }

    public void exitAction() {
        checkTaskTime.setKeepRunning(false);
        Platform.exit();
    }

    public void mouseClicked() {
        checkTableItems();
    }

    private void checkTableItems() {
        if (taskTable.getSelectionModel().getSelectedIndex() == -1 || taskTable.getItems().isEmpty()) {
            editBtn.setDisable(true);
            deleteBtn.setDisable(true);
        } else {
            editBtn.setDisable(false);
            deleteBtn.setDisable(false);
        }
    }
}