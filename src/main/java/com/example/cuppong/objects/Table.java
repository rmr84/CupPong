package com.example.cuppong.objects;

import javafx.scene.canvas.GraphicsContext;

public class Table extends Sprite {
    public Table() {
        super("images/table.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext context) {
        context.drawImage(_image, pos.getX(), pos.getY());
    }
}
