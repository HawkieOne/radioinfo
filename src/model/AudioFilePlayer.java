package model;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

/**
 * Class for playing audio from an URL
 * THE CODE IS NOT MINE AND I DO NOT CLAIM AUTHORSHIP OVER THE CODE
 * IT IS TAKEN FROM THE URL BENEATH
 * @author https://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java/17737483#17737483
 * @version 1.0
 */
public class AudioFilePlayer {

    /**
     * Plays audio from an URL
     * @param audioURL URL to play audio from
     * @throws MalformedURLException Is thrown if the URl is not valid
     */
    public void play(String audioURL) throws MalformedURLException {

        URL url;
        url = new URL(audioURL);

        try (final AudioInputStream in = getAudioInputStream(url)) {

            final AudioFormat outFormat = getOutFormat(in.getFormat());
            final Info info = new Info(SourceDataLine.class, outFormat);

            try (final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                if (line != null) {
                    line.open(outFormat);
                    line.start();
                    stream(getAudioInputStream(outFormat, in), line);
                    line.drain();
                    line.stop();
                }
            }

        } catch (UnsupportedAudioFileException
                | LineUnavailableException
                | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Gets the out format of the audio
     * @param inFormat The format which the audio is read in
     * @return The format for which the audio is played out in
     */
    private AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();

        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }

    /**
     * Reads data from pne stream and writes to another
     * @param in The stream to read from
     * @param line The stream to write to
     * @throws IOException Is thrown an input or output error occurs
     */
    private void stream(AudioInputStream in, SourceDataLine line) throws IOException {
        final byte[] buffer = new byte[4096];
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }
}