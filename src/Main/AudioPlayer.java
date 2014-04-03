package main;

import javax.sound.sampled.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Hung on 3/4/14.
 */
class AudioPlayer  extends Thread{
    private String[] filenames;
    private boolean loop;
    private AudioInputStream audioStream = null;
    private AudioFormat audioFormat = null;
    private SourceDataLine sourceLine = null;

    private AtomicBoolean stopFlag = new AtomicBoolean();
    private volatile float volume_dB = 0.0f;

    public AudioPlayer(){

    }
    public AudioPlayer(String filename)
    {
        String[] _filenames = {filename};
        this.filenames =_filenames;
    }

    public AudioPlayer(String[] filenames)
    {
        this.filenames = filenames;
    }

    public AudioPlayer(String filename, boolean loop)
    {
        String[] _filenames = {filename};
        this.filenames =_filenames;
        this.loop = loop;
    }

    public AudioPlayer(String[] filenames, boolean loop)
    {
        this.filenames = filenames;
        this.loop = loop;
    }

    public void playSound() throws FileNotFoundException, UnsupportedAudioFileException, IOException, LineUnavailableException
    {

        do {
            for (String filename : filenames) {
                final AudioInputStream in = AudioSystem.getAudioInputStream(this.getClass().getResource(filename));

                final AudioFormat baseFormat = in.getFormat();
                audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                        baseFormat.getSampleRate(),
                        16,
                        baseFormat.getChannels(),
                        baseFormat.getChannels() * 2,
                        baseFormat.getSampleRate(),
                        false);
                //System.out.println("Channels : " + baseFormat.getChannels());
                audioStream = AudioSystem.getAudioInputStream(audioFormat, in);
                final byte[] data = new byte[4096];
                try {
                    SourceDataLine res = null;
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                    res = (SourceDataLine) AudioSystem.getLine(info);
                    res.open(audioFormat);
                    sourceLine = res;
                    sourceLine.start();
                    int nBytesRead = 0;// nBytesWritten = 0;
                    while ((nBytesRead != -1)) {
                        nBytesRead = audioStream.read(data, 0, data.length);
                        if (nBytesRead != -1) {
                            if (sourceLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                                ((FloatControl) sourceLine.getControl(FloatControl.Type.MASTER_GAIN)).setValue(volume_dB);
                            }
                            sourceLine.write(data, 0, nBytesRead);
                            //nBytesWritten = sourceLine.write(data, 0, nBytesRead);
                            //System.out.println("... -->" + data[0] + " bytesWritten:" + nBytesWritten);
                        }
                    }
                    System.out.println("Done ...");

                    // Stop
                    sourceLine.drain();
                    sourceLine.stop();
                    sourceLine.close();
                    audioStream.close();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                in.close();

            }
        } while (loop);
    }


    @Override
    public void run()
    {
        try {
            playSound();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}