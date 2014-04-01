package main;

/**
 * Created by Hung on 23/3/14.
 */
public class Alien extends Sprite {
    static String IMAGE_A = "/assets/invader64_1.png";
    static String IMAGE_B = "/assets/invader64_2.png";
    char image = 'A';
    int SHAKE_INTERVAL = 10;
    int count = 0;
    Alien(int x, int y) {
        super(IMAGE_A, x, y);
    }

    private void changeImage() {
        if (++ count % SHAKE_INTERVAL == 0) {
            if (image == 'A') {
                this.setPic(IMAGE_B);
                image = 'B';
            } else {
                this.setPic(IMAGE_A);
                image = 'A';
            }
        }
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        changeImage();
    }

    @Override
    public void moveRight() {
        super.moveRight();
        changeImage();
    }

    @Override
    public void moveLeft(int step) {
        super.moveLeft(step);
        changeImage();
    }

    @Override
    public void moveRight(int step) {
        super.moveRight(step);
        changeImage();
    }
}
