package controller;

import model.AudioFilePlayer;
import view.Frame;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

/**
 * A SwingWorker for playing audio
 * @author id18hll
 * @version 1.0
 */
public class AudioWorker extends javax.swing.SwingWorker<Integer, Void> {

    private final String audioURL;
    private Frame frame;

    /**
     *  Constructor for the class.
     * @param audioURL The url of the audio file
     * @param frame The frame of the GUI
     */
    public AudioWorker(String audioURL, Frame frame) {
        this.audioURL = audioURL;
        this.frame = frame;
    }

    /**
     * Runs on another thread and plays the audio.
     * @return null
     * @throws MalformedURLException Is thrown if the URL is not valid
     */
    @Override
    protected Integer doInBackground() throws MalformedURLException {
        AudioFilePlayer musicPlayer = new AudioFilePlayer();
        musicPlayer.play(audioURL);
        return null;
    }

    /**
     * The method is only used to handle exceptions when audio is played.
     */
    @Override
    protected void done() {
        super.done();
        try {
            get();
        } catch (InterruptedException | ExecutionException e) {
            frame.showPopup("The audio could not be played" + "\n" + e.getMessage());
        }
    }
}
