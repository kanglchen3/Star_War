package main;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Hung on 23/3/14.
 */
public class GameLogic {
    public int score;
    static private int BOUND_LEFT = 50;
    static private int BOUND_RIGHT = 950;
    private int direction = 1; // -1 :  left, 1 : right, 0 : horizontal no movement

    private Collection<Sprite> sprites;
    private Collection<Bullet> bullets;
    private Collection<Wall> walls;

    private Timer aTimer;

    private int alienStep = 20;

    AudioCollection aAudioCollection;

    GameLogic(int difficulty, int previousScore){
        sprites = new ArrayList<Sprite>();
        bullets = new ArrayList<Bullet>();
        walls = new ArrayList<Wall>();
        aAudioCollection = new AudioCollection();
        aTimer = new Timer();
        aTimer.reset();
        alienStep = difficulty * 1 + 2;
        //generate things
        for (int i = 100; i <= 900; i = i + 50) {
            addSprite(new Alien(i, 100));
            addSprite(new Alien(i, 150));
            addSprite(new Alien(i, 200));
        }
        addSprite(new Ship(500, 550));
        for (int j = 25; j < 1000; j += 50) {
            addWall(new Wall(j, 500));
        }
        score = previousScore;//restore the score
    }

    public void addSprite(Sprite... s) {
        for (Sprite _s : s)
            sprites.add(_s);
    }

    public void addWall(Wall... w) {
        for (Wall _w : w)
            walls.add(_w);
    }

    public void removeSprite(Sprite... s) {
        for (Sprite _s : s)
            sprites.remove(_s);
    }

    public Ship getSpaceship() {
        for (Sprite s : sprites) {
            if (s instanceof Ship) {
                return (Ship)s;
            }
        }
        return new Ship(0, 0);
    }

    public void moveAliens() {
        boolean movingDown = false;
        int max_left = BOUND_RIGHT + 1;
        int max_right = BOUND_LEFT - 1;
        for (Sprite s : sprites) {
            if (s instanceof Alien) {
                max_left = (s.getPosX() < max_left)? s.getPosX() : max_left;
                max_right = (s.getPosX() > max_right)? s.getPosX() : max_right;
            }
        }
        if (max_left - Sprite.getStep() < BOUND_LEFT && max_right + Sprite.getStep() > BOUND_RIGHT) {
            direction = 0;
        } else if (max_left - Sprite.getStep() < BOUND_LEFT) {
            direction = 1;
            movingDown = true;
        } else if (max_right + Sprite.getStep() > BOUND_RIGHT) {
            direction = -1;
            movingDown = true;
        } else {
            //keep original direction
        }

        //start to move
        for (Sprite s : sprites) {
            if (s instanceof Alien) {
                switch (direction) {
                    case -1:
                        s.moveLeft(alienStep);
                        break;
                    case 1:
                        s.moveRight(alienStep);
                        break;
                }
                if (movingDown)
                    s.moveDown();
            }
        }
    }

    public void moveBullets() {
        Collection<Bullet> rubish = new ArrayList<Bullet>();
        for (Bullet i : bullets) {
            i.move();
            if (i.getPosY() < 0 || i.getPosY() > 600) {
                rubish.add(i);
            }
        }
        bullets.removeAll(rubish); //remove the bullet flow out of the screen
        //for debug: print all locations
//        System.out.print("Bullets: " + bullets.size() + " ");
//        for (Bullet b : bullets) {
//            System.out.print("(" + b.getPosX() + ", " + b.getPosY() + ") ");
//        }
//        System.out.print("\n Sprites: " + sprites.size() + " ");
//        for (Sprite s: sprites) {
//            System.out.print("(" + s.getPosX() + ", " + s.getPosY() + ") ");
//        }
//        System.out.print("\n");
    }

    private void removeBullets(Bullet ... b) {
        for (Bullet _b : b) {
            bullets.remove(_b);
        }
    }

