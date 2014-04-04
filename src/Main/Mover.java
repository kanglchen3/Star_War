package main;

import game.GameConsole;

import javax.swing.*;
import java.awt.*;


public class Mover extends Thing{

    static protected int step;

    public Mover(String file) {
        super(file);
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
