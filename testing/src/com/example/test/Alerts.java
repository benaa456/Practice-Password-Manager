package com.example.test;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Alerts {
    static TextField urlText, userText, passwordText, emailText;
    public static void addAlert(){
        Stage window = new Stage();
        WindowProperties dimentions = new WindowProperties();
        VBox vBox = new VBox();

        Label labelURL = new Label("Website");
        urlText = new TextField();
        urlText.setPromptText("Website");

        Label labelUserName = new Label("Username");
        userText = new TextField();
        userText.setPromptText("Username");

        Label labelPassword = new Label("Password");
        passwordText = new TextField();
        passwordText.setPromptText("Password");

        Label labelEmail = new Label("Email");
        emailText = new TextField();
        emailText.setPromptText("Email");

        HBox hBox = new HBox();

        Button add = new Button("add");
        add.setOnAction(e -> {
            appendToFile();
        });
        hBox.getChildren().addAll(add);
        hBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(labelURL, urlText, labelUserName, userText, labelPassword, passwordText, labelEmail, emailText, hBox);


        Scene scene = new Scene(vBox, dimentions.getWidth_alert(),dimentions.getHeight_alert());
        window.setScene(scene);
        window.setAlwaysOnTop(true);
        window.showAndWait();
    }
    private static void appendToFile() {
        //TODO ecrypt the password then add to string
        String infoToSave = urlText.getText() + "," + userText.getText() + "," + AES.encrypt(passwordText.getText()) + "," + emailText.getText();
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src\\com\\example\\test\\storage.txt", true));
            bufferedWriter.append(infoToSave + "\n");
            bufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        showChangesOnTable();
        Page2.getTableContentsTXT();
    }

    private static void showChangesOnTable() {
        TableContents tableContents = new TableContents();
        tableContents.setWebsite((urlText.getText()));
        tableContents.setUsername(userText.getText());
        tableContents.setPassword(passwordText.getText());
        tableContents.setEmail(emailText.getText());
        Page2.table.getItems().add(tableContents);
        urlText.clear();
        userText.clear();
        passwordText.clear();
        emailText.clear();
    }
}
