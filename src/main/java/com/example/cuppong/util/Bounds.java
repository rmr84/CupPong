package com.example.cuppong.util;

public class Bounds {
    private double _top;
    private double _bottom;
    private double _left;
    private double _right;

    private final double _PADDING = 3;

    public Bounds(float x, float y, int width, int height, boolean use_padding) {
        _top = y+(use_padding ? _PADDING : 0);
        _bottom = y+(0.4*height)+(use_padding ? -1*_PADDING : 0);//bottom is the bottom of the rim
        _left = x+(use_padding ? _PADDING : 0);
        _right = x+width+(use_padding ? -1*_PADDING : 0);
    }

    public double top() {
        return _top;
    }

    public double bottom() {
        return _bottom;
    }

    public double left() {
        return _left;
    }

    public double right() {
        return _right;
    }

}
