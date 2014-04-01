package main;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 * Created by Hung on 23/3/14.
 */
public class AudioCollection {
    static private String WELCOME = "/assets/welcome.mp3";
    static private String GAMEOVER = "/assets/gameover.wav";
    static private String INVASION = "/assets/invasion.wav";
    static private String LASER = "/assets/laser.wav";
    static AudioClip[] LASER_CLIPs;
    static AudioClip INVASION_CLIP =
            Applet.newAudioClip(AudioCollection.class.getResource(INVASION));
    static AudioClip GAMEOVER_CLIP =
            Applet.newAudioClip(AudioCollection.class.getResource(GAMEOVER));
    private int i;
    Thread[] LASER_THREAD;
    Thread GAMEOVER_THREAD;
    Thread INVASION_THREAD;

    final int NUM = 3; //switch for laser buffer
    AudioCollection() {
        i = 0;
        LASER_THREAD = new Thread[NUM];
        LASER_CLIPs = new AudioClip[NUM];
        for (int j = 0; j < NUM; j ++) {
            LASER_CLIPs[j] = Applet.newAudioClip(AudioCollection.class.getResource(LASER));
            final int finalJ = j;
            LASER_THREAD[j] = new Thread(
                new Runnable() {
                    public void run() {
                        try {
                            LASER_CLIPs[finalJ].play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            );
        }

    }

    static private void play(final String file, boolean loop) {
        new Thread(
                new Runnable() {
                    public void run() {
                        try {
                            AudioClip clip = Applet.newAudioClip(AudioCollection.class.getResource(file));
                            clip.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }

    static public void welcome() {
        play(WELCOME, true);
    }
    public void laser() {
        int j = i ++;
//        System.out.print(j + "\n");
        try {
            LASER_THREAD[(j) % NUM].run();
        } catch (Exception ex) {

        }
    }
    public void invasion() {
        INVASION_THREAD = new Thread(
            new Runnable() {
                public void run() {
                    try {
                        INVASION_CLIP.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        );
        INVASION_THREAD.run();
    }

    public void stopInvasion() {
        INVASION_THREAD.interrupt();
    }
    public void gameover() {
        GAMEOVER_THREAD = new Thread(
            new Runnable() {
                public void run() {
                    try {
                        Applet.newAudioClip(AudioCollection.class.getResource(GAMEOVER)).play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        );
        GAMEOVER_THREAD.run();
    }

    public void stopGameover(){
        GAMEOVER_THREAD.interrupt();
    }
}
