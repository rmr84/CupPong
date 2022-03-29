package com.example.cuppong.objects;

import com.example.cuppong.objects.shadows.Shadow;
import javafx.scene.canvas.GraphicsContext;

public class Cup extends Sprite {

    private final int DEFAULT_WIDTH = 10;
    private final int DEFAULT_HEIGHT=10;

    private Shadow shadow = new Shadow(this);

    public Cup() {
        super("images/cupshadow.png");
        set_defaultsize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public void update() {
        shadow.update();

    }

    public void render(GraphicsContext context) {
        shadow.render(context);

    }
}
