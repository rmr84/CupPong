package com.example.cuppong.util;

import com.example.cuppong.objects.Cup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class GV { //gamevars
    private static volatile GV instance;
    private static Object lockobj = new Object();

    private boolean _myturn = true;
    private boolean _throwing = false;
    private boolean _wasreset = true;
    private final int _width = 1000;
    private final int _height = 725;
    private ArrayList<Cup> _cups = new ArrayList<>();
    private int _id=-1;
    private boolean _midshot = false;

    private boolean _gamestarted = false;
    private boolean _launch = false;

    private UserInfo _info;

    private boolean _won=false;

    private LinkedList<Turn> turns = new LinkedList<>();

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

    private ArrayList<String> _onlinePlayers = new ArrayList<>(5);

    private Turn currentTurn;

    public void startGame() {
        if (_gamestarted)
            return;
        _gamestarted = true;
        if (_myturn) {
            _throwing=true;
        }

        currentTurn = new Turn(0);

        System.out.println("GS: " + _gamestarted + ", MT: " + _myturn + ", T:" + _throwing);
    }

    public ArrayList<Cup> cups() { return _cups; }

    public void setCupCapacity(int capacity) { _cups = new ArrayList<>(capacity); }

    public void removeCup(Cup c) { _cups.remove(c); }

    public boolean throwing() {
        return _throwing;
    }

    public void setThrowing(boolean value) {
        _throwing=value;
    }

    public void setId(int id) { _id=id; }

    public int getId() { return _id; }

    public void setMidShot(boolean value) { _midshot = value; }

    public boolean getMidShot() { return _midshot; }

    public boolean ballLaunch() { return _launch; }

    public void setLaunch(boolean value) {
        _launch = value;
    }

    public void initUser(String name) {
        _info=new UserInfo(name);
        _onlinePlayers.add(name);
    }

    public UserInfo info() {
        return _info;
    }

    public void addPlayer(String name) {
        if (!_onlinePlayers.contains(name)) {
            _onlinePlayers.add(name);
        }
    }

    public void removePlayer(String name) {
        _onlinePlayers.remove(name);
    }

    public ArrayList<String> getOnlinePlayers() {
        return _onlinePlayers;
    }

    public int plCount() {
        return _onlinePlayers.size();
    }

    public void resetToDefaults() {
        _cups.clear();
        _myturn = true;
        _throwing = false;
        _wasreset = true;
        _midshot = false;
        _gamestarted = false;
        _launch = false;
    }

    public boolean won() {
        return _won;
    }

    public void setwon(boolean val) {
        _won=val;
    }

    public void addTurn() {
        turns.add(currentTurn);
        currentTurn = new Turn(turns.size());
    }

    public void addThrowInfo(boolean made, int bb) {
        currentTurn.addThrow(made, bb);
    }

    public LinkedList<Turn> getTurns() {
        return turns;
    }
}
