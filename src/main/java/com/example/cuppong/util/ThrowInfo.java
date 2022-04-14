package com.example.cuppong.util;

public class ThrowInfo {
    private boolean _made=false;
    private int _ballsback=0;

    public ThrowInfo(boolean made, int ballsback) {
        _made=made;
        _ballsback=ballsback;
    }

    @Override
    public String toString() {
        String back = _ballsback > 0 ? " [Balls back=" : "";
        if(_ballsback==1) {
            back += "yes]";
        } else if (_ballsback==2) {
            back += "no]";
        }
        return (_made ? "Hit" : "Miss") + back;
    }
}
