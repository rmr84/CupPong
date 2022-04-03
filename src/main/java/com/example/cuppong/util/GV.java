package com.example.cuppong.util;

import com.example.cuppong.objects.Cup;

import java.util.ArrayList;

public class GV { //gamevars
    private static volatile GV instance;
    private static Object lockobj = new Object();

    private boolean _myturn = true;
    private boolean _throwing = false;
    private boolean _wasreset = true;
    private final int _width = 1000;
    private final int _height = 725;
    private ArrayList<Cup> _cups = new ArrayList<>();

    private boolean _gamestarted = false;

    private GV() {
    }

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
    public void addCup(Cup cup) {
        _cups.add(cup);
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

    public boolean gameStarted() { return _gamestarted; }

    public void startGame() { _gamestarted = true; }

    public ArrayList<Cup> cups() { return _cups; }

    public void setCupCapacity(int capacity) { _cups = new ArrayList<>(capacity); }

    public void removeCup(Cup c) { _cups.remove(c); }

    public boolean throwing() {
        return _throwing;
    }

    public void setThrowing(boolean value) {
        _throwing=value;
    }
}
