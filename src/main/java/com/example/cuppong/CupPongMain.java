package com.example.cuppong;


import com.example.cuppong.util.Client;
import com.example.cuppong.util.ClientHandler;
import com.example.cuppong.util.GameStage;
import com.example.cuppong.util.StageManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class CupPongMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        if (ClientHandler.getInstance().connect(4444)) {

            /*
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                ClientHandler.getInstance().disconnect();
            }));*/

            Stage menu = loadStage("hello-view.fxml", "Cup Pong", 800, 500);
            menu.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    kill();
                }
            });
            StageManager.getInstance().add(menu);

            Stage howtoplay = loadStage("howtoplay-view.fxml", "How To Play", 1280, 720);
            howtoplay.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    kill();
                }
            });
            StageManager.getInstance().add(howtoplay);


            Stage lobby = loadStage("lobby-view.fxml", "Cup Pong", 408, 600);
            lobby.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    kill();
                }
            });
            StageManager.getInstance().add(lobby);

            Stage play = new GameStage().create();
            play.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    kill();
                }
            });
            StageManager.getInstance().add(play);

            Stage results = loadStage("result-view.fxml", "Results", 500, 500);
            results.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    kill();
                }
            });
            StageManager.getInstance().add(results);

            StageManager.getInstance().show(StageManager.MAINMENU);
        } else {

            System.exit(0);
            //
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Could not connect to server.");

        }
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

    public void kill() {
        Platform.exit();
        System.exit(0);
    }
}