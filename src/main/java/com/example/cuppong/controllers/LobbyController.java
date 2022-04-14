package com.example.cuppong.controllers;

import com.example.cuppong.objects.Cup;
import com.example.cuppong.util.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LobbyController {

    private final String FILE_USERS = "C:\\Users\\ryanl\\Documents\\GitHub\\CupPong\\CupPongServer\\data\\users.txt";

    @FXML
    private Label labelOnline;

    @FXML
    private Button btnCreateMatch;

    @FXML
    private ListView<String> lvOnlinePlayers;

    private ArrayList<String> onlinePlayers = new ArrayList<>();

    @FXML
    void btnCreateMatch_Click(ActionEvent event) {

        boolean canJoin = true;

        if (canJoin) {
            GV.getInstance().cups().clear();
            Cup c = new Cup(0, 0, 0,-1);

            int cups = 6;
            int root = findRoot(cups);
            final int finalRoot = findRoot(cups);
            final double brimRatio = 0.29;


            //Vector2F awayStart = new Vector2F();

            //generate away cups

            int index=0;
            while (root > 0) { //where root=current number of cups in this row
                float startX = (float) (GV.getInstance().width() - c.getWidth() * root) / 2; //get by taking anticipated width and subtracted from total width, then divide margin by 2
                float startY = (float) c.getHeight() + (float) ((finalRoot - root) * (c.getHeight() * brimRatio)); //add base Y,then the brims*which row iteration
                for (int i = 0; i < root; i++) {
                    Cup cup = new Cup(startY, 725, startX + c.getWidth() * i, index++);
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

                    Cup cup = new Cup(startY, 725, startX + c.getWidth() * i, index++);
                    cup.setWidth((int) (c.getWidth()));
                    cup.setHeight((int) (c.getHeight()));
                    GV.getInstance().addCup(cup);
                }
                root++;
            }

            ClientHandler.getInstance().sendMessage("reg,cups:6");
            StageManager.getInstance().showhide(StageManager.PLAY, StageManager.LOBBY);
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

    public void updateStats() {
        String s = getstats(GV.getInstance().info().getName());
        String[] stats = s.split(",");
        lvOnlinePlayers.getItems().clear();
        lvOnlinePlayers.getItems().add(0, "Wins: " + stats[0]);
        lvOnlinePlayers.getItems().add(1, "Losses: " + stats[1]);
        lvOnlinePlayers.getItems().add(2, "Cups Hit: " + stats[2]);
        lvOnlinePlayers.getItems().add(3, "Cups Missed: " + stats[3]);
        lvOnlinePlayers.getItems().add(4, "Balls Back: " + stats[4]);
    }

    public String getstats(String user) {
        String stats = "";
        try {
            List<String> fileContent = new ArrayList<>(
                    Files.readAllLines(file(FILE_USERS).toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).startsWith(user)) {
                    String line = fileContent.get(i);
                    int splice = line.indexOf(",", line.indexOf(",") + 1);
                    stats = line.substring(splice+1);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("There was an error reading stats for user '" + user + "'.");
        }
        return stats;
    }
    // need to create file if it doesnt exist
    public File file(String path) {
        return new File(path);
    }
}
