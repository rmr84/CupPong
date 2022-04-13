package com.example.cuppong.controllers;

import com.example.cuppong.CupPongMain;
import com.example.cuppong.util.Client;
import com.example.cuppong.util.ClientHandler;
import com.example.cuppong.util.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class MenuController {

    @FXML
    VBox box;

    @FXML
    Label welcomeText;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnHowToPlay;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnRegister;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    public void initialize(){
        BackgroundImage myBI= new BackgroundImage(new Image(CupPongMain.class.getResourceAsStream("images/background.jpg")),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
//then you set to your node
        box.setBackground(new Background(myBI));

        DropShadow shadow = new DropShadow();
        welcomeText.setEffect(shadow);
    }
    @FXML
    public void onBtnPlayClick(ActionEvent actionEvent) {
        //StageManager.getInstance().showhide(StageManager.LOBBY, StageManager.MAINMENU);
        String user = usernameField.getText();
        String pass = passwordField.getText();
        ClientHandler.getInstance().trylogin(user, pass);
    }

    @FXML
    public void onBtnHowToPlayClick(ActionEvent actionEvent) {
        StageManager.getInstance().showhide(StageManager.HOWTOPLAY, StageManager.MAINMENU);
    }

    @FXML
    public void onBtnExitClick(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    void onBtnRegisterClick(ActionEvent event) {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        ClientHandler.getInstance().tryregister(user, pass);
    }
}