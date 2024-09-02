package com.javaprojektni.tasker;

import com.javaprojektni.tasker.Database.Database;
import com.javaprojektni.tasker.Thhreads.ReadChanges;
import com.javaprojektni.tasker.Thhreads.TaskRefresh;
import com.javaprojektni.tasker.Thhreads.WriteChanges;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {
    public static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }


    public static void main(String[] args) {

        launch();

    }

    @Override
    public void init() {

        ReadChanges readChanges = new ReadChanges(1);
        WriteChanges writeChanges = new WriteChanges(2);
        TaskRefresh taskRefresh = new TaskRefresh(3);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            Platform.runLater(readChanges);
            Platform.runLater(writeChanges);
            Platform.runLater(taskRefresh);
        }, 0, 5, TimeUnit.SECONDS);
    }


    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/javaprojektni/tasker/LoginPage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


}