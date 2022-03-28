package com.example.cuppong.objects;

import com.example.cuppong.CupPongMain;
import com.example.cuppong.util.Vector3F;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Vector;

public abstract class Sprite {
    protected int _width;
    protected int _height;
    protected Image _image;

    protected Vector3F pos;

    public Sprite(String filename) {
        pos = new Vector3F(0, 0, 0);
        loadImage(filename);
    }

    public Vector3F getPos() {
        return pos;
    }

    public void setPos(Vector3F pos) {
        this.pos.set(pos);
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public void setWidth(int width) {
        _width=width;
    }

    public void setHeight(int height) {
        _height=height;
    }

    private void loadImage(String filename) {
        _image = new Image(CupPongMain.class.getResourceAsStream(filename));
    }

    public abstract void update();
    public abstract void render(GraphicsContext context);
}
