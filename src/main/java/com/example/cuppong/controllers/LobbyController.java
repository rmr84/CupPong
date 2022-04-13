package com.example.cuppong.controllers;

import com.example.cuppong.objects.Cup;
import com.example.cuppong.util.ClientHandler;
import com.example.cuppong.util.GV;
import com.example.cuppong.util.StageManager;
import com.example.cuppong.util.Vector2F;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LobbyController {

    @FXML
    private Button btnCreateMatch;

    @FXML
    private ListView<?> lvOnlinePlayers;

    @FXML
    void btnCreateMatch_Click(ActionEvent event) {

        boolean canJoin = true;

        if (canJoin) {
            Cup c = new Cup(0, 0, 0);

            int cups = 6;
            int root = findRoot(cups);
            final int finalRoot = findRoot(cups);
            final double brimRatio = 0.29;


            //Vector2F awayStart = new Vector2F();

            //generate away cups
            while (root > 0) { //where root=current number of cups in this row
                float startX = (float) (GV.getInstance().width() - c.getWidth() * root) / 2; //get by taking anticipated width and subtracted from total width, then divide margin by 2
                float startY = (float) c.getHeight() + (float) ((finalRoot - root) * (c.getHeight() * brimRatio)); //add base Y,then the brims*which row iteration
                for (int i = 0; i < root; i++) {
                    Cup cup = new Cup(startY, 725, startX + c.getWidth() * i);
                    GV.getInstance().addCup(cup);
                }
                root--;
            }

            root = 1;
            final double ANGLE_MULTIPLIER = 1.3;
            c.setWidth((int) (c.getWidth() * ANGLE_MULTIPLIER));
            c.setHeight((int) (c.getHeight() * ANGLE_MULTIPLIER));

            while (root <= finalRoot) {
                float startX = (float) (GV.getInstance().width() - c.getWidth() * root) / 2;
                float startY = (GV.getInstance().height() - (c.getHeight() / 2)) - (float) ((finalRoot - root) * c.getHeight() * brimRatio);
                for (int i = 0; i < root; i++) {

                    Cup cup = new Cup(startY, 725, startX + c.getWidth() * i);
                    cup.setWidth((int) (c.getWidth()));
                    cup.setHeight((int) (c.getHeight()));
                    GV.getInstance().addCup(cup);
                }
                root++;
            }

            StageManager.getInstance().showhide(StageManager.PLAY, StageManager.LOBBY);
            GV.getInstance().startGame();
        } else {
            //is spectator
        }
    }

    private int findRoot(int sum) {
        int next = 1;
        while (sum != 0) {
            sum-=next;
            next++;
        }
        return next-1;
    }
}
