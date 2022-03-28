package com.example.cuppong.util;

public class GV { //gamevars
    private static volatile GV instance;
    private static Object lockobj = new Object();

    private boolean _myturn = true;
    private boolean _wasreset = false;
    private final int _width = 1000;
    private final int _height = 720;

    private GV() { }

    public static GV getInstance() {
        GV result = instance;
        if(instance==null) {
            synchronized (lockobj) {
                result = instance;
                if (result==null) {
                    instance = result = new GV();
                }
            }
        }
        return result;
    }

    public boolean isMyTurn() {
        return _myturn;
    }

    public void setMyTurn(boolean value) {
        _myturn = value;
    }

    public boolean wasReset() {
        return _wasreset;
    }

    public void setReset(boolean value) {
        _wasreset = value;
    }

    public int width() {
        return _width;
    }

    public int height() {
        return _height;
    }
}
