package com.javaprojektni.tasker.controllers;

import com.javaprojektni.tasker.Database.Database;
import com.javaprojektni.tasker.Exceptions.TaskUpdateFailedException;
import com.javaprojektni.tasker.genericClass.AlertUtils;
import com.javaprojektni.tasker.genericClass.InfoUtils;
import com.javaprojektni.tasker.model.Activity;
import com.javaprojektni.tasker.model.LogWriter;
import com.javaprojektni.tasker.model.Task;
import com.javaprojektni.tasker.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.javaprojektni.tasker.controllers.LoginPageController.logedUser;
import static com.javaprojektni.tasker.mail.Mailer.sendEmail;
import static com.javaprojektni.tasker.model.LogWriter.getChanges;

public class EditTaskController {
    private static final Logger logger = LoggerFactory.getLogger(EditTaskController.class);
    public static Integer editTaskint;
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
    private final ObservableList<String> selectedItems = FXCollections.observableArrayList();
    private int userId;
    private final Task task = new Task();
    private Task oldTask = new Task();
    private ArrayList<User> invitee;

    @FXML
    private void initialize() throws SQLException, IOException {
        userLabel.setText(logedUser);
        System.out.println(logedUser);
        database.openConnection();
        Task task = database.getTaskById(editTaskint);
        oldTask = database.getTaskById(editTaskint);

        ArrayList<User> users = database.getAllUsers();
        ObservableList<String> namesAndSurnames = users.stream().sorted().map(user -> user.getName() + " " + user.getSurname()).collect(Collectors.toCollection(FXCollections::observableArrayList));
        invitees.setItems(namesAndSurnames);

        taskName.setText(task.getName());
        taskDescription.setText(task.getTaskBody());
        invitees.setValue(task.getInvitees());
        dueDate.setValue(task.getDueDate().toLocalDate());
        changImg();

        invitee = (ArrayList<User>) database.getAllUsers().stream().filter(user -> (user.getName() + " " + user.getSurname()).equals(invitees.getValue())).collect(Collectors.toList());

    }

    @FXML
    private void changImg() throws SQLException, IOException {

        Database database = new Database();
        database.openConnection();

        userId = database.getAllUsers().stream().filter(user -> (user.getMail()).equals(logedUser)).findFirst().map(User::getUserId).orElse(0);

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
    private void editTask() throws IOException {
        if (LoginPageController.isAdmin == Boolean.FALSE) {
            AlertUtils alertUtils = new AlertUtils(Alert.AlertType.ERROR);
            alertUtils.showAlert("niste admin");
        } else {
            task.setTaskBody(taskDescription.getText());
            task.setName(taskName.getText());
            task.setDueDate(Date.valueOf(dueDate.getValue()));
            task.setTaskOwnerId(userId);
            task.setDateCreated(Date.valueOf(LocalDate.now()));
            String invitee = invitees.getValue();
            User invited = database.getAllUsers().stream().filter(user -> (user.getName() + " " + user.getSurname()).equals(invitee)).findFirst().orElse(null);


            database.deleteTaskInvitees(editTaskint);

            database.updateTask(task);

            try {
                int userId = database.getAllUsers().stream().filter(user -> (user.getName() + " " + user.getSurname()).equals(invitees.getValue())).findFirst().map(User::getUserId).orElse(0);
                if (userId == 0) throw new TaskUpdateFailedException();
            } catch (TaskUpdateFailedException e) {
                logger.warn(e.toString());
            }
            if (!invitee.isEmpty()) {
                database.addTaskInvitee(editTaskint, invited.getUserId());
            }
            InfoUtils<String, String> infoUtils = new InfoUtils<>(Alert.AlertType.INFORMATION, "Information");
            infoUtils.showInfo("uspjesno", "stvoren novi task");
            Activity activity = new Activity(Date.valueOf(LocalDate.now()), logedUser, "Task updated: Changes - " + getChanges(oldTask, task));
            LogWriter.writeLog(activity);
            MenuBarController.showHomePage();
            sendEmail(invited.getMail(), "Task updated", "Task updated: Changes - " + getChanges(oldTask, task), null);
            logger.info("mail sent");
        }

    }

}
