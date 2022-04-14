package com.example.cuppong.objects;

import com.example.cuppong.objects.shadows.Shadow;
import com.example.cuppong.util.Bounds;
import javafx.scene.canvas.GraphicsContext;

public class Cup extends Sprite {

    private final int DEFAULT_WIDTH = 90;
    private final int DEFAULT_HEIGHT= 120;

    private int _id=-1;

    private Shadow shadow;

    private Bounds _bounds;

    public Cup(float x, float y, float z, int id) {
        super("images/cupshadow.png");
        set_defaultsize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        _width = DEFAULT_WIDTH;
        _height = DEFAULT_HEIGHT;
        pos.set(x,y,z);
        shadow = new Shadow(this, pos.getZ(), pos.getX());
        _bounds = new Bounds(z,x,_width,_height,true);
        _id=id;
    }

    public void update() {
        //shadow.update();

    }

    public int id() {
        return _id;
    }

    public void render(GraphicsContext context) {

        //shadow.render(context);
        context.drawImage(_image, pos.getZ(), pos.getX(), _width, _height);
    }

    public Bounds bounds() { return _bounds; }
}
