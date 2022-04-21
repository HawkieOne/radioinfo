package view;

import model.Channel;

import javax.swing.*;
import java.awt.*;

/**
 * The class is responsible for showing fetched channels to the user
 * @author id18hll
 * @version 1.0
 */
public class ChannelOverviewContainer extends JPanelContainer {

    private JComboBox<String> channelsDropDown;
    private JLabel title;

    /**
     * Constructor for the class. Initializes necessary components and adjusts their properties
     */
    public ChannelOverviewContainer() {
        this.setLayout(new BorderLayout());
        createPadding(this,0, 10, 10, 10);

        title = new JLabel("Kanaler");
        centerX(title);
        createPadding(title, 0, 0, 5, 0);
        addChild(this, title);
        channelsDropDown = new JComboBox<>();
        expandX(channelsDropDown);
        addChild(this, channelsDropDown);
    }

    /**
     * Adds a given channel to the dropdown menu
     * @param channel Channel to add
     */
    public void addChannel(Channel channel) {
        channelsDropDown.addItem(channel.getName());
    }

    /**
     * @return The dropdown menu of the channels
     */
    public JComboBox<String> getChannelsDropdown() {
        return channelsDropDown;
    }

}
