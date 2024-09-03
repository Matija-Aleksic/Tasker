package com.javaprojektni.tasker.genericClass;


import javafx.scene.control.Alert;

public class InfoUtils<T, U> {

    private final Alert.AlertType alertType;
    private final String title;

    public InfoUtils(Alert.AlertType alertType, String title) {
        this.alertType = alertType;
        this.title = title;
    }

    public void showInfo(T message, U header) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header != null ? header.toString() : null);
        alert.setContentText(message.toString());
        alert.showAndWait();
    }
}
