package com.javaprojektni.tasker.controllers;

import com.javaprojektni.tasker.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuBarController {
    @FXML
    public static void showHomePage() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HomePage.fxml"));
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
    public static void showLoginPage() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginPage.fxml"));
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
