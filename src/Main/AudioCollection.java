package main;

import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AudioCollection {
    static private String WELCOME = "/assets/welcome.wav";
    static private String GAMEOVER = "/assets/gameover.wav";
    static private String INVASION = "/assets/invasion.wav";
    static private String LASER = "/assets/laser.wav";
    static private String BLOWUP = "/assets/blowup.wav";
    static private String DING = "/assets/ding.wav";
    static private String WARNING = "/assets/warning.wav";
    static private String BIGBANG = "/assets/bigbang.wav";
    static AudioClip WELCOME_CLIP =
            Applet.newAudioClip(AudioCollection.class.getResource(WELCOME));


    static public void welcome() {
        (new Thread(
                new Runnable() {
                    public void run() {
                        try {
                            WELCOME_CLIP.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        )).run();
    }
    static public void stopWelcome() {
        WELCOME_CLIP.stop();
    }
    static public void laser() {
        (new AudioPlayer(LASER)).start();
    }
    static public void invasion() {
        (new AudioPlayer(INVASION, true)).start();
    }

    static public void blowup() {
        (new AudioPlayer(BLOWUP)).start();
    }

    static public void ding() {
        (new AudioPlayer(DING)).start();
    }

    static public void warning() {
        (new AudioPlayer(WARNING)).start();
    }
    static public void bigbang() {
        (new AudioPlayer(BIGBANG)).start();
    }

}

