package main;

import javax.swing.*;

/**
 * Created by Hung on 23/3/14.
 */
public class Bullet extends Movable{
    public boolean movingDown;
    private int speed;
    Bullet(int x, int y, boolean movingDown, int speed) {
        super("/assets/bullet.png");
        if (movingDown)
            super.setPic("/assets/bullet_a.png");
        this.posX = x;
        this.posY = y;
        this.movingDown = movingDown;
        this.speed = speed;
    }



    public void move() {
        if (movingDown) {
            this.posY += speed;
        } else {
            this.posY -= speed;
        }
    }
}
