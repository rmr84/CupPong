package com.example.cuppong.objects;

import com.example.cuppong.CupPongMain;
import com.example.cuppong.util.Bounds;
import com.example.cuppong.util.Vector3F;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite {
    protected int _width;
    protected int _height;
    protected Image _image;

    protected Vector3F pos;
    protected Bounds _bounds;

    private int default_width;
    private int default_height;

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

    public Bounds bounds() { return _bounds; }

    public void setBounds(Bounds b) { _bounds = b; }

    public void use_defaultsize() {
        _width = default_width;
        _height = default_height;
    }

    public void set_defaultsize(int w, int h) {
        default_width = w;
        default_height = h;
    }

    public int get_default_width() {
        return default_width;
    }

    public int get_default_height() {
        return default_height;
    }

    public abstract void update();
    public abstract void render(GraphicsContext context);
}
