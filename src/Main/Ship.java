package main;

/**
 * Created by Hung on 23/3/14.
 */
public class Ship extends Sprite {
    static String IMAGE = "/assets/spaceship_1.png";

    static private int step = 30;

    Ship(int x, int y) {
        super(IMAGE, x, y);
    }

    public void moveLeft() {
        if (posX <= 0) return;
        posX -= step;
    }

    public void moveRight() {
        if (posX >= 1000) return;
        posX += step;
    }
}
