package com.example.test;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Page1 {
    public static void showScene(Stage window){
        WindowProperties dimentions = new WindowProperties();
        HBox hBox = new HBox();
        Label label = new Label("page 1");

        Button button = new Button("button");

        hBox.getChildren().addAll(button, label);

        button.setOnAction(event -> Page2.showScene(window));

        Scene scene = new Scene(hBox, dimentions.getWidth_window(), dimentions.getHeight_window());
        window.setScene(scene);
        window.setAlwaysOnTop(true);
        window.show();
    }


}
