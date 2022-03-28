package com.example.cuppong.gamestates;

import com.example.cuppong.util.KeyHandler;
import com.example.cuppong.util.MouseHandler;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameState {
    protected GameStateManager manager;
    public boolean suspended = false;

    public GameState(GameStateManager manager) {
        this.manager = manager;
    }

    public abstract void update();
    public abstract void input(KeyHandler k);
    public abstract void render(GraphicsContext context);
}
