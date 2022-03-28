package com.example.cuppong.objects;

import com.example.cuppong.CupPongMain;
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
    private final Vector3F vel = new Vector3F(10,0,0);
    private final double mass = 0.1;
    private final double restitution = -0.7;
    private double sizeMult = 1;

    public Ball() {
        super("images/ball.png");
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
            vel.addY((float)(ax * rate));
            vel.addZ((float)(ax * rate));
            pos.addX((float)(vel.getX()*rate*100));
            pos.addX((float)(vel.getY()*rate*100));
            pos.addX((float)(vel.getZ()*rate*100));

            if (pos.getX() < 0 - (size+sizeMult)) {
                //resetBall
            }
        }
    }

    public void render(GraphicsContext context) {
        context.drawImage(_image, pos.getX(), pos.getY(), size, size);
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile=mobile;
    }


}
