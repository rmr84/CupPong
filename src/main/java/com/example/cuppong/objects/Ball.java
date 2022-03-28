package com.example.cuppong.objects;

import com.example.cuppong.CupPongMain;
import com.example.cuppong.util.GV;
import com.example.cuppong.util.Vector2F;
import com.example.cuppong.util.Vector3F;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Ball extends Sprite {

    private double size = 64;
    private boolean mobile=false;

    private final double Cd = .47;
    private final double rho = 1.22;
    private final double radius = 15;
    private final double A = Math.PI * radius * radius / (10000);
    private final double ag = 9.81;
    private Vector2F mid = new Vector2F(0, 0);
    private final Vector3F vel = new Vector3F(10,0,0);
    private final double mass = 0.1;
    private final double restitution = -0.7;
    private double sizeMult = 1;
    private double lastY = 0;

    public Ball() {
        super("images/ball.png");
        pos.set(400, 300, 400);
    }



    public void launch(double x, double z) {
        pos.set((float)x, 300, (float)z);
        vel.set(0,0,0);
    }

    public void update() {
        if (mobile) {
            double Fx = -0.5 * Cd * A * rho * vel.getX() * vel.getX() * vel.getX() / Math.abs(vel.getX());
            double Fy = -0.5 * Cd * A * rho * vel.getY() * vel.getY() * vel.getY() / Math.abs(vel.getY());
            double Fz = -0.5 * Cd * A * rho * vel.getZ() * vel.getZ() * vel.getZ() / Math.abs(vel.getZ());

            Fx = Double.isNaN(Fx) ? 0 : Fx;
            Fy = Double.isNaN(Fy) ? 0 : Fy;

            double ax = Fx / mass;
            double ay = ag + (Fy / mass);
            double az = Fz / mass;

            double rate = 1/80;
            vel.addX((float)(ax * rate));
            vel.addY((float)(ay * rate));
            vel.addZ((float)(az * rate));
            pos.addX((float)(vel.getX()*rate*100));
            pos.addY((float)(vel.getY()*rate*100));
            pos.addZ((float)(vel.getZ()*rate*100));

            if (pos.getX() < 0 - (size+sizeMult)) {
                reset(false);
            }

            if (pos.getX() > 1000 + (size+sizeMult)) {
                reset(true);
            }

            if (lastY == pos.getY() && !GV.getInstance().wasReset()) {
                reset(false);
            }
            lastY = pos.getY();
        }

        mid.setX((float)(pos.getZ() + (size + sizeMult) / 2));
        mid.setY((float)(pos.getX() + (size + sizeMult) / 2));

        if(GV.getInstance().isMyTurn()) {
            if (pos.getY() > GV.getInstance().height() - radius) {
                vel.mulY((float) restitution);
                pos.setY((float) (GV.getInstance().height() - radius));
                System.out.println("Y: " + pos.getY());

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
    }

    public void render(GraphicsContext context) {
        context.drawImage(_image, pos.getZ(), pos.getX(), size + sizeMult, size + sizeMult);
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

        vel.set(0,0,0);
        pos.set(400,(float)(400-(size+sizeMult)),300);
        GV.getInstance().setReset(true);
    }
}
