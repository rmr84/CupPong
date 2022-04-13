package com.example.cuppong.util;

import com.example.cuppong.gamestates.GameStateManager;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameStage {
    public GameStage() {

    }

    public Stage create() {
        Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.web("#121212"));
        stage.setScene(scene);

        Canvas canvas = new Canvas(1000,850);
        root.getChildren().add(canvas);

        canvas.setOnMouseMoved(mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            MouseHandler.getInstance().update(x,y);
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            MouseHandler.getInstance().update(x,y);
        });

        canvas.setOnMousePressed(mouseEvent -> {
            if (GV.getInstance().isMyTurn()) {
                if (mouseEvent.getButton()== MouseButton.SECONDARY) {
                    GV.getInstance().setThrowing(true);
                    MouseHandler.getInstance().setMouseDown(false);
                    GV.getInstance().setReset(true);
                } else {
                    MouseHandler.getInstance().setMouseDown(true);
                    if (GV.getInstance().throwing()) {
                        GV.getInstance().setReset(false);
                        GV.getInstance().setMidShot(true);
                        MouseHandler.getInstance().setStartX();
                        MouseHandler.getInstance().setStartY();
                    }
                }
            }
        });

        canvas.setOnMouseReleased(mouseEvent -> {
            if (GV.getInstance().isMyTurn() && GV.getInstance().getMidShot()) {
                MouseHandler.getInstance().setMouseDown(false);
                GV.getInstance().setMidShot(false);
                if (!GV.getInstance().wasReset()) {
                    GV.getInstance().setThrowing(false);
                    GV.getInstance().setLaunch(true);
                }
            }
            //GV.getInstance().setReset(false);
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        GameStateManager manager = new GameStateManager();
        KeyHandler k = new KeyHandler();

        manager.add(GameStateManager.STATE_PLAY);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gc.clearRect(0, 0, 1000, 1000);
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
