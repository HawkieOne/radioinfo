package controller;

import model.Channel;
import model.ChannelManager;
import model.DataFetchingException;
import view.Frame;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * A SwingWorker for fetching API data
 * @author id18hll
 * @version 1.0
 */
public class APIWorker extends javax.swing.SwingWorker<Void, Channel> {

    private Frame frame;
    private boolean refresh;
    private int count;
    public APIWorker(Frame frame, boolean refresh) {
        this.frame = frame;
        this.refresh = refresh;
        count = 0;
    }

    /**
     * Runs on another thread and fetches the API data, both channels and programs. Publishes the fetched data every
     * time a channel and its programs is downloaded.
     * @return An ArrayList of the channels and programs
     * @throws SAXException Is thrown when the parser can not read the given file
     * @throws ParserConfigurationException Is thrown when the parser lacks necessary information
     * @throws DataFetchingException Is thrown when the data can not be fetched e.g. no internet connection
     */
    @Override
    protected Void doInBackground() throws SAXException, ParserConfigurationException,
            DataFetchingException {
        ChannelManager channelManager = new ChannelManager();
        channelManager.loadChannels();
        int channelCount = channelManager.getAmountOfChannels();
        for(int i = 0; i < channelCount; i++) {
            publish(channelManager.buildChannel(i));
        }
        return null;
    }

    /**
     * Handles data published in doInBackground(). Makes a deep clone of the published channel/channels and sends
     * the new channel to the view.
     * @param chunks A list of the published things in doInBackground().
     */
    @Override
    protected void process(List<Channel> chunks) {
        super.process(chunks);
        for (Channel channel : chunks) {
            count++;
            Channel channelDeepClone = channel.deepClone();
            if (!refresh) {
                frame.showChannelInDropdown(channelDeepClone);
            } else {
                frame.refreshChannelInDropdown(channelDeepClone);
            }
            frame.getProgramsOverview().showUpdateStatus("Refreshing: " + channel.getName());
            frame.getProgramsOverview().setProgress(count);
        }
    }

    /**
     * Tells the view that the API is done fetched
     */
    @Override
    protected void done() {
        super.done();
        try {
            get();
            frame.getProgramsOverview().showUpdateStatus();
            count = 0;
        } catch (InterruptedException | CancellationException e) {
            frame.showPopup("The data could not be fetched from the API" + "\n" +
                    e.getMessage());
        } catch (ExecutionException e) {
            if (e.getCause() instanceof DataFetchingException) {
                frame.showPopup("A connection to the API could not be established.");
            } else {
                frame.showPopup("The data could not be fetched from the API");
            }
        }
    }
}
