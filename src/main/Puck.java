package main;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Puck {

    private Circle circle;
    private int x, y, xInit, yInit;
    private int xVelocity, yVelocity;
    private Bounds LPaddle, RPaddle;

    public Puck(int x, int y) {
        this.x = x;
        this.y = y;
        xInit = x;
        yInit = y;
        xVelocity = 5;
        yVelocity = -1;
        circle = new Circle(x, y, 6);
        circle.setFill(Color.WHITE);
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
        }

        if(y - circle.getRadius() <= 0 || y + circle.getRadius() >= 560) yVelocity *= -1;
    }

    public Circle getCircle() {
        return circle;
    }

}
