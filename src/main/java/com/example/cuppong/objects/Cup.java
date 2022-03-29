package com.example.cuppong.objects;

import com.example.cuppong.objects.shadows.Shadow;
import javafx.scene.canvas.GraphicsContext;

public class Cup extends Sprite {

    private final int DEFAULT_WIDTH = 200;
    private final int DEFAULT_HEIGHT= 200;

    private Shadow shadow;


    public Cup() {
        super("images/cup.png");
        set_defaultsize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        _width = DEFAULT_WIDTH;
        _height = DEFAULT_HEIGHT;
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
