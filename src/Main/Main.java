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

public class Main {
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
        AudioCollection.welcome();

        Image starwar = new ImageIcon(this.getClass().getResource("/assets/Star_Wars.jpg")).getImage();
        console.drawImage(0, 0, starwar);
        console.drawText(340, 500, "Press ENTER to start game, space to fire, arrows to move.", new Font("Helvetica", Font.BOLD, 14), Color.white);
        console.update();
        console.idle(10); // to buffer a little
        while (true) {
            int key = console.getPressedKey();
            System.out.println(key);
            if (key == KeyEvent.VK_ENTER)
                break;
        }
        AudioCollection.stopWelcome();
        AudioCollection.invasion();
        int previousScore = 0;
        while (true) {
            AudioCollection.warning();
            Image statusImage = null;
            GameLogic aGame = new GameLogic(difficulty, previousScore);


            //set time
            Timer aTimer = new Timer();
            aTimer.start();
            Image level_bg = new ImageIcon(this.getClass().getResource("/assets/level_bg.jpg")).getImage();
            while (!aTimer.setTimeout(2000)) {
                console.clear();
                console.drawImage(0, 0, level_bg);
                console.drawText(100, 200, "LEVEL" + String.valueOf(difficulty + 1), new Font("TimesRoman", Font.TRUETYPE_FONT, 50), Color.white);
                console.update();
            }
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
                    previousScore = 0;
                    gameover = true;
                } else if (key == KeyEvent.VK_SPACE) { //
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
                            previousScore = aGame.score;
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
                    aGame.displayAll();
                    console.drawText(600, 20, "[LEVEL]", new Font("Helvetica", Font.BOLD, 12), Color.white);
                    console.drawText(650, 20, String.valueOf(difficulty + 1), new Font("Helvetica", Font.PLAIN, 12), Color.white);
                    console.drawText(700, 20, "[TIME]", new Font("Helvetica", Font.BOLD, 12), Color.white);
                    console.drawText(750, 20, aTimer.getTimeString(), new Font("Helvetica", Font.PLAIN, 12), Color.white);
                    console.drawText(850, 20, "[SCORE]", new Font("Helvetica", Font.BOLD, 12), Color.white);
                    console.drawText(910, 20, String.valueOf(aGame.score), new Font("Helvetica", Font.PLAIN, 12), Color.white);
                    console.drawImage(0, 0, statusImage);
                    console.update();
                }
                console.idle(2);
            }
        }

    }
}
