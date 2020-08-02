package com.example.test;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class LoginPage {
    public static TextField username_text, password_text;
    public static Stage window;

    public static void loginScene(){
        window = new Stage();
        window.setTitle("Login");
        WindowProperties windowProperties = new WindowProperties();
        VBox layout = new VBox();

        Label username_label = new Label();
        username_label.setText("Username");
        username_text = new TextField();
        username_text.setPromptText("Username");

        Label password_label = new Label();
        password_label.setText("Password");
        password_text = new TextField();
        password_text.setPromptText("Password");
        password_text.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                window.setAlwaysOnTop(false);
                checkLoginCredentials();
            }
        });

        Button login_button = new Button("Login");
        login_button.setOnAction(e -> {
            window.setAlwaysOnTop(false);
            checkLoginCredentials();
        });

        login_button.setCenterShape(true);

        HBox layout_buttons = new HBox();
        layout_buttons.getChildren().addAll(login_button);
        layout_buttons.setAlignment(Pos.CENTER);


        layout.getChildren().addAll(username_label,username_text,password_label,password_text, layout_buttons);

        Scene scene = new Scene(layout, windowProperties.getWidth_alert(),windowProperties.getHeight_alert());
        window.setScene(scene);
        window.setAlwaysOnTop(true);
        window.showAndWait();

    }

    private static void checkLoginCredentials() {
        String username = username_text.getText();
        String password = password_text.getText();
        //encrypt username and password to check what is stored in the file
        username = AES.encrypt(username);
        password = AES.encrypt(password);

        if (checklogintxt(username, password)){
            window.close();
            Main.loginSuccessful = true;
            Page2.showScene(window);
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Username or Password was incorrect");
            //System.out.println(AES.decrypt("PUQ95VNSDM43MlP5vhhIJg=="));
            alert.show();
            username_text.clear();
            password_text.clear();
        }


    }

    private static boolean checklogintxt(String username, String password) {
        String line;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src\\com\\example\\test\\logins.txt"));
            while((line = reader.readLine()) != null){
                arrayList.add(line);
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        for (int i = 0; i < arrayList.size(); i++){
            String[] split = arrayList.get(i).split(",");
            if(split[0].equals(username) && split[1].equals(password)){
                return true;
            }
        }
        return false;
    }
}
