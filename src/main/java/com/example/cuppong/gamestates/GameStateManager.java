package com.example.cuppong.gamestates;

import com.example.cuppong.util.KeyHandler;
import com.example.cuppong.util.MouseHandler;

public class GameStateManager {

    private GameState[] states;

    public static final int STATE_MENU = 0;
    public static final int STATE_PLAY = 1;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_WIN = 3;
    public static final int STATE_LOSE = 4;

    public GameStateManager() {

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
            case STATE_MENU:
                break;
            case STATE_PLAY:
                break;
            case STATE_PAUSE:
                break;
            case STATE_WIN:
                break;
            case STATE_LOSE:
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

    public void input(KeyHandler k, MouseHandler m) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null && !states[i].suspended) {
                states[i].input(k, m);
            }
        }
    }

    public void render() {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null && !states[i].suspended) {
                states[i].render();
            }
        }
    }
}
