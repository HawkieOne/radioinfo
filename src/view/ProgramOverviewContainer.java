package view;

import model.Channel;
import model.Program;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * The class is responsible for showing fetched programs to the user
 * @author id18hll
 * @version 1.0
 */
public class ProgramOverviewContainer extends JPanelContainer {
    private CustomJTable table;
    private JScrollPane scrollPaneTable;
    private JPanel progressPanel;
    private JLabel lastUpdate;
    private JProgressBar progressBar;

    /**
     * Constructor for the class. Initializes necessary components and adjusts their properties
     */
    public  ProgramOverviewContainer() {
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        table = new CustomJTable();
        scrollPaneTable = new JScrollPane(table);

        lastUpdate = new JLabel();
        progressBar = new JProgressBar();

        progressPanel = new JPanel();
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));

        progressPanel.add(progressBar);
        progressPanel.add(lastUpdate);
        centerX(lastUpdate);


        this.add(scrollPaneTable, BorderLayout.CENTER);
        this.add(progressPanel, BorderLayout.SOUTH);
    }

    /**
     * For a given index in a given ArrayList shows that channels programs
     * @param index Index of the channel to get programs from
     * @param channels List over all fetched channels
     */
    public void showProgramsForChannel(int index, ArrayList<Channel> channels) {
        table.clearTable();
        Channel channel = channels.get(index);
        System.out.println();
        for (int i = 0; i < channel.getPrograms().size(); i++) {
            Program program = channels.get(index).getPrograms().get(i);
            table.addRow(new Object[] {program});
        }
    }

    /**
     * Sets the max progress for the progress bar
     * @param max Max limit for the progress
     */
    public void setMaxProgress(int max) {
        progressBar.setMaximum(max);
    }

    /**
     * Shows the progress of the API fetching
     * @param progress The progress of the API fetching
     */
    public void setProgress(int progress) {
        progressBar.setValue(progress);
    }

    /**
     * Shows the status of the API fetching in text form
     */
    public void showUpdateStatus() {
        String timeHour = formatDigit(java.time.LocalTime.now().getHour());
        String timeMinute = formatDigit(java.time.LocalTime.now().getMinute());
        String timeSeconds = formatDigit(java.time.LocalTime.now().getSecond());
        lastUpdate.setText("Last updated at: " + timeHour + ":" + timeMinute + ":" + timeSeconds);
    }

    /**
     * Shows the status of the API fetching in text form
     */
    public void showUpdateStatus(String text) {
        lastUpdate.setText(text);
    }

    /**
     * Adds a zero before a one length digit for a given number
     * @param number The number to modify
     * @return The modified number
     */
    public String formatDigit(int number) {
        String numberString = String.valueOf(number);
        String newDigit = "0" + number;
        return (numberString.length() == 1) ? newDigit : numberString;
    }

    /**
     * @return Returns the table of the programs
     */
    public CustomJTable getTable() {
        return table;
    }
}
