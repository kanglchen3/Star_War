package main;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 * Created by Hung on 24/3/14.
 */
public class SoundThread extends Thread{
    boolean running = true;
    AudioClip audioClip;
    public SoundThread(String file) {
        try  {
            audioClip = Applet.newAudioClip(this.getClass().getResource(file));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        start();
    }
    public void run() {
        while(running){
            audioClip.play();
        }
    }
    public void turnOff(){
        running = false;
    }
}
