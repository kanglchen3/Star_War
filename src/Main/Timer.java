/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package example4;


/**
 *
 * @author Van Ting <vanting at gmail.com>
 */
public class Timer {
    
    private long initTime = 0;
    
    public void start() {
        if(initTime == 0)
            initTime = System.currentTimeMillis();
    }
    
    public void stop() {
        initTime = 0;
    }
    
    public String getTimeString() {
        int time = (int)(System.currentTimeMillis() - initTime) / 1000;
        int hour = time / 3600;
        int minute = (time % 3600) / 60;
        int second = (time % 3600) % 60;
        
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

}
