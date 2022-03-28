package com.example.cuppong.gamestates;

import com.example.cuppong.util.KeyHandler;
import com.example.cuppong.util.MouseHandler;
import javafx.scene.canvas.GraphicsContext;

public class PauseState extends GameState {

    public PauseState(GameStateManager manager) {
        super(manager);
    }

    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void input(KeyHandler k) {
        manager.input(k);
    }

    @Override
    public void render(GraphicsContext context) {
        manager.render(context);
    }


}
