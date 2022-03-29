package com.example.cuppong.util;

import com.example.cuppong.gamestates.GameStateManager;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameStage {
    public GameStage() {

    }

    public Stage create() {
        Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.web("#121212"));
        stage.setScene(scene);

        Canvas canvas = new Canvas(1000,700);
        root.getChildren().add(canvas);

        canvas.setOnMouseMoved(mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            MouseHandler.getInstance().update(x,y);
        });

        canvas.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getButton()== MouseButton.SECONDARY) {

            }
            MouseHandler.getInstance().setMouseDown(true);
        });

        canvas.setOnMouseReleased(mouseEvent -> {
            MouseHandler.getInstance().setMouseDown(false);
            GV.getInstance().setReset(false);
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        GameStateManager manager = new GameStateManager();
        KeyHandler k = new KeyHandler();

        manager.add(GameStateManager.STATE_PLAY);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gc.clearRect(0, 0, 1280, 720);
                manager.update();
                manager.input(k);
                manager.render(gc);
            }
        };

        stage.setOnShowing(windowEvent -> {
            timer.start();
        });

        stage.setOnCloseRequest(windowEvent -> {
            timer.stop();
        });

        return stage;
    }
}
