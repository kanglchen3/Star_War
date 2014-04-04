/*
 * Project Name: EE2311 Project - Space War
 * Student Name:
 * Student ID:
 * 
 */

package main;

public class Sprite extends Mover {

    // the size of the sprite
    public static final int w = 128;
    public static final int h = 128;
    // step size

    protected int step = 2;
            

    
    Sprite(String file, int x, int y) {
        super(file);
        this.posX = x;
        this.posY = y;
    }
    



}
