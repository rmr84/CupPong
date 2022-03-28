package com.example.cuppong.util;

public class Vector3F extends Vector2F {
    float _z;

    public Vector3F(float x, float y, float z) {
        super(x, y);
        _z=z;
    }

    public float getZ() {
        return _z;
    }

    public void setZ(float z) {
        _z=z;
    }

    public void addZ(float dz) {
        _z+=dz;
    }

    public void mulZ(float dz) { _z*=dz; }

    public void set(Vector3F v) {
        _x = v.getX();
        _y = v.getY();
        _z = v.getZ();
    }

    public void set(float x, float y, float z) {
        _x=x;
        _y=y;
        _z=z;
    }
}
