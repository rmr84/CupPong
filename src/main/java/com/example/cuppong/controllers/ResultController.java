package com.example.cuppong.controllers;

import com.example.cuppong.util.ClientHandler;
import com.example.cuppong.util.GV;
import com.example.cuppong.util.StageManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class ResultController {

    @FXML
    private Button MenuButton;

    @FXML
    private Button PlayAgainButton;

    @FXML
    private Label WinOrLoseLabel;

    @FXML
    private Rectangle WinOrLoseRect;

    @FXML
    private ImageView elGato1;

    @FXML
    private ImageView elGato2;

    @FXML
    private ImageView elPerro;

    @FXML
    private ListView<String> lvMatchInfo;

    public void update() {
        updateDisplay(GV.getInstance().won());
        updateMatchInfo();
    }

    private void updateDisplay(boolean win) {
        if (win) {
            WinOrLoseLabel.setText("Win!");
            WinOrLoseRect.setFill(Color.web("#0fe800"));
            elGato1.setVisible(true);
            elGato2.setVisible(true);
            elPerro.setVisible(true);
        } else {
            WinOrLoseLabel.setText("Lose :(");
            WinOrLoseRect.setFill(Color.web("#ff0000"));
            elGato1.setVisible(false);
            elGato2.setVisible(false);
            elPerro.setVisible(false);
        }
    }

    private void updateMatchInfo() {/*
        lvMatchInfo.getItems().clear();
        for (int i = 0; i < GV.getInstance().getTurns().size(); i++) {
            lvMatchInfo.getItems().add(lvMatchInfo.getItems().size(), GV.getInstance().getTurns().get(i).toString());
            for (int j = 0; j < GV.getInstance().getTurns().get(i).getThrowInfo().size(); j++) {
                lvMatchInfo.getItems().add(lvMatchInfo.getItems().size(), GV.getInstance().getTurns().get(i).getThrowInfo().get(j).toString());
            }
        }*/
    }

    public void click_MenuButton(ActionEvent actionEvent) {
        ClientHandler.getInstance().sendMessage("leave");
        Platform.exit();
        System.exit(0);
    }

    public void click_PlayAgain(ActionEvent actionEvent) {
        GV.getInstance().resetToDefaults();
        StageManager.getInstance().showhide(StageManager.LOBBY, StageManager.RESULT);
    }
}