    public void shootByShip() {
        if (aTimer.shouldShot(160)) {
            aTimer.reset();
        } else {
            return;
        }
        for (Sprite s : sprites){
            if (s instanceof Ship) {
                int x = this.getSpaceship().getPosX();
                int y = this.getSpaceship().getPosY();
                bullets.add(new Bullet(x, y, false, 8));
                aAudioCollection.laser();
            }
        }

    }

    public void shootByAliens() {
        for (Sprite s : sprites) {
            if (s instanceof Alien) {
                if (Math.random() <= 0.01) {
                    int x = s.getPosX();
                    int y = s.getPosY();
                    bullets.add(new Bullet(x, y, true, 4));
//                    aAudioCollection.laser();
                    return;
                }
            }
        }
    }

    public boolean checkWon() {
        if (sprites.size() == 1) {
            for (Sprite s : sprites) {
                if (s instanceof Ship) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkShot() {
        //return false if ship died
        Collection<Sprite> rubish_s = new ArrayList<Sprite>();
        Collection<Bullet> rubish_b = new ArrayList<Bullet>();
        Collection<Wall> rubbish_w = new ArrayList<Wall>();
        try {
            for (Sprite s : sprites) {
                if (s instanceof Ship) continue;
                for (Bullet b : bullets) {
                    if (b.movingDown) continue;
                    int distance_x = Math.abs(s.getPosX() - b.getPosX());
                    int distance_y = Math.abs(s.getPosY() - b.getPosY());
                    if (distance_x <= s.getPicWidth() / 2 - 6 && distance_y <= s.getPicHeight() / 2 - 6) {
                        rubish_b.add(b);
                        rubish_s.add(s);
                    }
                    if (s.getPosY() > 450) { // hit wall
                        for (Wall w : walls) {
                            distance_x = Math.abs(s.getPosX() - w.getPosX());
                            distance_y = Math.abs(s.getPosY() - w.getPosY());
                            if (distance_x <= s.getPicWidth() / 2 + w.getPicWidth() / 2 - 6
                                    && distance_y <= s.getPicHeight() / 2 + w.getPicHeight() / 2) {
                                rubbish_w.add(w);
                            }
                        }
                    }
                    if (s.getPosY() > 600) {
                        return false;
                    }
                }
            }
            for (Sprite s : sprites) {
                if (s instanceof Alien) continue;
                for (Bullet b : bullets) {
                    if (!b.movingDown) continue;
                    int distance_x = Math.abs(s.getPosX() - b.getPosX());
                    int distance_y = Math.abs(s.getPosY() - b.getPosY());
                    if (distance_x <= s.getPicWidth() / 2 - 6 && distance_y <= s.getPicHeight() / 2 - 6) {
                        rubish_b.add(b);
                        rubish_s.add(s);
                        AudioCollection.bigbang();
                    }
                }
            }
            for (Wall w : walls) {
                for (Bullet b : bullets) {
                    if (!b.movingDown) continue;
                    int distance_x = Math.abs(b.getPosX() - w.getPosX());
                    int distance_y = Math.abs(b.getPosY() - w.getPosY());
                    if (distance_x <= w.getPicWidth() / 2 && distance_y <= w.getPicHeight() / 2) {
                        if (w.broke() == 0)
                            rubbish_w.add(w);
                        rubish_b.add(b);
                    }
                }
            }
        } catch (Exception ex) {

        }
        try {
            if (rubish_s.size() > 0) {
                sprites.removeAll(rubish_s);
                AudioCollection.blowup();
            }
            bullets.removeAll(rubish_b);
            walls.removeAll(rubbish_w);
        }catch (Exception e) {

        }
        for (Sprite s : rubish_s) {
            if (s instanceof Ship) {
                return false;
            } else {
                score += 1;
            }
        }
        return true;
    }

    public void displayAll() {
        for (Sprite s : sprites) {
            s.display();
        }
        for (Bullet b : bullets) {
            b.display();
        }
        for (Wall w : walls) {
            w.display();
        }
    }
}
