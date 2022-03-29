package com.example.cuppong.controllers;

import com.example.cuppong.CupPongMain;
import com.example.cuppong.util.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class HowToPlayController {
    ColorAdjust colorAdjust = new ColorAdjust();


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
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        box.setBackground(new Background(myBI));

        top.setTextFill(Color.RED);
        top.setStyle("-fx-font-weight: bold");
        top.setFont(Font.font("Arial", 20));
        a.setFont((Font.font("Arial", 20)));
        a.setTextFill(Color.RED);
        a.setStyle("-fx-font-weight: bold");
        b.setTextFill(Color.RED);
        b.setStyle("-fx-font-weight: bold");
        b.setFont(Font.font("Arial", 20));
        c.setTextFill(Color.RED);
        c.setStyle("-fx-font-weight: bold");
        c.setFont(Font.font("Arial", 20));
        d.setTextFill(Color.RED);
        d.setStyle("-fx-font-weight: bold");
        d.setFont(Font.font("Arial", 20));
        e.setTextFill(Color.RED);
        e.setStyle("-fx-font-weight: bold");
        e.setFont(Font.font("Arial", 20));
        f.setTextFill(Color.RED);
        f.setStyle("-fx-font-weight: bold");
        f.setFont(Font.font("Arial", 20));
        g.setTextFill(Color.RED);
        g.setStyle("-fx-font-weight: bold");
        g.setFont(Font.font("Arial", 20));

    }

    @FXML
    public void onBackClicked(ActionEvent actionEvent) {
        StageManager.getInstance().showhide(StageManager.MAINMENU, StageManager.HOWTOPLAY);
    }
    }


