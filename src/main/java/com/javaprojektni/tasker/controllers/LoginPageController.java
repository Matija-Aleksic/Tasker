package com.javaprojektni.tasker.controllers;

import com.javaprojektni.tasker.Database.Database;
import com.javaprojektni.tasker.genericClass.AlertUtils;
import com.javaprojektni.tasker.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class LoginPageController {
    public static Boolean isAdmin = Boolean.FALSE;
    private ArrayList<User> users = new ArrayList<>();
    @FXML
    private TextField usernameTextfield;
    @FXML
    private TextField passwordTextfield;
    @FXML
    private Button loginBuutton;

    @FXML
    protected void initialize() {
        Popup suggestionsPopup = new Popup();

        VBox content = new VBox();
        suggestionsPopup.getContent().add(content);

        usernameTextfield.setOnKeyReleased(event -> {
            String enteredText = usernameTextfield.getText();
            if (enteredText.isEmpty()) {
                suggestionsPopup.hide();
            } else {
                content.getChildren().clear();
                try {
                    for (String suggestion : filterSuggestions(enteredText)) {
                        Label label = new Label(suggestion);
                        label.setOnMouseClicked(mouseEvent -> {
                            usernameTextfield.setText(label.getText());
                            suggestionsPopup.hide();
                        });
                        content.getChildren().add(label);
                    }
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }

                if (!suggestionsPopup.isShowing()) {
                    Window window = usernameTextfield.getScene().getWindow();
                    double x = window.getX() + usernameTextfield.localToScene(0, 0).getX()+10;
                    double y = window.getY() + usernameTextfield.localToScene(0, usernameTextfield.getHeight()).getY()+30;
                    suggestionsPopup.show(window, x, y);
                }
            }
        });
    }
    public static void setAdmin(Boolean value) {
        isAdmin = value;
    }


    private String[] getSuggestions(String enteredText) throws SQLException, IOException {
        Database database = new Database();
        database.openConnection();
        ArrayList<String> emailList = new ArrayList<>();
        users = database.getAllUsers();
        for (User user : users) {
            emailList.add(user.getMail());
        }
        database.closeConnection();
        return emailList.toArray(new String[0]);
    }

    private String[] filterSuggestions(String enteredText) throws SQLException, IOException {
        String[] allSuggestions = getSuggestions(enteredText);
        ArrayList<String> filteredSuggestionsList = new ArrayList<>();

        for (String suggestion : allSuggestions) {
            if (suggestion.toLowerCase().contains(enteredText.toLowerCase())) {
                filteredSuggestionsList.add(suggestion);
            }
        }

        return filteredSuggestionsList.toArray(new String[0]);
    }

    @FXML
    private void login() {
        try {
            Database database = new Database();
            database.openConnection();

            String loginpass = passwordTextfield.getText();
            String username = usernameTextfield.getText();
            if (database.checkPassword(username,loginpass)) {
                MenuBarController.showHomePage();
                isAdmin = Boolean.TRUE;
            } else {
                passwordTextfield.setText(null);
                System.out.println("wrong pass");
                AlertUtils<String> alertUtils = new AlertUtils<>(Alert.AlertType.WARNING);
                alertUtils.showAlert("Wrong password");

            }
        } catch (SQLException | IOException e) {
            // Handle exceptions
            e.printStackTrace();
          //  Alert.error("An error occurred during login");
        }
    }


}
