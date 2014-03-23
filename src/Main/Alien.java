/*
 * Project Name: EE2311 Project - Space War
 * Student Name:
 * Student ID:
 * 
 */

package example4;

/**
 *
 * @author vanting
 */
public class Alien extends Sprite {
        
    private int speed = 50;      // pixel per second
    private boolean forward = true;
    
    Alien(String[] file, int x, int y, int fps, int speed) {
        super(file, x, y, fps);
        this.speed = speed;
    }

    @Override
    public void next(int i,int k) {
        super.next(i,k);       // update frame
        
        if(forward)
            posX += speed/globalFps;
        else
            posX -= speed/globalFps;
        
        if(posX + 610 -50*i > console.getBoardWidth() || posX < 50*i)
            forward = !forward;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
}
