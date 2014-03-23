/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example4;

/**
 *
 * @author vanting
 */
public class Spaceship extends Sprite {
    
    // step size
    protected int step = 10;

    Spaceship(String[] file, int x, int y, int fps) {
        super(file, x, y, fps);
    }
    
    public void moveLeft() {
        posX -= step;
    }

    public void moveRight() {
        posX += step;
}
    
    
    
    
}
