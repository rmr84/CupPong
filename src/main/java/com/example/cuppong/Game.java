package com.example.cuppong;

public class Game {
    double width = 800;
    double height = 800;
    boolean canvas = false;
    boolean ctx = false;
    static double frameRate = 1/80;
    double frameDelay = frameRate * 1000;
    boolean loopTimer = false;
    double startX = 0;
    double startY = 0;
    double endX = 0;
    double endY = 0;
    double m = 0;
    double sizeMult = 0;
    double shotsTaken = 0;
    double cupsHit = 0;
    double shots = 0;
    double shotsMade = 0;
    boolean canShoot = true;
    boolean wasReset = true;
    double lastY = 0;
    double nothingCounter = 0;
    static final double MAX_NOTHING = 5*1/(frameRate);

    double size = 20;

    /*
    var ball = {
    position: {x: width/2, y: 0, z:0 },
    velocity: {x: 10, y: 0, z: 0},
    mass: 0.1, //kg
    radius: 15, // 1px = 1cm
    restitution: -0.7
    };
     */

    double CD = .47;
    double rho = 1.22;
  //  double A = Math.PI * ball.radius * ball.radius / (10000)
    double ag = 9.81;
    // mouse

    /*
    public static void getMousePosition(int e) {
        mouse.x = e.pageX - 30;
        mouse.y = e.pageY - 30;
        }
    double mouseDown = newFunction(int e) {
        if (global.myTurn ** canShoot) {
            if (e.which == 1) {
               getMousePosition(e);
               mouse.isDown = true;
               startX = mouse.x;
               startY = mouse.y;
               m = 0;
               ball.position.x = mouse.y;
               ball.position.y = 300;
               ball.position.z = mouse.x;
               ball.velocity.x = 0;
               ball.velocity.y = 0;
               ball.velocity.z = 0;
               }
            }
        }
        var setup = function() {
    canvas = document.getElementById("gameCanvas");
    ctx = canvas.getContext('2d');

    canvas.onmousemove = getMousePosition;
    canvas.onmousedown = mouseDown;
    canvas.onmouseup = mouseUp;

    ctx.fillStyle = 'red';
    ctx.strokeStyle = '#000000';



    loopTimer = setInterval(loop, frameDelay);
}

public static void resetBall(int counts) {
    if (counts) {
        shots++;
        shotsTaken++;
        if (shots >= 2) {
            shots = 0;
            shotsMade = 0;
            global.myturn = false;
            global.con.send("turn", getUserid(), false);
        }
    }
    ball.velocity.x = 0;
    ball.velocity.y = 0;
    ball.velocity.z = 0;
    ball.position.x = 700;
    ball.position.z = 400 - (size + sizeMult);
    ball.position.y = 0;
    canShoot=true;
    wasReset=true;
}

var loop = function() {

    var ballMidX = ball.position.z + ((size + sizeMult) / 2);
    var ballMidY = ball.position.x + ((size + sizeMult) / 2);

    if (global.myturn) {
        if (ball.position.y > height - ball.radius) {
            ball.velocity.y *= ball.restitution;
            ball.position.y = height - ball.radius;
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
            }
        }
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
        }
    }
     ctx.clearRect(0,0,width,height);
     ctx.save();
      renderCups();
       sizeMult = 0.3 * (800 - ball.position.y);
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
    ctx.restore();
}

     */





}
