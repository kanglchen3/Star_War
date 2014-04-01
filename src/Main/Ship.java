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

    @Override
    public void moveLeft() {
        posX -= step;
    }

    @Override
    public void moveRight() {
        posX += step;
    }
}
