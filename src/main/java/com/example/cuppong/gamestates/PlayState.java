package com.example.cuppong.gamestates;

import com.example.cuppong.objects.Ball;
import com.example.cuppong.objects.Cup;
import com.example.cuppong.objects.Table;
import com.example.cuppong.util.KeyHandler;
import com.example.cuppong.util.MouseHandler;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class PlayState extends GameState {

    private final int width = 1280;
    private final int height = 720;

    private Ball ball;
    private ArrayList<Cup> cups;
    private Table table;

    public PlayState(GameStateManager manager, int cupsLength) {
        super(manager);
        initializeCups(cupsLength);
        ball = new Ball();
        table=new Table();
    }

    private void initializeCups(int cupsLength) {
        cups = new ArrayList<>(cupsLength);
        cups.forEach(c -> {
            c = new Cup();
        });
    }

    public void update() {

        ball.getPos().setX((int)MouseHandler.getInstance().getX());
        ball.getPos().setY((int)MouseHandler.getInstance().getY());
    }

    public void input(KeyHandler k) {

    }

    public void render(GraphicsContext context) {
        table.render(context);
        ball.render(context);
    }
}
