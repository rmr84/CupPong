package com.example.cuppong.objects;

import com.example.cuppong.objects.shadows.Shadow;
import com.example.cuppong.util.GV;
import com.example.cuppong.util.MouseHandler;
import com.example.cuppong.util.Vector2F;
import com.example.cuppong.util.Vector3F;
import javafx.scene.canvas.GraphicsContext;

public class Ball extends Sprite {

    private final int DEFAULT_SIZE = 20;

    private boolean mobile=false;

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
        super("images/sandro.png");
        pos.set(400, 300, 400);
        vel=new Vector3F(0,0,0);
        vel.setY(3);
        mobile=true;
        _width = DEFAULT_SIZE;
        _height=DEFAULT_SIZE;
        shadow = new Shadow(this, pos.getZ(), pos.getX());
        set_defaultsize(DEFAULT_SIZE, DEFAULT_SIZE);
    }



    public void launch(double x, double z) {
        //pos.set((float)x, 300, (float)z);
        //vel.set(0,0,0);
    }

    public void update() {
        _width=(int)(DEFAULT_SIZE+sizeMult);
        _height=(int)(DEFAULT_SIZE+sizeMult);
        if (!MouseHandler.getInstance().mouseDown()) {
            GV.getInstance().setThrowing(false);
            double Fx = -0.5 * Cd * A * rho * vel.getX() * vel.getX() * vel.getX() / Math.abs(vel.getX());
            double Fy = -0.5 * Cd * A * rho * vel.getY() * vel.getY() * vel.getY() / Math.abs(vel.getY());
            double Fz = -0.5 * Cd * A * rho * vel.getZ() * vel.getZ() * vel.getZ() / Math.abs(vel.getZ());

            //System.out.println("POS: ["+pos.getX()+","+pos.getY()+","+pos.getZ()+"]");

            Fx = Double.isNaN(Fx) ? 0 : Fx;
            Fy = Double.isNaN(Fy) ? 0 : Fy;
            Fz = Double.isNaN(Fz) ? 0 : Fz;

            double ax = Fx / mass;
            double ay = ag + (Fy / mass);
            double az = Fz / mass;

            float rate = 1f/80f;
            vel.addX((float)(ax * rate));
            vel.addY((float)(ay * rate));
            vel.addZ((float)(az * rate));

            pos.addX((float)(vel.getX()*rate*100f));
            pos.addY((float)(vel.getY()*rate*100f));
            pos.addZ((float)(vel.getZ()*rate*100f));


            if (pos.getX() < 0 - _width) {
                reset(false);
            }

            if (pos.getX() > 1000 + _width) {
                reset(true);
            }

            if (lastY==pos.getY() && !GV.getInstance().wasReset()) {
                reset(false);
            }
            lastY = pos.getY();
        } else {
            GV.getInstance().setThrowing(true);
        }

        mid.setX((float)(pos.getZ() + _width / 2));
        mid.setY((float)(pos.getX() + _height / 2));

        if(GV.getInstance().isMyTurn()) {
            if (pos.getY() > GV.getInstance().height() - radius) {
                vel.mulY(restitution);
                pos.setY((float) (GV.getInstance().height() - radius));

                //System.out.println(pos.toString());

                /*
                for (let i = 0; i < cups.length; i++) {
                    if (!cups[i].hit) {
                        if (ballMidX >= cups[i].hitBounds.left && ballMidX <= cups[i].hitBounds.right &&
                                ballMidY >= cups[i].hitBounds.top && ballMidY <= cups[i].hitBounds.bottom) {
                            cups[i].hit = true;
                            cupsHit++;
                            shotsMade++;
                            resetBall(true);
                            global.con.send("cup", getUserid(), i);
                            if (cupsHit >= global.cupCount) {
                                global.con.send("gameover", getUserid());
                                game.inMatch = false;
                            }
                            break;
                        }
                        if (ballMidX >= cups[i].hitBounds.left && ballMidX <= cups[i].hitBounds.right &&
                                ballMidY <= cups[i].y + cups[i].height && ballMidY >= cups[i].hitBounds.bottom) {
                            ball.velocity.x *= ball.restitution;
                            ball.velocity.z *= ball.restitution;
                        }
                    }
                }*/
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
                lastY = 0;
                clearInterval(loopTimer);
            }*/
        }

        /*
        ctx.clearRect(0,0,width,height);
        ctx.save();*/
        sizeMult = 0.3 * (GV.getInstance().height() - pos.getY());
        /*
        if (global.myturn) {
            ctx.drawImage(images.ball, ball.position.z, ball.position.x, size + sizeMult, size + sizeMult);
            if (mouse.isDown) {
                ctx.drawImage(images.dash, 0, 325, 1000, 25);
            }
        }
        if (!global.myturn) {
            ctx.fillStyle = "#e00909";
            ctx.font = "bold 16px Arial";
            ctx.fillText("it is not your turn", 400, 400);
        }
        ctx.restore();*/
        shadow.update();
    }

    public void render(GraphicsContext context) {
        shadow.render(context);
        context.drawImage(_image, pos.getZ(), pos.getX(), _width, _height);
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile=mobile;
    }

    private void reset(boolean counted) {
        if (counted) {

        } else {

        }

        //vel.set(0,0,0);
        pos.set(400,(float)(400-_height),300);
        shadow = new Shadow(this, pos.getZ(), pos.getX());
        GV.getInstance().setReset(true);
    }
}
