package main;

/**
 * Created by Hung on 4/4/14.
 */
public class Wall extends Thing {
    int lives;
    public Wall(int x, int y) {
        super("/assets/wall.png");
        this.posX = x;
        this.posY = y;
        lives = 3;
    }

    public int broke() {
        lives --;
        AudioCollection.ding();
        switch (lives) {
            case 2:
                super.setPic("/assets/wall2.png");
                break;
            case 1:
                super.setPic("/assets/wall3.png");
        }
        return lives;
    }
}
