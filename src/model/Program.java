package model;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The class is responsible for keeping information about a program
 * @author id18hll
 * @version 1.0
 */
public class Program {

    private String title;
    private String description;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String imageURL;

    /**
     * Constructor for the class. Initializes the program with the given properties.
     * @param title Title of the program.
     * @param description  Description of the program.
     * @param startTime Start time of the program.
     * @param endTime  End time of the program.
     */
    public Program(String title, String description, String startTime, String endTime, String imageURL) {
        this.title = title;
        this.description = description;
        this.startTime = transformToDate(startTime);
        this.endTime = transformToDate(endTime);
        this.imageURL = imageURL;
    }

    public Program(String title, String description, ZonedDateTime startTime, ZonedDateTime endTime) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Transforms a string date to a ZonedDateTime date. The method also transforms the date to the correct time zone
     * @param string The date
     * @return The date in ZonedDateTime
     */
    private ZonedDateTime transformToDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME.withZone(ZoneId.systemDefault());
        return ZonedDateTime.parse(string, formatter);
    }

    /**
     * @param imageURL URL of the image of the program
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * @return URL of the image of the program
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * @return The title of the program
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The description of the program
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The start time of the program
     */
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    /**
     * @return The end time of the program
     */
    public ZonedDateTime getEndTime() {
        return endTime;
    }

    /**
     * Makes a deep clone of the program
     * @return The deep clone of the program
     */
    public Program deepClone() {
        Program program = new Program(this.title, this.description, this.startTime, this.endTime);
        program.setImageURL(imageURL);
        return program;
    }
}
