package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;

public class Snake extends Application {


    private int frame;
    private int width = 1200;
    private int height = 800;

    private Pane layout;
    private Player p;
    private KeyEvent k;
    private Rectangle food;

    public void start(Stage window) {
        window = new Stage();
        layout = new Pane();
        Scene main = new Scene(layout);

        layout.setBackground(Background.EMPTY);
        main.setFill(Color.BLACK);
        window.setTitle("Snake");
        window.setWidth(width);
        window.setHeight(height);
        window.setScene(main);

        p = new Player(width, height);
        for(Rectangle r: p.getSnake()) {
            layout.getChildren().add(r);
        }

        food = new Rectangle(16, 16);
        food.setFill(Color.LIGHTGREEN);
        addFood();

        main.setOnKeyPressed(e -> k = e);

        new AnimationTimer() {
            public void handle(long now) {
                frame++;
                if(frame % 4 != 0) {
                    return;
                }

                // Remove the snake
                for(Rectangle r: p.getSnake()) {
                    layout.getChildren().remove(r);
                }

                if(k != null) {
                    if (k.getCode() == KeyCode.UP) p.update(0, -16);
                    else if (k.getCode() == KeyCode.DOWN) p.update(0, 16);
                    else if (k.getCode() == KeyCode.LEFT) p.update(-16, 0);
                    else if (k.getCode() == KeyCode.RIGHT) p.update(16, 0);
                }

                if(p.getSnake().get(0).intersects(food.getBoundsInLocal())) {
                    p.grow();
                    addFood();
                }

                if(!p.isAlive()) {
                    p.reset();
                    k = null;
                    addFood();
                }

                // Add the snake
                for (Rectangle r : p.getSnake()) {
                    layout.getChildren().add(r);
                }
            }
        }.start();

        layout.getChildren().add(food);

        window.show();
    }

    private void addFood() {
        Random rand = new Random();
        int x = rand.nextInt(width / 16) * 16;
        int y = rand.nextInt(height / 16) * 16;
        food.setX(x);
        food.setY(y);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
