package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Player {

    private ArrayList<Rectangle> snake;
    private int w, h, xSpeed, ySpeed;
    private boolean alive;

    public Player(int w, int h) {
        this.w = w;
        this.h = h;
        reset();
    }

    public void update(int dx, int dy) {
        for(int i = snake.size() - 1; i >= 0; i--) {
            Rectangle r = snake.get(i);
            if(i == 0) {
                if(dx == -xSpeed) dx = xSpeed;
                if(dy == -ySpeed) dy = ySpeed;
                r.setX(r.getX() + dx);
                r.setY(r.getY() + dy);
            } else {
                r.setX(snake.get(i - 1).getX());
                r.setY(snake.get(i - 1).getY());
            }
        }

        if(snake.size() > 3) {
            for (int i = 3; i < snake.size(); i++) {
                if (snake.get(0).intersects(snake.get(i).getBoundsInLocal())) {
                    alive = false;
                    break;
                }
            }
        }
        if(snake.get(0).getX() <= -1 || snake.get(0).getX() + 15 >= w) alive = false;
        if(snake.get(0).getY() <= -1 || snake.get(0).getY() + 15 >= h) alive = false;

        xSpeed = dx;
        ySpeed = dy;
    }

    public void grow() {
        Rectangle rect = snake.get(snake.size() - 1);
        Rectangle r = new Rectangle(rect.getX(), rect.getY(), 16, 16);
        r.setFill(Color.WHITE);
        snake.add(r);
    }

    public void reset() {
        alive = true;
        xSpeed = 0;
        ySpeed = 0;
        snake = new ArrayList<>();
        Rectangle start = new Rectangle(64, 64, 16, 16);
        start.setFill(Color.WHITE);
        snake.add(start);
    }

    public ArrayList<Rectangle> getSnake() {
        return snake;
    }

    public boolean isAlive() {
        return alive;
    }

}
