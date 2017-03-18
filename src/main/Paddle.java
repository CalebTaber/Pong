package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {

    private Rectangle rect;
    private int x, y;
    private int direction;

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
        direction = 0;
        rect = new Rectangle(x, y, 12, 96);
        rect.setFill(Color.WHITE);
    }

    public void update() {
        if(direction != 0) move(direction);
    }

    private void move(int direction) {
        y += direction * 10;
        rect.setY(y);

        collision();
    }

    private void collision() {
        if(y <= 0) y = 0;
        else if(y + rect.getHeight() >= 560) y = 560 - (int) rect.getHeight();
    }

    public void setDirection(int i) {
        direction = i;
    }

    public Rectangle getRect() {
        return rect;
    }

}
