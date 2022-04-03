package com.example.cuppong.util;

import java.io.*;
import java.net.*;

public class ClientHandler {
    private static volatile ClientHandler instance;
    private static Object lockobj = new Object();
    private static volatile Socket client = null;
    private ObjectOutputStream out;
    private ObjectInputStream in;

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
        try {
            InetAddress addr = InetAddress.getByName("localhost");
            client = new Socket(addr, port);
            out = new ObjectOutputStream(client.getOutputStream());
            out.flush();
            in = new ObjectInputStream(client.getInputStream());

            Client c = new Client(in);
            c.start();

            sendMessage("reg,cups:6");

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void disconnect() {
        if (client.isConnected()) {
            try {
                client.close();
                System.out.println("Disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String input) {
        try {
            //Message m = new Message(input);
            out.writeObject(input);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error when sending message.\n");
            e.printStackTrace();
        }
    }
}
