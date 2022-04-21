package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The class is responsible fetching the programs from the API
 * @author id18hll
 * @version 1.0
 */
public class ProgramManager extends APIManager{
    private final LocalDateTime date;

    /**
     * Private constructor for the class.
     */
    public ProgramManager() {
        date = java.time.LocalDateTime.now();
    }

    /**
     * Fetches the programs for a given channel in the API. The method loops through all the pages in the API
     * for the channel and creates an ArrayList with the channel's programs in it.
     * @param id ID of channel to add programs to
     * @return Channel with the added programs
     * @throws ParserConfigurationException Is thrown when a parse error occurs
     * @throws SAXException Is thrown if a configuration occurs
     */
    public ArrayList<Program> fetchPrograms(int id) throws SAXException, ParserConfigurationException {
        ArrayList<Program> programs = new ArrayList<>();
        ArrayList<String> dates = findTimesToDownload(id);
        APIFetcher apiFetcher = new APIFetcher();
        findTimesToDownload(id);


        for (String urlDay : dates) {
            Document doc = apiFetcher.fetchAPI(urlDay);
            if (doc != null) {
                NodeList nodeList = doc.getElementsByTagName("scheduledepisode");
                for (int j = 0; j < nodeList.getLength(); j++) {
                    Element element = (Element) nodeList.item(j);
                    Program program = buildProgram(element);
                    if (program != null) {
                        programs.add(program);
                    }
                }
            }
        }
        dates.clear();
        return programs;
    }

    /**
     * Depending on the current time gets the dates to download programs for
     * @param id ID of channel to download programs for
     */
    private ArrayList<String> findTimesToDownload(int id) {
        ArrayList<String> dates = new ArrayList<>();
        if (date.getHour() == 12) {
            dates.add("http://api.sr.se/api/v2/scheduledepisodes?channelid=" + id +
                    "&date=" + getCurrentDay() + "&pagination=false");
        } else if (date.getHour() < 12){
            dates.add("http://api.sr.se/api/v2/scheduledepisodes?channelid=" + id +
                    "&date=" + getPreviousDay() + "&pagination=false");
            dates.add("http://api.sr.se/api/v2/scheduledepisodes?channelid=" + id +
                    "&date=" + getCurrentDay() + "&pagination=false");
        } else {
            dates.add("http://api.sr.se/api/v2/scheduledepisodes?channelid=" + id +
                    "&date=" + getCurrentDay() + "&pagination=false");
            dates.add("http://api.sr.se/api/v2/scheduledepisodes?channelid=" + id +
                    "&date=" + getNextDay() + "&pagination=false");
        }
        return dates;
    }

    /**
     * Creates a program and adds it to the given channel if the program's start time is max 12 before or after
     * the current time
     * @param element The information about the program in the XML
     */
    private Program buildProgram(Element element) {

        String startTime = getTextValueOfNode(element, "starttimeutc");
        String endTime = getTextValueOfNode(element, "endtimeutc");
        ZonedDateTime startZoned = transformToDate(startTime);

        if (date.minusHours(12).isBefore(startZoned.toLocalDateTime()) &&
                date.plusHours(12).isAfter(startZoned.toLocalDateTime())) {
            String title = getTextValueOfNode(element, "title");
            String description = getTextValueOfNode(element, "description");
            String imageURL = getTextValueOfNode(element, "imageurl");
            return new Program(title, description, startTime, endTime, imageURL);
        }
        return null;
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
     * @return The current date in the specified format
     */
    private String getCurrentDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * @return The previous date in the specified format
     */
    private String getPreviousDay() {
        LocalDateTime date = java.time.LocalDateTime.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * @return The next date in the specified format
     */
    private String getNextDay() {
        LocalDateTime date = java.time.LocalDateTime.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(date);
    }
}
