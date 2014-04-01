/*
 * Project Name: EE2311 Project - Space War
 * Student Name:
 * Student ID:
 * 
 */
package main;

import game.GameConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Demo for the use of:
 *
 * 1. create and display game console 
 * 2. start a game loop 
 * 3. create and display aliens 
 * 4. detect key pressed 
 * 5. update screen at predefined interval 
 * 6. draw text to show score/time information
 *
 * @author Van Ting
 */
public class Main {
    AudioCollection aAudioCollection;
    int difficulty = 0;
    public static void main(String[] args) {
        Main game = new Main();
        game.startGame();
    }

    public void startGame() {
        GameConsole console = GameConsole.getInstance();
        console.setTitle("Space War");
        console.setFrameRate(30);
        // set custom background image
        console.setBackground("/assets/bg.png");
        // make the console visible
        console.show();
        // board dimension can be obtained from console
        int width = console.getBoardWidth();
        int height = console.getBoardHeight();
        aAudioCollection = new AudioCollection();
        while (true) {

            Image statusImage = null;
            GameLogic aGame = new GameLogic(difficulty);
            for (int i = 100; i <= 900; i = i + 50) {
                aGame.addSprite(new Alien(i, 100));
                aGame.addSprite(new Alien(i, 150));
                aGame.addSprite(new Alien(i, 200));
            }
            aGame.addSprite(new Ship(500, 550));

            //set time
            Timer aTimer = new Timer();
            aTimer.start();
            aAudioCollection.invasion();
            // enter the main game loop
            boolean gameover = false;
            while (!gameover) {

                // get whatever inputs
                int key = console.getPressedKey();
                if (key == KeyEvent.VK_LEFT) {
                    aGame.getSpaceship().moveLeft();     // action for left key
                } else if (key == KeyEvent.VK_RIGHT) {
                    aGame.getSpaceship().moveRight();    // action for right key
                } else if (key == KeyEvent.VK_ENTER) {
                    //handle restart game
                    statusImage = null;
                    difficulty = 0;
                    gameover = true;
                } else if (key == KeyEvent.VK_SPACE) {
                    //shot of ship
                    aGame.shootByShip();
                }
                aTimer.tick();//this is for tick the timer.
                if (aTimer.shouldMove(20, 0)) {
                    aGame.moveAliens();
                    aGame.moveBullets();
                    if (!aGame.checkShot()) {
                        statusImage = new ImageIcon(this.getClass().getResource("/assets/gameover.png")).getImage();
                    }
                    if (aGame.checkWon()) {
                        if (aTimer.setTimeout(1000)) {
                            difficulty++;
                            gameover = true;
                        }
                    }
                }
                if (aTimer.shouldMove(95, 15)) {
                    aGame.shootByAliens();
                }

                // refresh at the specific rate, default 25 fps
                if (console.shouldUpdate()) {
                    console.clear();
                    //display movables
                    for (Sprite t : aGame.getSprites()) {
                        t.display();
                    }
                    for (Bullet b : aGame.getBullets()) {
                        b.display();
                    }

                    console.drawText(600, 20, "[LEVEL]", new Font("Helvetica", Font.BOLD, 12), Color.white);
                    console.drawText(650, 20, String.valueOf(difficulty + 1), new Font("Helvetica", Font.PLAIN, 12), Color.white);

                    console.drawText(700, 20, "[TIME]", new Font("Helvetica", Font.BOLD, 12), Color.white);
                    console.drawText(750, 20, aTimer.getTimeString(), new Font("Helvetica", Font.PLAIN, 12), Color.white);

                    console.drawText(850, 20, "[SCORE]", new Font("Helvetica", Font.BOLD, 12), Color.white);
                    console.drawText(910, 20, String.valueOf(aGame.score), new Font("Helvetica", Font.PLAIN, 12), Color.white);

                    console.drawImage(0, 0, statusImage);


                    console.update();
                }

                // the idle time affects the no. of iterations per second which
                // should be larger than the frame rate
                // for fps at 25, it should not exceed 40ms
                console.idle(2);
            }
        }

    }
}
