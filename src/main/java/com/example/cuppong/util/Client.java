package com.example.cuppong.util;


import java.io.ObjectInputStream;

public class Client extends Thread {
    private ObjectInputStream in;

    public Client(ObjectInputStream in) {
        this.in=in;
    }

    @Override
    public void run()
    {

    }
}
