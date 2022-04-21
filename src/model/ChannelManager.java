package model;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;

/**
 * The class is responsible fetching the channels from the API
 * @author id18hll
 * @version 1.0
 */
public class ChannelManager extends APIManager{

    private final String url;
    private NodeList channels;

    /**
     * Constructor for the class.
     */
    public ChannelManager() {
        this.url = "http://api.sr.se/api/v2/channels?pagination=false";
    }

    /**
     * Loads the channels in the XML into a NodeList so the size may be fetched though getAmunOfChannels().
     * @throws ParserConfigurationException Is thrown when a parse error occurs
     * @throws SAXException Is thrown if a configuration error occurs
     */
    public void loadChannels() throws ParserConfigurationException, SAXException, DataFetchingException {
        APIFetcher apiFetcher = new APIFetcher();
        Document doc = apiFetcher.fetchAPI(url);
        try {
            channels = doc.getElementsByTagName("channel");
        } catch (NullPointerException e) {
            throw new DataFetchingException();
        }
    }

    /**
     * Returns the amount of channels found in the XML
     * @return The length of the channels NodeList
     */
    public int getAmountOfChannels() {
        return channels.getLength();
    }

    /**
     * Fetches the channels in the API. The method loops through all the pages in the API for channels and creates
     * an ArrayList with them in it.
     * @throws ParserConfigurationException Is thrown when a parse error occurs
     * @throws SAXException Is thrown if a configuration error occurs
     */
    public Channel buildChannel(int channelIndex) throws SAXException, ParserConfigurationException {
        Node node = channels.item(channelIndex);
        Channel channel = new Channel();
        if (isElementNode(node)) {
            NamedNodeMap namedNodeMap = node.getAttributes();
            channel.setId(Integer.parseInt(namedNodeMap.getNamedItem("id").getNodeValue()));
            channel.setName(namedNodeMap.getNamedItem("name").getNodeValue());

            Element element = (Element) node;
            String tagLine = getTextValueOfNode(element, "tagline");
            channel.setTooltip(tagLine);
            String audioURL = getTextValueOfNode(element, "url");
            channel.setAudioURL(audioURL);

            ProgramManager programManager = new ProgramManager();
            channel.setPrograms(programManager.fetchPrograms(channel.getId()));
        }
        return channel;
    }
}
