package com.example.cuppong.util;

public class UserInfo {

    private String _name;

    private int _cupsmade = 0;
    private int _cupsmissed=0;
    private int _wins = 0;
    private int _losses=0;
    private int _ballsback=0;

    public UserInfo(String name) {
        _name=name;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setCupsMade(int x) {
        _cupsmade=x;
    }

    public void setCupsMissed(int x) {
        _cupsmissed=x;
    }

    public void setWins(int x) {
        _wins=x;
    }

    public void setLosses(int x) {
        _losses=x;
    }

    public void setBallsBack(int x) {
        _ballsback=x;
    }
}
