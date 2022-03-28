package com.example.cuppong.util;

public class MouseHandler {

    private static volatile MouseHandler instance;
    private static Object lockobj = new Object();
    private double _x;
    private double _y;

    private MouseHandler() {
        _x=0;
        _y=0;
    }

    public static MouseHandler getInstance() {
        MouseHandler result = instance;
        if (result==null) {
            synchronized (lockobj) {
                result = instance;
                if (result==null) {
                    instance = result = new MouseHandler();
                }
            }
        }
        return result;
    }

    public void update(double x, double y) {
        _x=x;
        _y=y;
    }

    public double getX() {
        return _x;
    }

    public double getY() {
        return _y;
    }
}
