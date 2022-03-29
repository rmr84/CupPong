package com.example.cuppong.util;

public class Vector2F {
    protected float _x;
    protected float _y;

    public Vector2F(float x, float y) {
        _x=x;
        _y=y;
    }

    public float getX() {
        return _x;
    }

    public float getY() {
        return _y;
    }

    public void setX(float x) {
        _x=x;
    }

    public void setY(float y) {
        _y=y;
    }

    public void addX(float dx) {
        _x+=dx;
    }

    public void addY(float dy) {
        _y+=dy;
    }

    public void mulX(float dx) { _x*=dx; }

    public void mulY(float dy) { _y*=dy; }

    public void set(Vector2F v) {
        _x=v.getX();
        _y=v.getY();
    }

    public void set(float x, float y) {
        _x=x;
        _y=y;
    }

    @Override
    public String toString() {
        return "POS: (" + _x + ", " + _y + ")";
    }
}
