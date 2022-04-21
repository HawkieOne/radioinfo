package view;

import javax.swing.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The class is responsible for showing details of a program to the user
 * @author id18hll
 * @version 1.0
 */
public class ProgramInformationContainer extends JPanelContainer {

    private JLabel containerTitle;
    private JLabel image;
    private JLabel title;
    private JTextPane description;
    private JScrollPane descriptionScrollPane;
    private JLabel time;
    private JButton listenButton;
    private JLabel srInfo;

    /**
     * Constructor for the class. Initializes necessary components and adjusts their properties
     */
    public ProgramInformationContainer() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createPadding(this, 0, 10, 0, 10);

        containerTitle = new JLabel();
        containerTitle.setText("Program");
        centerX(containerTitle);
        createPadding(containerTitle, 10,10,10,10);
        addChild(this, containerTitle);

        image = new JLabel();
        setupInformationComponent(image);

        title = new JLabel("Title");
        setupInformationComponent(title);

        time = new JLabel("Start Time");
        setupInformationComponent(time);

        description = new JTextPane();
        description.setBackground(null);
        description.setEditable(false);
        descriptionScrollPane = new JScrollPane(description);
        descriptionScrollPane.setBorder(null);
        descriptionScrollPane.setBackground(null);
        setupInformationComponent(descriptionScrollPane);

        listenButton = new JButton("Spela upp kanal");
        expandX(listenButton);
        setupInformationComponent(listenButton);

        srInfo = new JLabel();
        srInfo.setText("Besök sr.se för ytterligare info om kanalerna och dess program");
        setupInformationComponent(srInfo);
    }

    /**
     * Adjusts the properties of a component and adds it as a child to the JPanel
     * @param component The component to set the adjustments on
     */
    private void setupInformationComponent(JComponent component) {
        centerX(component);
        createPadding(component, 10,10,10,10);
        addChild(this, component);
        component.setVisible(false);
    }

    /**
     * @param icon Sets the image of the program
     */
    public void setImageIcon(ImageIcon icon) {
        image.setIcon(icon);
    }

    /**
     * @param text Sets the title of the program
     */
    public void setTitleText(String text) {
        title.setText(text);
    }

    /**
     * @param text Sets the description of the program
     */
    public void setDescriptionText(String text) {
        description.setText(text);
    }

    /**
     * Sets the time interval of the program
     * @param startDateTime The start time of the program
     * @param endDateTime The end time of the program
     */
    public void setTimeText(ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String startTime = formatter.format(startDateTime);
        String endTime = formatter.format(endDateTime);
        time.setText(startTime + " - " + endTime);
    }

    /**
     * Sets the details of the program visibel to the user
     */
    public void setProgramInformationVisible() {
        image.setVisible(true);
        title.setVisible(true);
        descriptionScrollPane.setVisible(true);
        time.setVisible(true);
        listenButton.setVisible(true);
        srInfo.setVisible(true);
    }

    /**
     * @return The button for listening to the current program on the current channel
     */
    public JButton getListenButton() {
        return listenButton;
    }
}

