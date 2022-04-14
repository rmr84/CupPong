package com.example.cuppong.objects;

import com.example.cuppong.objects.shadows.Shadow;
import com.example.cuppong.util.*;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Ball extends Sprite {

    private final int DEFAULT_SIZE = 30;

    private final float Cd = .47f;
    private final float rho = 1.22f;
    private final float radius = 15f;
    private final double A = Math.PI * radius * radius / (10000);
    private final float ag = 9.81f;
    private Vector2F mid = new Vector2F(0, 0);
    private final Vector3F vel;
    private final float mass = 0.1f;
    private final float restitution = -0.7f;
    private double sizeMult = 1;
    private float lastY = 0;

    private Shadow shadow;

    public Ball() {
        super("images/sandro2.png");
        pos.set(400, 300, 400);
        vel=new Vector3F(0,0,0);
        vel.setY(6);
        _width = DEFAULT_SIZE;
        _height=DEFAULT_SIZE;
        shadow = new Shadow(this, pos.getZ(), pos.getX());
        set_defaultsize(DEFAULT_SIZE, DEFAULT_SIZE);
    }



    public void launch() {
        GV.getInstance().setLaunch(false);
        lastY=-1;
        GV.getInstance().setReset(false);
        double startX = MouseHandler.getInstance().getStartX();
        double startY = MouseHandler.getInstance().getStartY();
        double endX = MouseHandler.getInstance().getX();
        double endY = MouseHandler.getInstance().getY();


        if (endY >= startY || endY < 300) {
            reset(false, -1);
            return;
        }

        ClientHandler.getInstance().sendMessage("throw");

        vel.set((float)((endY-startY)/15.0), 5, (float)((endX-startX)/15.0));
        //pos.set((float)x, 300, (float)z);
        //vel.set(0,0,0);
    }

    public void update() {
        _width=(int)(DEFAULT_SIZE+sizeMult);
        _height=(int)(DEFAULT_SIZE+sizeMult);

        if (GV.getInstance().ballLaunch()) {
            launch();
        }

        if (GV.getInstance().isMyTurn()) {  //is it my turn
            if (GV.getInstance().throwing()) {  //am i able to throw
                if (!GV.getInstance().getMidShot()) {   //is the ball moving
                    //if not, make ball centered on mouse
                    float x = (float)(MouseHandler.getInstance().getX() - getWidth()/2);
                    float y = (float)(MouseHandler.getInstance().getY() - getHeight()/2);
                    pos.set(y, 300, x);
                }
            } else {
                double Fx = -0.5 * Cd * A * rho * vel.getX() * vel.getX() * vel.getX() / Math.abs(vel.getX());
                double Fy = -0.5 * Cd * A * rho * vel.getY() * vel.getY() * vel.getY() / Math.abs(vel.getY());
                double Fz = -0.5 * Cd * A * rho * vel.getZ() * vel.getZ() * vel.getZ() / Math.abs(vel.getZ());

                Fx = Double.isNaN(Fx) ? 0 : Fx;
                Fy = Double.isNaN(Fy) ? 0 : Fy;
                Fz = Double.isNaN(Fz) ? 0 : Fz;

                double ax = Fx / mass;
                double ay = ag + (Fy / mass);
                double az = Fz / mass;

                float rate = 1f / 80f;
                vel.addX((float) (ax * rate));
                vel.addY((float) (ay * rate));
                vel.addZ((float) (az * rate));

                pos.addX((float) (vel.getX() * rate * 100f));
                pos.addY((float) (vel.getY() * rate * 100f));
                pos.addZ((float) (vel.getZ() * rate * 100f));

                //if the ball is out of screen near opponent side
                if (pos.getX() < 0 - _width) {
                    reset(true, -1);
                    return;
                }

                //if ball is behind you
                if (pos.getZ() > GV.getInstance().height()) {
                    reset(true, -1);
                    return;
                }

                if(pos.getZ() < 0-_height) {
                    reset(true, -1);
                    return;
                }

                //if the ball flies off the screen on opponent side reset and count it
                if (pos.getX() > 1000 + _width) {
                    reset(false, -1);
                    return;
                }

                //if ball has stopped bouncing reset
                if ((lastY == pos.getY() || vel.getY()==0f || (vel.getZ()==0f&&vel.getZ()==0f)) && !GV.getInstance().wasReset()) {
                    reset(false, -1);
                    return;
                }
                lastY = pos.getY();

                mid.setX((float)(pos.getZ() + _width / 2));
                mid.setY((float)(pos.getX() + _height / 2));

                //test for collisions on cups
                if (pos.getY() > GV.getInstance().height() - radius) {
                    vel.mulY(restitution);
                    pos.setY((float) (GV.getInstance().height() - radius));
                    ArrayList<Cup> cups = GV.getInstance().cups();
                    for (int i = 0; i < cups.size(); i++) {
                        Cup c = cups.get(i);
                        if (mid.getX() >= c.bounds().left() && mid.getX() <= c.bounds().right() &&
                                mid.getY() >= c.bounds().top() && mid.getY() <= c.bounds().bottom()) {
                            //cups[i].hit = true;
                            GV.getInstance().removeCup(c);
                            reset(true, c.id());
                            break;
                        }
                        if (mid.getX() >= c.bounds().left() && mid.getX() <= c.bounds().right() &&
                                mid.getY() <= c.getPos().getY() + c.getHeight() && mid.getY() >= c.bounds().bottom()) {
                            vel.mulX(restitution);
                            vel.mulZ(restitution);
                        }
                    }
                }
                /*
                if (!game.inMatch) {
                    global.shots = shotsTaken;
                    global.hit = cupsHit;
                    startX = 0;
                    startY = 0;
                    endX = 0;
                    endY = 0;
                    m = 0;
                    sizeMult = 0;
                    cupsHit = 0;
                    shots = 0;
                    shotsMade = 0;
                    canShoot = true;
                    wasReset=true;
                    clearInterval(loopTimer);
                }*/
            }
            lastY = 0;

            sizeMult = 0.3 * (GV.getInstance().height() - pos.getY());

            //ctx.drawImage(images.dash, 0, 325, 1000, 25);

            //shadow.update();
        } else {

        }
    }

    public void render(GraphicsContext context) {
        //shadow.render(context);
        if (GV.getInstance().isMyTurn()) {
            context.drawImage(_image, pos.getZ(), pos.getX(), _width, _height);
        }
    }

    /**
     * Reset ball after a throw
     * @param counted   If the throw counted or not
     * @param made      If the user made the ball into a cup. -1 if not, else send the index of cup they hit
     */
    private void reset(boolean counted, int made) {

        ClientHandler.getInstance().sendMessage("rball,counted:" + counted + ",made:" + made);

        vel.set(0,0,0);
        shadow = new Shadow(this, pos.getZ(), pos.getX());
        GV.getInstance().setThrowing(true);
        MouseHandler.getInstance().setMouseDown(false);
        GV.getInstance().setReset(true);
        float x = (float)(MouseHandler.getInstance().getX() - getWidth()/2);
        float y = (float)(MouseHandler.getInstance().getY() - getHeight()/2);
        pos.set(y, 400, x);

    }
}
