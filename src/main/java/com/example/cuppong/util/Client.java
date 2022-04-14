package com.example.cuppong.util;


import com.example.cuppong.objects.Cup;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

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
                    case "gameover" -> {
                        String win = m.getString("winner");
                        if (win.equals(GV.getInstance().info().getName())) {
                            ClientHandler.getInstance().sendMessage("win");
                            GV.getInstance().setwon(true);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    StageManager.getInstance().showhide(StageManager.RESULT, StageManager.PLAY);
                                }
                            });

                        } else {
                            ClientHandler.getInstance().sendMessage("lose");
                            GV.getInstance().setwon(false);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    StageManager.getInstance().showhide(StageManager.RESULT, StageManager.PLAY);
                                }
                            });

                        }
                    }
                    case "reg" -> {
                        GV.getInstance().setMyTurn(m.getBool("turn"));
                        GV.getInstance().startGame();
                    }
                    case "throw" -> {
                        //GV.getInstance().addThrowInfo(m.getBool("made"),0);
                    }
                    case "init" -> {
                        if (GV.getInstance().gameStarted()) {
                            System.out.println("[Warning] Game has already been started.");
                        }
                        GV.getInstance().startGame();
                    }
                    case "turn" -> {
                        String misc = m.getString("misc");
                        String name = m.getString("name");
                        if (name.equals(GV.getInstance().info().getName())) {
                            switch (misc) {
                                case "ballsback" -> GV.getInstance().setMyTurn(true);
                                case "null" -> GV.getInstance().setMyTurn(false);
                            }
                        } else {
                            switch (misc) {
                                case "ballsback" -> GV.getInstance().setMyTurn(false);
                                case "null" -> GV.getInstance().setMyTurn(true);
                            }
                        }
                        //GV.getInstance().addTurn();
                    }
                    case "sys" -> {
                        String systemMessage = m.getString("msg");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("System");
                                alert.setContentText(systemMessage);
                            }
                        });
                    }
                    case "cup" -> {
                        String op = m.getString("name");

                        System.out.println("[CUP] " + op + ", " + m.getInt("id"));
                        if(!GV.getInstance().info().getName().equals(op)) {
                            int id = m.getInt("id");
                            System.out.println("me");

                            for (int i= 0 ; i < GV.getInstance().cups().size(); i++) {
                                Cup c = GV.getInstance().cups().get(i);
                                if(id==c.id()) {
                                    GV.getInstance().removeCup(c);
                                }
                            }
                        }
                    }
                    case "join" -> {GV.getInstance().addPlayer(m.getString("name")); System.out.println("got join");}
                    case "leave" -> GV.getInstance().removePlayer(m.getString("name"));
                    case "register" -> {
                        boolean registered = m.getBool("status");
                        if (registered) {
                            GV.getInstance().initUser(m.getString("name"));
                            ClientHandler.getInstance().sendMessage("join");
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    StageManager.getInstance().showhide(StageManager.LOBBY, StageManager.MAINMENU);
                                }
                            });
                        }
                    }
                    case "login" -> {
                        boolean loggedin = m.getBool("status");
                        if (loggedin) {
                            GV.getInstance().initUser(m.getString("name"));
                            ClientHandler.getInstance().sendMessage("join");
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    StageManager.getInstance().showhide(StageManager.LOBBY, StageManager.MAINMENU);
                                }
                            });
                        }
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + m.getType());
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Error reading message [CNF].");
            } catch (IOException e) {
                System.out.println("Error reading message [IO].");
            }
        }
    }
}
