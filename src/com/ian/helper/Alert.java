package com.ian.helper;

import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class Alert {
    public interface AlertCallback {
        void onOkButton(javafx.scene.control.Alert alert);
        void onCancelButton(javafx.scene.control.Alert alert);
    }

    /**
     * to create confirmation alert
     *
     * @param title         alert title
     * @param headerText    alert header text
     * @param contentText   alert content text
     * @param alertCallback alert callback for button
     */
    public static void createAlertConfirmation(String title, String headerText, String contentText, AlertCallback alertCallback) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alertCallback.onOkButton(alert);
        } else {
            alertCallback.onCancelButton(alert);
        }
    }

    /**
     * to create warning alert
     *
     * @param primaryStage java fx primary stage
     * @param title        alert title
     * @param headerText   alert header text
     * @param contentText  alert content text
     */
    public static void alertWarning(Stage primaryStage, String title, String headerText, String contentText) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(primaryStage);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
    }
}
