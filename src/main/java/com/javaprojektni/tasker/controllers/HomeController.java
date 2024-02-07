package com.javaprojektni.tasker.controllers;

import com.javaprojektni.tasker.Database.Database;
import com.javaprojektni.tasker.genericClass.AlertUtils;
import com.javaprojektni.tasker.model.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.javaprojektni.tasker.controllers.LoginPageController.isAdmin;
import static com.javaprojektni.tasker.controllers.LoginPageController.logedUser;

public class HomeController {
    public Button deleteButton;
    @FXML
    TableColumn<Task, String> ownerColumn;
    @FXML
    TableColumn<Task, String> nameColumn;
    @FXML
    TableColumn<Task, String> bodyColumn;
    @FXML
    TableColumn<Task, String> statusColumn;
    @FXML
    TableColumn<Task, String> madeDateColumn;
    @FXML
    TableColumn<Task, String> inviteesColumn;
    @FXML
    TableColumn<Task, String> dueDateColumn;
    @FXML
    private ImageView imageView;
    @FXML
    private ComboBox<String> by;
    @FXML
    private ComboBox<String> invitees;
    @FXML
    private DatePicker madeOn;
    @FXML
    private DatePicker dueDate;
    @FXML
    private CheckBox showFinished;
    @FXML
    private TableView<Task> taskTableView;
    @FXML
    private Button searchbutton;
    @FXML
    private Button refreshButton;
    private ArrayList<Task> tasks;

    public HomeController() {
    }

    @FXML
    protected void initialize() throws SQLException, IOException {


        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/0.png")).toExternalForm());
        imageView.setImage(image);

        Database database = new Database();
        database.openConnection();
        ArrayList<User> users = database.getAllUsers();
        tasks = database.getAllTasks();

        ObservableList<String> namesAndSurnames = users.stream().sorted().map(user -> user.getName() + " " + user.getSurname()).collect(Collectors.toCollection(FXCollections::observableArrayList));
        by.setItems(namesAndSurnames);
        invitees.setItems(namesAndSurnames);

        ArrayList<Task> tasks = database.getAllTasks();
        ownerColumn.setCellValueFactory(cellData -> {
            try {
                return new ReadOnlyStringWrapper(cellData.getValue().getTaskOwner());
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        bodyColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTaskBody()));
        statusColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().isFinalizedStatus()));
        madeDateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDateCreated().toString()));
        inviteesColumn.setCellValueFactory(cellData -> {
            try {
                return new ReadOnlyStringWrapper(cellData.getValue().getInvitees());
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        dueDateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDueDate().toString()));

        ObservableList<Task> taskList = FXCollections.observableArrayList(tasks);
        taskTableView.setItems(taskList);

    }

    @FXML
    private void editTask() {

        EditTaskController.editTaskint = taskTableView.getSelectionModel().getSelectedItem().getId();
        MenuBarController menuBarController = new MenuBarController();
        menuBarController.showEditPage();

    }


    @FXML
    private void changImg() throws SQLException, IOException {
        Database database = new Database();
        database.openConnection();
        String nameSurname = by.getValue();

        int userId = database.getAllUsers().stream().filter(user -> (user.getName() + " " + user.getSurname()).equals(nameSurname)).findFirst().map(User::getUserId).orElse(0);

        String imagePath = "/images/" + userId + ".jpg";
        URL imageUrl = getClass().getResource(imagePath);
        Image image = null;
        try {
            assert imageUrl != null;
            image = new Image(imageUrl.toExternalForm());
        } catch (Exception e) {
            System.out.println("nije pronadena slika");
        }
        imageView.setImage(image);
    }

    @FXML
    private void refresh() throws SQLException, IOException {
        Database database = new Database();
        database.openConnection();
        tasks = database.getAllTasks();
        ObservableList<Task> taskList = FXCollections.observableArrayList(tasks);
        taskTableView.setItems(taskList);
        by.setValue(null);
        invitees.setValue(null);
        dueDate.setValue(null);
        madeOn.setValue(null);
        showFinished.setSelected(false);
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/0.png")).toExternalForm());
        imageView.setImage(image);
    }

    @FXML
    private void search() {

        Optional<String> optionalBy = Optional.ofNullable(by.getValue());
        Optional<String> optionalInvitee = Optional.ofNullable(invitees.getValue());
        Optional<LocalDate> optionalDateMadeon = Optional.ofNullable(madeOn.getValue());
        Optional<LocalDate> optionalDueDate = Optional.ofNullable(dueDate.getValue());
        Optional<Boolean> optionalBoolean = Optional.of(showFinished.isSelected());

        ArrayList<Task> filteredTasks = tasks.stream().filter(task -> optionalBy.map(byValue -> {
            try {
                return task.getTaskOwner().equals(byValue);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }).orElse(true)).filter(task -> optionalInvitee.map(inviteeValue -> {
            try {
                return task.getInvitees().contains(inviteeValue);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }).orElse(true)).filter(task -> optionalDateMadeon.map(dateValue -> task.getDateCreated().toLocalDate().isEqual(dateValue)).orElse(true)).filter(task -> optionalDueDate.map(dateValue -> task.getDueDate().toLocalDate().isEqual(dateValue)).orElse(true)).filter(task -> optionalBoolean.get() == Boolean.parseBoolean(task.isFinalizedStatus())).collect(Collectors.toCollection(ArrayList::new));
        ObservableList<Task> taskList = FXCollections.observableArrayList(filteredTasks);
        taskTableView.setItems(taskList);
    }


    public void deleteTask(ActionEvent actionEvent) throws SQLException, IOException {

        if (!isAdmin) {
            AlertUtils<String> alertUtils = new AlertUtils<>(Alert.AlertType.WARNING);
            alertUtils.showAlert("Nemate Prava");
            return;
        }

        boolean confirmed = ConfirmationDialog.showConfirmationDialog("Confirmation", "Izbrisati zadatak?");
        if (confirmed) {
            Database database = new Database();
            int taskId = taskTableView.getSelectionModel().getSelectedItem().getId();
            database.deleteTaskInvitees(taskId);
            database.deleteTask(taskId);
            Activity activity = new Activity(Date.valueOf(LocalDate.now()), logedUser, "deleted task named " + taskTableView.getSelectionModel().getSelectedItem().getName());
            LogWriter.writeLog(activity);
            refresh();

        }

    }
}


