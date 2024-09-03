package com.javaprojektni.tasker.genericClass;

import javafx.scene.control.Alert;

public class AlertUtils<T> {

    private final Alert.AlertType alertType;
    private String message;

    public AlertUtils(Alert.AlertType alertType) {
        this.alertType = alertType;
    }

    public AlertUtils(Alert.AlertType alertType, T message) {
        this.alertType = alertType;
        this.message = (String) message;
    }

    public void showAlert(T message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.toString());
        alert.setHeaderText(null);
        alert.setContentText((String) message);
        alert.showAndWait();
    }


}
