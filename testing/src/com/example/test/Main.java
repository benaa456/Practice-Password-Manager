package com.example.test;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;


public class Main extends Application {
    Stage window;
    public static boolean loginSuccessful = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        LoginPage.loginScene();
    }
//    when closing the stage so that you can change what happens when closing a window
    //        primaryStage.setOnCloseRequest();
    @Override
    public void stop(){
        if (loginSuccessful) {
            ArrayList<Integer> indexList = Page2.indexToDelete;
            ArrayList<String> compareList = new ArrayList<>();
            compareList = readFromFileAndDelete(compareList);
            for (int i = 0; i < indexList.size(); i++){
                compareList.remove(indexList.get(i));
            }
            appendToFile(compareList);
        }
    }

    private ArrayList<String> readFromFileAndDelete(ArrayList<String> list) {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src\\com\\example\\test\\storage.txt"));
            while ((line = reader.readLine()) != null){
                list.add(line);
            }
            reader.close();
            File file = new File("src\\com\\example\\test\\storage.txt");
            file.delete();
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }


    private void appendToFile(ArrayList<String> writeList) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src\\com\\example\\test\\storage.txt", true));
            for (int i = 0;i < writeList.size(); i++){
                bufferedWriter.append(writeList.get(i) + "\n");
            }

            bufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
