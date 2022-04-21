package model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Abstract class with methods usable for XML reading
 * @author id18hll
 * @version 1.0
 */
public abstract class APIManager {

    /**
     * Return if the given node is an Element node, e.g. if it has values
     * @param node The node the investigate
     * @return If the node is an element node
     */
    protected boolean isElementNode(Node node) {
        return node.getNodeType() == Node.ELEMENT_NODE;
    }

    /**
     * Checks if a given element contains the given tagname. If it does the first valid value of the NodeList created
     * from the tagname is returned. If no matches to the tagname is found, the method returns null.
     * @param element The element which may contain the tagname
     * @param tagName The tagname to look for
     * @return The first value found
     */
    protected String getTextValueOfNode(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (isElementNode(node)) {
                    Element nodeElement = (Element) node;
                    return nodeElement.getTextContent();
                }
            }
        }
        return null;
    }
}
