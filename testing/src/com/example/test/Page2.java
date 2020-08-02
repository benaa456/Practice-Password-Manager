package com.example.test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class Page2 {
    static TableView<TableContents> table;
    static ArrayList<Integer> indexToDelete;
    static Stage window;
    public static void showScene(Stage primaryStage){
        window = primaryStage;
        window.setAlwaysOnTop(true);
        indexToDelete = new ArrayList<>();

        //make the table rows and columns
        TableColumn<TableContents, String> websiteColumn = new TableColumn<>("website");
        websiteColumn.setMinWidth(200);
        websiteColumn.setCellValueFactory(new PropertyValueFactory<>("website"));

        //username column
        TableColumn<TableContents, String> usernameColumn = new TableColumn<>("username");
        usernameColumn.setMinWidth(100);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        //password column
        TableColumn<TableContents, Integer> passwordColumn = new TableColumn<>("password");
        passwordColumn.setMinWidth(100);
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        //email column
        TableColumn<TableContents, String> emaildColumn = new TableColumn<>("email");
        emaildColumn.setMinWidth(100);
        emaildColumn.setCellValueFactory(new PropertyValueFactory<>("email"));


        WindowProperties dimentions = new WindowProperties();
        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox(10);
        borderPane.setLeft(hBox);
        window.setTitle("Password Manager");

        table = new TableView<>();
        table.setItems(getTableContentsTXT());
        table.getColumns().addAll(websiteColumn, usernameColumn, passwordColumn, emaildColumn);
        borderPane.setCenter(table);

        Button button = new Button("<==");
        button.setOnAction(event -> Page1.showScene(window));

        //bottom
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            Alerts.addAlert();
        });
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(event -> {
            removeFromTable();
            window.setAlwaysOnTop(true);
        });
        HBox bottomHBox = new HBox(10);
        bottomHBox.getChildren().addAll(addButton, removeButton);
        borderPane.setBottom(bottomHBox);

        //Left
        Button website_copy = new Button("Copy URL");
        website_copy.setOnAction(event -> copyURL());
        Button username_copy = new Button("Copy Username");
        username_copy.setOnAction(event -> copyUsername());
        Button password_copy = new Button("Copy password");
        password_copy.setOnAction(event -> copyPassword());
        Button email_copy = new Button("Copy email");
        email_copy.setOnAction(event -> copyEmail());
        VBox rightVBox = new VBox(10);
        rightVBox.getChildren().addAll(website_copy, username_copy, password_copy, email_copy);
        borderPane.setRight(rightVBox);

        //table.getSelectionModel().setCellSelectionEnabled(true);

        hBox.getChildren().addAll(button);
        Scene scene = new Scene(borderPane, dimentions.getWidth_window(), dimentions.getHeight_window());
        window.setScene(scene);
        window.show();


    }
    public static  ObservableList<TableContents> getTableContentsTXT(){
        String line;
        ArrayList<String> list = new ArrayList<>();
        try{
            BufferedReader bufferedReader= new BufferedReader(new FileReader("src\\com\\example\\test\\storage.txt"));
            while((line = bufferedReader.readLine() )!= null){
                list.add(line);
            }
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        ObservableList<TableContents> tableinfo = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++){
            //split the line read into website name password email
            String[] split = list.get(i).split(",");
            addTOTableTXT(tableinfo, split[0], split[1], split[2], split[3]);
        }
        return tableinfo;
    }
    public static ObservableList<TableContents> addTOTableTXT(ObservableList<TableContents> tableinfo, String url, String name, String password, String email){
        //TODO decrypt the password and add it to the table
        password = AES.decrypt(password);
        tableinfo.add(new TableContents(url, name, password, email));
        return tableinfo;
    }




    public static void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static void removeFromTable(){
        //TODO confirm if you want to delete an entry
        window.setAlwaysOnTop(false);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> confirm = alert.showAndWait();
        if(confirm.get() == ButtonType.OK){
            ObservableList<TableContents> selected , allItems;
            allItems = table.getItems();
            selected = table.getSelectionModel().getSelectedItems();

            indexToDelete.add(table.getSelectionModel().getSelectedIndex());
            selected.forEach(allItems::remove);
        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
        }

    }

    public static void copyURL(){
        TablePosition position = table.getSelectionModel().getSelectedCells().get(0);
        int row = position.getRow();
        TableContents item = table.getItems().get(row);
        copyToClipboard(item.getWebsite());
    }
    public static void copyUsername(){
        TablePosition position = table.getSelectionModel().getSelectedCells().get(0);
        int row = position.getRow();
        TableContents item = table.getItems().get(row);
        copyToClipboard(item.getUsername());
    }
    public static void copyPassword(){
        TablePosition position = table.getSelectionModel().getSelectedCells().get(0);
        int row = position.getRow();
        TableContents item = table.getItems().get(row);
        copyToClipboard(item.getPassword());
    }
    public static void copyEmail(){
        TablePosition position = table.getSelectionModel().getSelectedCells().get(0);
        int row = position.getRow();
        TableContents item = table.getItems().get(row);
        copyToClipboard(item.getEmail());
    }



}
