package view;

import model.Program;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The class is responsible for showing information of the program in the program table
 * @author id18hll
 * @version 1.0
 */
public class TableCellComponent extends JPanel {
    private Program program;
    private JLabel titleLabel;
    private JLabel timeLabel;
    private JPanel panel;
    private LocalDateTime dateTime;

    /**
     * Constructor for the class. Initializes necessary components and adjusts their properties
     */
    public TableCellComponent() {
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(0, 5, 0, 5));
        panel = new JPanel(new BorderLayout());
        titleLabel = new JLabel();
        timeLabel = new JLabel();

        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.getPreferredSize().height));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(timeLabel, BorderLayout.SOUTH);
        this.add(panel, BorderLayout.WEST);
        dateTime = java.time.LocalDateTime.now();
    }

    /**
     * Updates the data in the class
     * @param program The program fow which information is shown
     * @param isSelected Status for the selected cell in the table
     * @param table The table of the programs
     */
    public void updateData(Program program, boolean isSelected, JTable table) {
        this.program = program;

        titleLabel.setText(program.getTitle());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String startTime = formatter.format(program.getStartTime());
        String endTime = formatter.format(program.getEndTime());
        String day = program.getStartTime().getDayOfWeek().toString();
        day = translateDayToSwedish(day);
        timeLabel.setText(day + " " + startTime + " - " + endTime);

        this.setToolTipText(program.getDescription());
        updateColors(isSelected, table);
    }

    /**
     * Updates the colors for the current cell in the table
     * @param isSelected Status for the selected cell in the table
     * @param table The table of the programs
     */
    private void updateColors(boolean isSelected, JTable table) {
        if (isSelected) {
            setBackgroundColor(Color.decode("#D8DEE9"));
            setTextColor(Color.BLACK);
        } else {
            if (dateTime.isAfter(program.getEndTime().toLocalDateTime())) {
                if (UIManager.getLookAndFeel().getID().equals("Metal")){
                    setBackgroundColor(table.getBackground());
                    setTextColor(Color.GRAY);
                } else if (UIManager.getLookAndFeel().getID().equals("Motif")) {
                    setBackgroundColor(table.getBackground());
                    setTextColor(Color.DARK_GRAY);
                } else {
                    setBackgroundColor(Color.decode("#4C566A"));
                    setTextColor(Color.BLACK);
                }
            } else if (dateTime.isAfter(program.getStartTime().toLocalDateTime())
                        && dateTime.isBefore(program.getEndTime().toLocalDateTime())) {
                setBackgroundColor(Color.decode("#81A1C1"));
                setTextColor(Color.BLACK);
            } else {
                if (UIManager.getLookAndFeel().getID().equals("Metal")
                        || UIManager.getLookAndFeel().getID().equals("Motif")){
                    setBackgroundColor(table.getBackground());
                    setTextColor(Color.BLACK);
                } else {
                    setBackgroundColor(table.getBackground());
                    setTextColor(Color.BLACK);
                }
            }
        }
    }

    /**
     * Sets the background color of the current cell in the table
     * @param color Color to set
     */
    private void setBackgroundColor(Color color) {
        panel.setBackground(color);
        panel.getParent().setBackground(color);
    }

    /**
     * Sets the text color of the current cell in the table
     * @param color Color to set
     */
    private void setTextColor(Color color) {
        titleLabel.setForeground(color);
        timeLabel.setForeground(color);
    }

    /**
     * Translates the days of the week from english to swedish
     * @param day Day to translate
     * @return The translated day in swedish
     */
    private String translateDayToSwedish(String day) {
        switch (day) {
            case "MONDAY" -> day = "Måndag";
            case "TUESDAY" -> day = "Tisdag";
            case "WEDNESDAY" -> day = "Onsdag";
            case "THURSDAY" -> day = "Torsdag";
            case "FRIDAY" -> day = "Fredag";
            case "SATURDAY" -> day = "Lördag";
            case "SUNDAY" -> day = "Söndag";
        }
        return day;
    }
}
