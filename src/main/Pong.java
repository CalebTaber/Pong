package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Pong extends Application {

    private Pane layout;
    private int width = 1000;
    private int height = 560;

    private Puck puck;
    private Paddle left, right;
    private int LScore, RScore;
    private Text lScoreCount, rScoreCount;

    private void update() {
        puck.update(left.getRect().getBoundsInParent(), right.getRect().getBoundsInParent());
        left.update();
        right.update();

        if(puck.getCircle().getCenterX() - puck.getCircle().getRadius() < 0) {
            RScore++;
            puck.reset();
            rScoreCount.setText(RScore + "");
        } else if(puck.getCircle().getCenterX() + puck.getCircle().getRadius() >= 1000) {
            LScore++;
            puck.reset();
            lScoreCount.setText(LScore + "");
        }

    }

    public void start(Stage window) {
        window = new Stage();
        layout = new Pane();
        layout.setBackground(Background.EMPTY);
        Scene main = new Scene(layout);
        main.setFill(Color.BLACK);

        window.setTitle("Pong");
        window.setScene(main);
        window.setResizable(false);
        window.setWidth(width);
        window.setHeight(height);

        // Create scoreboard
        Font font = new Font("Ubuntu", 48);

        lScoreCount = new Text(LScore + "");
        lScoreCount.setLayoutX(width / 4);
        lScoreCount.setLayoutY(50);
        lScoreCount.setFill(Color.WHITE);
        lScoreCount.setFont(font);

        rScoreCount = new Text(RScore + "");
        rScoreCount.setLayoutX(width - (width / 4));
        rScoreCount.setLayoutY(50);
        rScoreCount.setFill(Color.WHITE);
        rScoreCount.setFont(font);

        // Create "net" in the middle of the screen
        Rectangle[] net = new Rectangle[35];
        for(int i = 0; i < net.length; i++) {
            net[i] = new Rectangle((width / 2) - 4, i * 16, 8, 8);
            net[i].setFill(Color.WHITE);
        }

        // Create paddles and puck
        left = new Paddle(10, (height / 2) - 48);
        right = new Paddle(width - 22, (height / 2) - 48);

        puck = new Puck(width / 2, height / 2);

        layout.getChildren().addAll(puck.getCircle(), left.getRect(), right.getRect(), lScoreCount, rScoreCount);
        for(Rectangle r: net) {
            layout.getChildren().add(r);
        }

        new AnimationTimer() {
            public void handle(long time) {
                update();
            }
        }.start();

        // Get keyboard input
        main.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.UP)) right.setDirection(-1);
                if(event.getCode().equals(KeyCode.DOWN)) right.setDirection(1);

                if(event.getCode().equals(KeyCode.W)) left.setDirection(-1);
                if(event.getCode().equals(KeyCode.S)) left.setDirection(1);
            }
        });

        main.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.UP)) right.setDirection(0);
                if(event.getCode().equals(KeyCode.DOWN)) right.setDirection(0);

                if(event.getCode().equals(KeyCode.W)) left.setDirection(0);
                if(event.getCode().equals(KeyCode.S)) left.setDirection(0);
            }
        });

        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
