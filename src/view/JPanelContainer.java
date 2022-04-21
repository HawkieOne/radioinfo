package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Abstract class with some methods that are often used in JPanels
 * @author id18hll
 * @version 1.0
 */
public abstract  class JPanelContainer extends JPanel {

    /**
     * Adds a child component to a parent component
     * @param parent Parent component
     * @param child Child component
     */
    protected void addChild(Container parent, Component child) {
        parent.add(child);
    }

    /**
     * Expands a component in width and limits its height to normal height
     * @param component Component to adjust
     */
    protected void expandX(Component component) {
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, component.getPreferredSize().height));
    }

    /**
     * Centers a component in its parent component
     * @param component Component to adjust
     */
    protected void centerX(JComponent component) {
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Creates an empty border aroung a component which works as padding
     * @param component Component to add padding to
     * @param top Padding on the top
     * @param right Padding on the right
     * @param bottom Padding on the bottom
     * @param left Padding on the lefts
     */
    protected void createPadding(JComponent component, int top, int right, int bottom, int left) {
        component.setBorder(new EmptyBorder(top, right, bottom, left));
    }
}
