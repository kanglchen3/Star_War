/*
 * Project Name: EE2311 Project - Space War
 * Student Name:
 * Student ID:
 * 
 */
package example4;


import game.GameConsole;
import java.awt.Color;
import java.awt.Font;
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
public class Demo4 {

    public static void main(String[] args) {
        Demo4 game = new Demo4();
        game.startGame();
    }

    public void startGame() {
        
        // initialize console by chaining calls, and set it visible
        GameConsole console = GameConsole.getInstance()
                .setTitle("Space War")
                .setFrameRate(50)
                .setBoardWidth(1000)
                .setBoardHeight(600)
                .setBackground("/assets/bg.png")
                .show();
        
        // start timer
        Timer timer = new Timer();
        timer.start();
        
        // board dimension can be obtained from console
        int width = console.getBoardWidth();
        int height = console.getBoardHeight();
        
        
        Alien invader[][] = new Alien[12][4];
       for(int k = 0;k<4;k++){
               for(int i=0;i<12;i++){
            invader[i][k] = new Alien(new String[] {"/assets/invader64_1.png","/assets/invader64_2.png"}, 100+50*i, 200-50*k, 5, 50);
        }
       }
        Spaceship ship = new Spaceship(new String[] {"/assets/spaceship_1.png"}, 480, 500, 1);

        // enter the main game loop
        while (true) {

            // get whatever inputs
            int key = console.getPressedKey();
            if (key == KeyEvent.VK_LEFT) {
                ship.moveLeft();     // action for left key
            } else if (key == KeyEvent.VK_RIGHT) {
                ship.moveRight();    // action for right key
            } else if (key == KeyEvent.VK_ENTER) {
                // action for enter key
            } else if (key == KeyEvent.VK_SPACE) {
                // action for space key
            }

            // refresh at the specific rate
            if (console.shouldUpdate()) {
                console.clear();

                console.drawText(700, 20, "[TIME]", new Font("Helvetica", Font.BOLD, 12), Color.white);
                console.drawText(750, 20, timer.getTimeString(), new Font("Helvetica", Font.PLAIN, 12), Color.white);

                console.drawText(850, 20, "[SCORE]", new Font("Helvetica", Font.BOLD, 12), Color.white);
                console.drawText(910, 20, "220", new Font("Helvetica", Font.PLAIN, 12), Color.white);
               for(int k =0;k<4;k++){
                for(int i=0;i<12;i++){
                    invader[i][k].display(i,k);
                }
               }
                ship.display(0,0);
                console.update();
            }

            // the idle time affects the no. of iterations per second which 
            // should be larger than the frame rate
            // for fps at 25, it should not exceed 40ms
            console.idle(5);
        }

    }
}
