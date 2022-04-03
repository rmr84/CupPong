package com.example.cuppong.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class ClientHandler {
    private static volatile ClientHandler instance;
    private static Object lockobj = new Object();
    private static volatile Socket client = null;
    private PrintWriter out;
    private BufferedReader in;

    private ClientHandler()
    {

    }

    public static ClientHandler getInstance() {
        ClientHandler result = instance;
        if (result==null) {
            synchronized (lockobj) {
                result = instance;
                if (result==null) {
                    instance = result = new ClientHandler();
                }
            }
        }
        return result;
    }

    public void connect(int port) {
        if (client == null) {
            try {
                client = new Socket("127.0.0.1", port);
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                Runnable r = () -> {
                    try {
                        while (client.isConnected()) {
                            String inputLine = in.readLine();
                            if (in.readLine() != null) {
                                Message m = new Message(inputLine);

                                switch (m.getType()) {
                                    case "reg":
                                        System.out.println("Player was registered");
                                        break;
                                }

                                System.out.println("Got a mesage");
                                // out.println(inputLine);
                            }

                            in.close();
                            out.close();
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                };
                Thread clientThread = new Thread(r);
                clientThread.start();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (client != null && client.isConnected()) {
                    System.out.println("Connected successfully.");
                    out.write("reg,cups:6");
                }
            }
        }
    }

    public void disconnect() {
        if (client != null && client.isConnected()) {
            try {
                client.close();
                System.out.println("Disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage() {

    }

    private void receiveMessage() {

    }
}
