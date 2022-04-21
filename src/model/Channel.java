package model;

import java.util.ArrayList;

/**
 * The class is responsible for keeping information about a channel
 * @author id18hll
 * @version 1.0
 */
public class Channel {

    private int id;
    private String name;
    private String tooltip;
    private ArrayList<Program> programs;
    private String audioURL;

    /**
     * Constructor for the class. Initializes the channel with the given parameters
     * @param id The id of the channel
     * @param name The name of the channel
     * @param tooltip The tooltip of the channel
     * @param audioURL The URL for audio of the channel
     */
    public Channel(int id, String name, String tooltip, String audioURL) {
        this.id = id;
        this.name = name;
        this.tooltip = tooltip;
        this.audioURL = audioURL;
        programs = new ArrayList<>();
    }

    /**
     * Constructor with no arguments
     */
    public Channel() {
        programs = new ArrayList<>();
    }

    /**
     * @return The id of the channel
     */
    public int getId() {
        return id;
    }

    /**
     * @param id  The id of the channel
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The name of the channel
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name of the channel
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param tooltip The tooltip of the channel
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * @param audioURL The URL for audio of the channel
     */
    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL + "?audioquality=hi";
    }

    /**
     * @return The URL for audio of the channel
     */
    public String getAudioURL() {
        return audioURL;
    }

    /**
     * @param programs An ArrayList off the channel's programs
     */
    public void setPrograms(ArrayList<Program> programs) {
        this.programs = programs;
    }

    /**
     * @return An ArrayList off the channel's programs
     */
    public ArrayList<Program> getPrograms() {
        return programs;
    }

    /**
     * Makes a deep clone of the programs in the channel
     * @return A dee clone of the programs in the channel.
     */
    public Channel deepClone() {
        Channel channel = new Channel(id, name, tooltip, audioURL);
        for (Program ch : programs) {
            channel.getPrograms().add(ch.deepClone());
        }
        return channel;
    }
}
