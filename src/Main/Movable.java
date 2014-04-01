package main;

import game.GameConsole;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hung on 23/3/14.
 */
public class Movable {
    // the upper-left corner of the console, reference origin point
    public static final int orgX = 0;
    public static final int orgY = 0;

    // position of the mover
    protected int posX = 0;
    protected int posY = 0;

    protected Image pic;

    protected int picWidth;
    protected int picHeight;

    public Movable(String file) {
        this.pic = new ImageIcon(this.getClass().getResource(file)).getImage();
        this.picWidth = this.pic.getWidth(null);
        this.picHeight = this.pic.getWidth(null);
    }

    public void display() {
        GameConsole.getInstance().drawImage((int)(posX + orgX - picWidth / 2), (int)(posY + orgY - picHeight / 2), pic);
    }

    public int getPicWidth() {
        return picWidth;
    }
    public int getPicHeight() {
        return picHeight;
    }

    public Image getPic() {
        return pic;
    }

    public void setPic(String file) {
        this.pic = new ImageIcon(this.getClass().getResource(file)).getImage();
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
