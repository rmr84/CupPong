package com.example.cuppong.util;


import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Client extends Thread {
    private ObjectInputStream in;

    public Client(ObjectInputStream in) {
        this.in=in;
    }

    @Override
    public void run()
    {
        while (true) {
            try {
                String str = (String)in.readObject();
                Message m = new Message(str);
                switch (m.getType()) {
                    case "reg":
                        GV.getInstance().setId(m.getInt("id"));
                        GV.getInstance().setMyTurn(m.getBool("turn"));
                        GV.getInstance().startGame();
                        break;
                    case "init":
                        if(GV.getInstance().gameStarted()) {
                            System.out.println("[Warning] Game has already been started.");
                        }
                        GV.getInstance().startGame();
                        break;
                    case "turn":
                        if (m.getInt("id")==GV.getInstance().getId()) {
                            GV.getInstance().setMyTurn(m.getBool("turn"));
                        } else {
                            GV.getInstance().setMyTurn(!m.getBool("turn"));
                        }
                        break;
                    case "end":
                        if (GV.getInstance().getId() == m.getInt("id")) {
                            //win
                        } else {
                            //lose
                        }
                        break;
                    case "sys":
                        String systemMessage = m.getString("msg");

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("System");
                        alert.setContentText(systemMessage);
                        break;
                    case "register":
                        boolean registered = m.getBool("status");
                        if (registered) {
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    StageManager.getInstance().showhide(StageManager.LOBBY, StageManager.MAINMENU);
                                }
                            });
                        }
                        break;
                    case "login":
                        boolean loggedin = m.getBool("status");
                        if (loggedin) {
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    StageManager.getInstance().showhide(StageManager.LOBBY, StageManager.MAINMENU);
                                }
                            });
                        }
                        break;

                }
            } catch (ClassNotFoundException e) {
                System.out.println("Error reading message [CNF].");
            } catch (IOException e) {
                System.out.println("Error reading message [IO].");
            }
        }
    }
}
