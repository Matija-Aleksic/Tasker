package com.javaprojektni.tasker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }


    public static void main(String[] args) {

        launch();

    }


    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/javaprojektni/tasker/HomePage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

}