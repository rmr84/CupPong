package com.example.cuppong.util;

import java.util.ArrayList;

public class Turn {
    private int _id=0;

    private ArrayList<ThrowInfo> throwInfo;

    public Turn (int id) {
        _id = id;
    }

    public String getTitle() {
        return "Turn #" + _id;
    }

    public void addThrow(boolean made, int ballsback) {
        throwInfo.add(new ThrowInfo(made, ballsback));
    }

    public ArrayList<ThrowInfo> getThrowInfo () {
        return throwInfo;
    }
}
