/*
 * Project Name: EE2311 Project - Space War
 * Student Name:
 * Student ID:
 * 
 */

package example4;

import game.GameConsole;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Sample design of a sprite
 * 
 * @author Van Ting
 */
public class Sprite {

    // the upper-left corner of the console, reference origin point
    protected int orgX = 0;
    protected int orgY = 0;
    // the size of the sprite
    protected int w = 64;
    protected int h = 64;
    // position of the sprite    
    protected int posX = 0;
    protected int posY = 0;
    // multiple frames        
    protected Image[] pic;
    protected float currentFrame = 0; 
    
    protected GameConsole console = GameConsole.getInstance();
    protected int globalFps, localFps;
    
          
    
    Sprite(String[] file, int x, int y, int fps) {
        pic = new Image[file.length];
        for(int i=0; i<file.length; i++)
            pic[i] = new ImageIcon(this.getClass().getResource(file[i])).getImage();       
        
        this.posX = x;
        this.posY = y;
        
        globalFps = console.getFrameRate();
        localFps = fps;
    }
    
    public void next(int i,int k) {
        currentFrame += (float)localFps/globalFps;
        if(currentFrame >= pic.length)
            currentFrame = 0;
    }
        
    public void display(int i, int k) {
        next(i,k);
        console.drawImage((int)(posX + orgX), (int)(posY + orgY), pic[(int)currentFrame]);
    }  

    /*
     * getters and setters 
     **************************************************************************/
    
    public int getFrameRate() {
        return localFps;
    }

    public void setFrameRate(int fps) {
        this.localFps = fps;
    }   

    public Image[] getPic() {
        return pic;
    }

    public void setPic(String[] file) {
        pic = new Image[file.length];
        for(int i=0; i<file.length; i++)
            pic[i] = new ImageIcon(this.getClass().getResource(file[i])).getImage();      
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
