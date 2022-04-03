package com.example.cuppong.util;

import java.io.BufferedReader;

public class Client extends Thread {
    private BufferedReader in;

    public Client(BufferedReader in) {
        this.in=in;
    }

    @Override
    public void run() {

    }
}
