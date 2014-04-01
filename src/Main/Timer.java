package main;

/**
 * Created by Hung on 23/3/14.
 */
public class Timer {

    private long initTime = 0;
    private int count = 0;
    private long timeoutInit = 0;

    public void start() {
        if(initTime == 0)
            initTime = System.currentTimeMillis();
    }

    public void stop() {
        initTime = 0;
    }

    public void tick() {
        count ++;
    }
    public boolean shouldMove(int interval, int point) {
        if (count % interval == point) {
            return true;
        } else {
            return false;
        }
    }

    public void reset() {
        initTime = System.currentTimeMillis();
    }

    public boolean shouldShot(int interval) {
        if (System.currentTimeMillis() - initTime > interval) {
            return true;
        } else {
            return false;
        }
    }

    public String getTimeString() {
        int time = (int)(System.currentTimeMillis() - initTime) / 1000;
        int hour = time / 3600;
        int minute = (time % 3600) / 60;
        int second = (time % 3600) % 60;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public boolean setTimeout(int i) {
//        can only exits one at one time;
        if (timeoutInit == 0) {
            timeoutInit = System.currentTimeMillis();
        } else {
            if (System.currentTimeMillis() - timeoutInit >= i) {
                timeoutInit = 0;
                return true;
            }
        }
        return false;
    }

}