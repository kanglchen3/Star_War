/*
 * Project Name: EE2311 Project - Space War
 * Student Name:
 * Student ID:
 * 
 */

package main;

import game.GameConsole;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Sample design of a sprite
 * 
 * @author Van Ting
 */
public class Sprite extends Movable{

    // the size of the sprite
    public static final int w = 128;
    public static final int h = 128;
    // step size
    static private int step = 2;
            

    
    Sprite(String file, int x, int y) {
        super(file);
        this.posX = x;
        this.posY = y;
    }
    
    public void moveLeft() {
        posX -= step;
    }
    
    public void moveRight() {
        posX += step;
    }

    public void moveLeft(int step) {
        posX -= step;
    }

    public void moveRight(int step) {
        posX += step;
    }

    public void moveDown() {
        posY += 20;
    }

    static public int getStep() {
        return step;
    }

}
