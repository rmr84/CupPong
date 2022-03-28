package com.example.cuppong.gamestates;

import com.example.cuppong.Game;
import com.example.cuppong.util.KeyHandler;
import com.example.cuppong.util.MouseHandler;
import javafx.scene.canvas.GraphicsContext;

public class GameStateManager {

    private GameState[] states;

    public static final int STATE_PLAY = 0;
    public static final int STATE_PAUSE = 1;

    public GameStateManager() {
        states=new GameState[2];

    }

    public void suspend(int state) {
        if (states[state] != null) {
            states[state].suspended = true;
        }
    }

    public void allow(int state) {
        if (states[state] != null) {
            states[state].suspended = false;
        }
    }

    public void remove(int state) {
        states[state] = null;
    }

    public void add(int state) {
        if (states[state] != null) {
            return;
        }

        switch (state) {
            case STATE_PLAY:
                states[STATE_PLAY] = new PlayState(this, 10);
                break;
            case STATE_PAUSE:
                states[STATE_PAUSE] = new PauseState(this);
                break;
        }
    }

    public GameState gameState(int state) {
        return states[state];
    }

    public boolean active(int state) {
        return states[state] != null;
    }

    public void update() {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null && !states[i].suspended) {
                states[i].update();
            }
        }
    }

    public void input(KeyHandler k) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null && !states[i].suspended) {
                states[i].input(k);
            }
        }
    }

    public void render(GraphicsContext context) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null && !states[i].suspended) {
                states[i].render(context);
            }
        }
    }
}
