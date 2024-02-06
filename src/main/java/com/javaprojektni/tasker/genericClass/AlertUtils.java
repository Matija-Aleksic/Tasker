package com.javaprojektni.tasker.genericClass;

import javafx.scene.control.Alert;

public class AlertUtils<T> {

    private final Alert.AlertType alertType;

    public AlertUtils(Alert.AlertType alertType) {
        this.alertType = alertType;
    }

    public void showAlert(String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.toString()); // Set title to alert type
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
