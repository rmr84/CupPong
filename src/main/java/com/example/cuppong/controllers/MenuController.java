package com.example.cuppong.controllers;

import com.example.cuppong.util.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MenuController {

    @FXML
    public void onBtnPlayClick(ActionEvent actionEvent) {
        StageManager.getInstance().showhide(StageManager.LOBBY, StageManager.MAINMENU);
    }

    @FXML
    public void onBtnHowToPlayClick(ActionEvent actionEvent) {

    }

    @FXML
    public void onBtnExitClick(ActionEvent actionEvent) {
        System.exit(0);
    }
}