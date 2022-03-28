package com.example.cuppong.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseEvent;

public class PlayController {

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private Canvas gameCanvas;

    @FXML
    void gameCanvas_Click(MouseEvent event) {

    }

    @FXML
    void gameCanvas_MouseDragged(MouseEvent event) {

    }

    @FXML
    void gameCanvas_MouseEntered(MouseEvent event) {

    }

    @FXML
    void gameCanvas_MouseExited(MouseEvent event) {

    }

    @FXML
    void gameCanvas_MouseMoved(MouseEvent event) {

    }

    @FXML
    void gameCanvas_MousePressed(MouseEvent event) {

    }

    @FXML
    void gameCanvas_MouseReleased(MouseEvent event) {

    }

    @FXML
    public void initialize() {
        System.out.println("hello");
        GraphicsContext context = gameCanvas.getGraphicsContext2D();
    }

}
