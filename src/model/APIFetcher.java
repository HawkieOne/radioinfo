package model;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * The class is responsible for fetching and XML document from the entered URl
 * @author id18hll
 * @version 1.0
 */
public class APIFetcher {
    private Document doc;

    /**
     * Fetches the given URL and builds a Document file
     * @param url To build document from
     * @return The built document
     * @throws ParserConfigurationException Is thrown when a parse error occurs
     * @throws SAXException Is thrown if a configuration occurs
     */
    public Document fetchAPI(String url) throws ParserConfigurationException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        try {
            doc = dBuilder.parse(url);
        } catch (IOException e) {
            //Does nothing, this will be caught in the SwingWorker
        }
        return doc;
    }
}
