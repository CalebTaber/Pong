package main;

import javafx.geometry.Bounds;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.File;
import java.util.Random;

public class Puck {

    private Circle circle;
    private int x, y, xInit, yInit;
    private int xVelocity, yVelocity;
    private Bounds LPaddle, RPaddle;

    private MediaPlayer mp;

    public Puck(int x, int y) {
        this.x = x;
        this.y = y;
        xInit = x;
        yInit = y;
        xVelocity = 5;
        yVelocity = -1;
        circle = new Circle(x, y, 6);
        circle.setFill(Color.WHITE);

        try {
            File file = new File(getClass().getResource("/sound/blip.wav").toURI());
            System.out.println(file.getAbsolutePath());
            Media blip = new Media(file.toURI().toString());
            mp = new MediaPlayer(blip);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        x = xInit;
        y = yInit;

        int[] velocities = {-6, -5, 5, 6};
        Random r = new Random();
        xVelocity = velocities[r.nextInt(velocities.length)];
        yVelocity = velocities[r.nextInt(velocities.length)];
    }

    public void update(Bounds left, Bounds right) {
        LPaddle = left;
        RPaddle = right;
        move();
    }

    private void blip() {
        mp.play();
        mp.seek(Duration.ZERO);
    }

    private void move() {
        x += xVelocity;
        y += yVelocity;

        circle.setCenterX(x);
        circle.setCenterY(y);

        collide();
    }

    private void collide() {
        Bounds b = circle.getBoundsInParent();
        if(b.intersects(LPaddle) || b.intersects(RPaddle)) {
            xVelocity *= -1;

            // Speed up puck each time a volley occurs
            if(xVelocity < 0) xVelocity--;
            else xVelocity++;

            if(yVelocity < 0) yVelocity--;
            else yVelocity++;

            blip();
        }

        if(y - circle.getRadius() <= 0 || y + circle.getRadius() >= 560) {
            yVelocity *= -1;
            blip();
        }
    }

    public int getXVelocity() {
        return xVelocity;
    }

    public Circle getCircle() {
        return circle;
    }

}
