package view;

import model.Channel;
import model.Program;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The class is responsible for showing the GUI to the user and all the necessary information
 * @author id18hll
 * @version 1.0
 */
public class Frame extends JFrame {

    private JFrame frame;
    private JPanel mainPanel;
    private MenuBar menuBar;
    private JPanel overview;
    private ProgramInformationContainer programInfo;
    private ProgramOverviewContainer programsOverview;
    private ChannelOverviewContainer channelsOverview;

    private ArrayList<Channel> channelsList;
    private int currentChannel;

    /**
     * Constructor for the class. Initializes necessary components and adjusts their properties
     */
    public Frame() {
        initializeComponents();
        setupLayouts();
        setupRelations();

        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setMinimumSize(new Dimension(800, 400));
        frame.setPreferredSize(new Dimension(1000, 800));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        channelsList = new ArrayList<>();
    }

    /**
     * Initializes necessary components
     */
    private void initializeComponents() {
        frame = new JFrame("RadioInfo");
        menuBar = new MenuBar();
        mainPanel = new JPanel();
        overview = new JPanel();
        programsOverview = new ProgramOverviewContainer();
        programInfo = new ProgramInformationContainer();
        channelsOverview = new ChannelOverviewContainer();
        programsOverview.setMaxProgress(52);
    }

    /**
     * Sets layouts for components
     */
    private void setupLayouts() {
        frame.setLayout(new BorderLayout());
        mainPanel.setLayout(new BorderLayout());
        overview.setLayout(new BorderLayout());
    }

    /**
     * Sets relations between components
     */
    private void setupRelations() {
        frame.setJMenuBar(menuBar);
        frame.add(mainPanel);
        mainPanel.add(overview, BorderLayout.WEST);
        mainPanel.add(programInfo, BorderLayout.CENTER);
        overview.add(channelsOverview, BorderLayout.NORTH);
        overview.add(programsOverview, BorderLayout.CENTER);
    }

    /**
     * Shows a given channel in the dropdown of channels
     * @param channel Channels to add to the dropdown
     */
    public void showChannelInDropdown(Channel channel) {
        channelsList.add(channel);
        channelsOverview.addChannel(channel);
    }

    /**
     * Updates a channel in the dropdown menu of channel
     * @param channel Channel with updated information
     */
    public void refreshChannelInDropdown(Channel channel) {
        for (int i = 0; i < channelsList.size(); i++) {
            if (channel.getId() == channelsList.get(i).getId()) {
                channelsList.set(i, channel);
            }
        }
    }

    /**
     * Shows the programs for a given channel
     * @param index Index of the channel
     */
    public void showProgramsForChannel(int index) {
        currentChannel = index;
        programsOverview.showProgramsForChannel(index, channelsList);
    }

    /**
     * Shows detailed information about a given program
     * @param index Index of the program
     */
    public void showInfoForProgram(int index) {
        Program program = channelsList.get(currentChannel).getPrograms().get(index);
        ImageIcon imageIcon = null;
        try {
            imageIcon = new ImageLoader().getProgramImageIcon(program.getImageURL());
        } catch (IOException e) {
            showPopup("Could not load image" + "\n" + e.toString());
        }
        programInfo.setImageIcon(imageIcon);
        programInfo.setTitleText(program.getTitle());
        programInfo.setDescriptionText(program.getDescription());
        programInfo.setTimeText(program.getStartTime(), program.getEndTime());
        programInfo.setProgramInformationVisible();
    }

    /**
     * Shows a popup with text
     * @param exception Text to show in the popup
     */
    public void showPopup(String exception) {
        JOptionPane.showMessageDialog(frame, exception, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Sets the GUI to visible so the user can see it.
     */
    public void showUI(){
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * @return The dropdown menu of channels
     */
    public JComboBox<String> getChannelsDropdown() {
        return channelsOverview.getChannelsDropdown();
    }

    /**
     * @return The table of programs
     */
    public JTable getProgramTable() {
        return programsOverview.getTable();
    }

    /**
     * @return Menu item for updating the data
     */
    public JMenuItem getRefreshItem() {
        return menuBar.getRefreshItem();
    }

    /**
     * @return Menu item for quitting the application
     */
    public JMenuItem getQuitItem() {
        return menuBar.getQuitItem();
    }

    /**
     * @return Menu item for changing the theme to the system theme
     */
    public JMenuItem getSystemThemeItem() {
        return menuBar.getSystemThemeItem();
    }

    /**
     * @return Menu item for changing the theme to the standard theme
     */
    public JMenuItem getCrossPlatformThemeItem() {
        return menuBar.getCrossPlatformThemeItem();
    }

    /**
     * @return Menu item for changing the theme to the motif theme
     */
    public JMenuItem getMotifThemeItem() {
        return menuBar.getMotifThemeItem();
    }

    /**
     * @return Button for starting the audio of a channel
     */
    public JButton getListenButton() {
        return programInfo.getListenButton();
    }

    /**
     * @return The container for the programs
     */
    public ProgramOverviewContainer getProgramsOverview() {
        return programsOverview;
    }

    /**
     * @return The selected channel
     */
    public Channel getActiveChannel() {
        return channelsList.get(currentChannel);
    }

    /**
     * @return The main frame of the application
     */
    public JFrame getFrame() {
        return frame;
    }
}
