package com.javaprojektni.tasker.controllers;

import com.javaprojektni.tasker.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuBarController {
    @FXML
    public static void showHomePage() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/javaprojektni/tasker/HomePage.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            HelloApplication.getMainStage().setTitle("Welcome");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    public void showHomePage(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/javaprojektni/tasker/HomePage.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            HelloApplication.getMainStage().setTitle("Welcome");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void showEditPage() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/javaprojektni/tasker/EditTask.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            HelloApplication.getMainStage().setTitle("Welcome");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showChangesPage() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/javaprojektni/tasker/ChangesPage.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            HelloApplication.getMainStage().setTitle("Welcome");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void showNewTask(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/javaprojektni/tasker/NewTask.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            HelloApplication.getMainStage().setTitle("Welcome");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @FXML
    public void showEditPage(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/javaprojektni/tasker/EditTask.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            HelloApplication.getMainStage().setTitle("Welcome");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void showLoginPage(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/javaprojektni/tasker/LoginPage.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            HelloApplication.getMainStage().setTitle("Welcome");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
            LoginPageController.setAdmin(Boolean.FALSE);
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
}
