package com.javaprojektni.tasker.controllers;


import com.javaprojektni.tasker.Database.Database;
import com.javaprojektni.tasker.model.Task;
import com.javaprojektni.tasker.model.TaskBuilder;
import com.javaprojektni.tasker.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.stream.Collectors;

import static com.javaprojektni.tasker.controllers.LoginPageController.logedUser;

public class NewTaskController {

    Database database = new Database();
    @FXML
    private TextField taskName;
    @FXML
    private TextArea taskDescription;
    @FXML
    private ChoiceBox<String> invitees;
    @FXML
    private Label userLabel;
    @FXML
    private ImageView userPicture;
    @FXML
    private DatePicker dueDate;
    private ObservableList<String> selectedItems = FXCollections.observableArrayList();
    private int userId;
    private int taskId;

    @FXML
    private void initialize() throws SQLException, IOException {
        userLabel.setText(logedUser);
        System.out.println(logedUser);
        database.openConnection();


        ArrayList<User> users = database.getAllUsers();
        ObservableList<String> namesAndSurnames = users.stream().sorted().map(user -> user.getName() + " " + user.getSurname()).collect(Collectors.toCollection(FXCollections::observableArrayList));
        invitees.setItems(namesAndSurnames);
        changImg();

    }

    @FXML
    private void changImg() throws SQLException, IOException {
        Database database = new Database();
        database.openConnection();

        userId = database.getAllUsers().stream().filter(user -> (user.getMail()).equals(logedUser)).findFirst().map(User::getUserId).orElse(0);
        taskId = database.getAllTasks().size();
        String imagePath = "/images/" + userId + ".jpg";
        URL imageUrl = getClass().getResource(imagePath);
        Image image = null;
        try {
            assert imageUrl != null;
            image = new Image(imageUrl.toExternalForm());
        } catch (Exception e) {
            System.out.println("nije pronadena slika");
        }
        userPicture.setImage(image);
    }

    @FXML
    private void createTask() {
        TaskBuilder taskBuilder = new TaskBuilder();
        Task newtask = taskBuilder
                .setName(taskName.getText())
                .setTaskBody(taskDescription.getText())
                .setDateCreated(Date.valueOf(LocalDate.now()))
                .setTaskOwnerId(userId)
                .setDueDate(Date.valueOf(dueDate.getValue()))
                .setFinalizedStatus(Boolean.FALSE)
                .setId(taskId + 1)
                .createTask();
        database.createTask(newtask);
        ArrayList<User> invited = new ArrayList<>();
        invited = (ArrayList<User>) database.getAllUsers().stream()
                .filter(user -> (user.getName() + " " + user.getSurname()).equals(invitees.getValue()))
                .collect(Collectors.toList());

        for (int i = 0; i < invited.size(); i++) {
            database.addTaskInvitee(taskId + 1, invited.get(i).getUserId());
        }

    }
}
