package com.example.cuppong.objects;

import com.example.cuppong.objects.shadows.Shadow;
import javafx.scene.canvas.GraphicsContext;

public class Cup extends Sprite {

    private final int DEFAULT_WIDTH = 90;
    private final int DEFAULT_HEIGHT= 120;

    private Shadow shadow;


    public Cup(float x, float y, float z) {
        super("images/cup.png");
        set_defaultsize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        _width = DEFAULT_WIDTH;
        _height = DEFAULT_HEIGHT;
        pos.set(x,y,z);
        shadow = new Shadow(this, pos.getZ(), pos.getX());
    }

    public void update() {
        shadow.update();

    }

    public void render(GraphicsContext context) {

        shadow.render(context);
        context.drawImage(_image, pos.getZ(), pos.getX(), _width, _height);
    }
}
