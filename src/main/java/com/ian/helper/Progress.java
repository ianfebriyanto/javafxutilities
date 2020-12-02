package com.ian.helper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Progress {
    public interface StageInterface {
        void event(Stage dialog);
    }

    public interface TaskInterface {
        void startTask(ProgressBar progressBar, Label title, Stage dialog);
    }

    public static abstract class StageAbstract implements StageInterface {
        @Override
        public void event(Stage stage) {

        }
    }

    /**
     * create progressbar
     *
     * @param label         progress label
     * @param primaryStage  primary stage of scene
     * @param taskInterface interface on start task
     * @param btnCallback   on close progress
     */
    public void create(String label, Stage primaryStage, TaskInterface taskInterface, StageAbstract btnCallback) {
        Group root = new Group();
        Scene scene = new Scene(root, 200, 100, Color.WHITE);

        BorderPane mainPane = new BorderPane();
        root.getChildren().add(mainPane);

        Label title = new Label(label);
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setMinWidth(480);

        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(5));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().addAll(title, progressBar);
        mainPane.setTop(vBox);

        Stage dialog = new Stage();
        dialog.initOwner(primaryStage);
        dialog.setScene(scene);

        btnCallback.event(dialog);

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setMinWidth(498);
        dialog.setMinHeight(60);
        dialog.setMaxHeight(100);
        dialog.setMaximized(false);
        dialog.setResizable(false);
        dialog.show();

        taskInterface.startTask(progressBar, title, dialog);
    }
}
