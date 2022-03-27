package com.example.cuppong.util;

import com.example.cuppong.gamestates.GameState;
import com.example.cuppong.gamestates.GameStateManager;
import com.example.cuppong.util.KeyHandler;
import com.example.cuppong.util.MouseHandler;


public class GamePanel implements Runnable {
    public static int width = 0;
    public static int height = 0;

    public static int oldFrameCount;

    private Thread thread;
    private boolean running = false;
    //private Graphics2D graphics;
    //private BufferedImage bufferedImage;

    private KeyHandler key;
    private MouseHandler mouse;
    private GameStateManager manager;

    public GamePanel(int w, int h) {
        width = w;
        height = h;

    }

    public void addNotify() {
        if(thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    public void initialize() {
        key = new KeyHandler();
        mouse = new MouseHandler();
        running=true;
        //set buffer/context here
    }

    public void run() {
        initialize();

        final double game_hz = 64.0;
        final double timebeforeupdate = 1000000000/game_hz;
        final int maxupdates=5;

        double lastupdate = System.nanoTime();
        double lastrender;

        final double targetfps = 120.0;
        final double totaltimebr = 1000000000 / targetfps;

        int framecount = 0;
        int lastsecond = (int)(lastupdate / 1000000000);
        oldFrameCount=0;

        while(running) {
            double now = System.nanoTime();
            int updatecount=0;

            while ((now - lastupdate) > timebeforeupdate && (updatecount < maxupdates)) {
                update();
                input(key,mouse);
                lastupdate += timebeforeupdate;
                updatecount++;
            }

            if (now - lastupdate > timebeforeupdate) {
                lastupdate = now - timebeforeupdate;
            }

            input(key,mouse);
            render();
            draw();

            lastrender=now;
            framecount++;
            int second = (int)(lastupdate/1000000000);
            if(second > lastsecond) {
                if (framecount != oldFrameCount) {
                    oldFrameCount=framecount;
                }
                framecount=0;
                lastsecond=second;
            }

            while (now-lastrender < totaltimebr && now - lastupdate < timebeforeupdate) {
                Thread.yield();
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                now=System.nanoTime();
            }
        }
    }

    public void update() {
        //getMousePosition();
        manager.update();
    }

    public void input(KeyHandler k, MouseHandler m) {
        manager.input(k, m);
    }

    public void render() {
        /*if graphics/context != null*/
        manager.render();
    }

    public void draw() {
        /*
        Graphics g2 = (Graphics)getGraphics();
        g2.drawImage(img, 0, 0, width, height, null);
        g2.dispose();
         */
    }
}
