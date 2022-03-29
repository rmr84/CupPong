package com.example.cuppong.controllers;

import com.example.cuppong.objects.Cup;
import com.example.cuppong.util.GV;
import com.example.cuppong.util.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class LobbyController {

    Cup cup = new Cup();


    private boolean creating=false;

    @FXML
    private Button btnCreateMatch;

    @FXML
    private Button btnJoinMatch;

    @FXML
    private Label labelStatus;

    @FXML
    private TextField textFieldJoinKey;

    @FXML
    private TextField textFieldToJoin;

    @FXML
    void btnCreateMatch_Click(ActionEvent event) {
        creating=!creating;
        if(creating) {
            cup.getPos().set(400, 700, 400);
            GV.getInstance().addCup(cup);
            btnJoinMatch.setDisable(true);
            int port = 7000;
            textFieldJoinKey.setText(String.valueOf(port));
            labelStatus.setText("Connected");
            btnCreateMatch.setText("Cancel");

            StageManager.getInstance().showhide(StageManager.PLAY, StageManager.LOBBY);
        } else {
            btnJoinMatch.setDisable(false);
            textFieldJoinKey.clear();
            labelStatus.setText("Not Connected");
            btnCreateMatch.setText("Create");
        }
    }

    @FXML
    void btnJoinMatch_Click(ActionEvent event) {
        btnCreateMatch.setDisable(true);
    }
}
