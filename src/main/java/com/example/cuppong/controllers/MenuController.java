package com.example.cuppong.controllers;

import com.example.cuppong.CupPongMain;
import com.example.cuppong.util.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class MenuController {
    @FXML
    VBox box;
    @FXML
    public void initialize(){
        BackgroundImage myBI= new BackgroundImage(new Image(CupPongMain.class.getResourceAsStream("images/background.jpg")),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        box.setBackground(new Background(myBI));
    }

    @FXML
    public void onBtnPlayClick(ActionEvent actionEvent) {
        StageManager.getInstance().showhide(StageManager.LOBBY, StageManager.MAINMENU);
    }

    @FXML
    public void onBtnHowToPlayClick(ActionEvent actionEvent) {
        StageManager.getInstance().showhide(StageManager.HOWTOPLAY, StageManager.MAINMENU);

    }

    @FXML
    public void onBtnExitClick(ActionEvent actionEvent) {
        System.exit(0);
    }
}