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

    public boolean connect(int port) {
        boolean con = false;
        try {
            InetAddress addr = InetAddress.getByName("localhost");
            client = new Socket(addr, port);
            out = new ObjectOutputStream(client.getOutputStream());
            out.flush();
            in = new ObjectInputStream(client.getInputStream());

            Client c = new Client(in);
            c.start();

            sendMessage("reg,cups:6");
            con=true;

        } catch (ConnectException e) {
            System.out.println("[Connection Refused] Server is not running.");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return con;
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

    public boolean isConnected() {
        return client.isConnected();
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

    public void trylogin(String user, String pass) {
        sendMessage("login,user:" + user + ",pass:" + pass);
    }

    public void tryregister(String user, String pass) {
        sendMessage("register,user:" + user + ",pass:" + pass);
    }
}
