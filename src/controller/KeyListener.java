package controller;

import javax.swing.*;

/**
 * The class is responsible for adding key listeners to components
 * @author id18hll
 * @version 1.0
 */
public class KeyListener {

    /**
     * Adds a key listener to a JComboBox
     * @param dropDown The JComboBox
     * @param key The key
     * @param action The action to happen when the key is pressed
     */
    public void addKeyListener(JComboBox<String> dropDown, String key, AbstractAction action) {
        dropDown.getInputMap().put(KeyStroke.getKeyStroke(key), "action" + key);
        dropDown.getActionMap().put("action" + key, action);
    }

    /**
     * Adds a key listener to a JComboBox
     * @param dropDown The JComboBox
     * @param keyEvent The key
     * @param key The key in text
     * @param action The action to happen when the key is pressed
     */
    public void addKeyListener(JComboBox<String> dropDown, int keyEvent, String key, AbstractAction action) {
        dropDown.getInputMap().put(KeyStroke.getKeyStroke((char) keyEvent), "action" + key);
        dropDown.getActionMap().put("actionMapKeyEnter", action);
    }

    /**
     * Adds a key listener to a JTable
     * @param table The table
     * @param keyEvent The key
     * @param key The key in text
     * @param action The action to happen when the key is pressed
     */
    public void addKeyListener(JTable table, int keyEvent, String key, AbstractAction action) {
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(keyEvent,
                0), key);
        table.getActionMap().put(key, action);
    }

    /**
     * Adds a key listener to a JTable
     * @param table The table
     * @param key The key
     * @param action The action to happen when the key is pressed
     */
    public void addKeyListener(JTable table, int key, AbstractAction action) {
        table.getInputMap().put(KeyStroke.getKeyStroke(key, java.awt.event.InputEvent.CTRL_DOWN_MASK),
                "openDropdown");
        table.getActionMap().put("openDropdown", action);
    }
}
