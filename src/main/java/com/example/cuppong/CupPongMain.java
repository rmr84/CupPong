package com.example.cuppong;


import com.example.cuppong.util.GameStage;
import com.example.cuppong.util.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class CupPongMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        StageManager.getInstance().add(loadStage("hello-view.fxml", "Cup Pong", 800, 500));
        StageManager.getInstance().add(loadStage("howtoplay-view.fxml", "How To Play", 900, 600));
        StageManager.getInstance().add(loadStage("lobby-view.fxml", "Cup Pong", 900, 600));

        Stage playStage = new GameStage().create();
        StageManager.getInstance().add(playStage);

        //StageManager.getInstance().add(loadStage("play-view.fxml", "Cup Pong", 1280, 720));


        StageManager.getInstance().add(loadStage("result-view.fxml", "Results", 500, 500));
        StageManager.getInstance().show(StageManager.MAINMENU);
    }

    public static void main(String[] args) {
        launch();
    }

    public Stage loadStage(String filename, String title, int w, int h) throws IOException {

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(CupPongMain.class.getResource(filename));

        Scene scene = new Scene(fxmlLoader.load(), w,h);

        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);
        return stage;
    }
}