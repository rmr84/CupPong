package com.example.cuppong.controllers;

import com.example.cuppong.CupPongMain;
import com.example.cuppong.util.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class HowToPlayController {

    @FXML
    Label top;
    @FXML
    Label a;
    @FXML
    Label b;
    @FXML
    Label c;
    @FXML
    Label d;
    @FXML
    Label e;
    @FXML
    Label f;
    @FXML
    Label g;

    @FXML
    VBox box;
    @FXML
    public void initialize() {
        BackgroundImage myBI= new BackgroundImage(new Image(CupPongMain.class.getResourceAsStream("images/bilitski.jpg")),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        box.setBackground(new Background(myBI));

        a.setTextFill(Color.WHITE);
        b.setTextFill(Color.WHITE);
    }



    @FXML
    public void onBackClicked(ActionEvent actionEvent) {
        StageManager.getInstance().showhide(StageManager.MAINMENU, StageManager.HOWTOPLAY);
    }

    }


