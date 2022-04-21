package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * The class is responsible for creating a menubar with menus in it
 * @author id18hll
 * @version 1.0
 */
public class MenuBar extends JMenuBar {
    private JMenu optionsMenu;
    private JMenuItem refreshItem;
    private JMenuItem quitItem;

    private JMenu themeMenu;
    private JRadioButtonMenuItem systemThemeItem;
    private JRadioButtonMenuItem crossPlatformThemeItem;
    private JRadioButtonMenuItem motifThemeItem;

    /**
     * Constructor for the class. Initializes necessary components and adjusts their properties
     */
    public MenuBar() {
        optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_O);
        String OPTIONSDESCRIPTION = "Menu for refreshing content or quitting the application";
        optionsMenu.getAccessibleContext().setAccessibleDescription(OPTIONSDESCRIPTION);
        addChild(this, optionsMenu);

        refreshItem = createMenuItem("Refresh content", KeyEvent.VK_R, "Refresh the information");
        optionsMenu.add(refreshItem);
        quitItem = createMenuItem("Quit", KeyEvent.VK_Q, "Quit the application");
        optionsMenu.add(quitItem);

        themeMenu = new JMenu("Theme");
        optionsMenu.setMnemonic(KeyEvent.VK_T);
        String THEMEDESCRIPTION = "Change theme of the application";
        optionsMenu.getAccessibleContext().setAccessibleDescription(THEMEDESCRIPTION);
        addChild(this, themeMenu);

//        systemThemeItem = createThemeMenuItem("System theme", KeyEvent.VK_S, "Set theme to mimic the system");
//        themeMenu.add(systemThemeItem);
//        crossPlatformThemeItem = createThemeMenuItem("Metal theme", KeyEvent.VK_M, "Set theme to cross-platform theme");
//        themeMenu.add(crossPlatformThemeItem);
//        motifThemeItem = createThemeMenuItem("Motif theme", KeyEvent.VK_J, "Set theme to motif theme");
//        themeMenu.add(motifThemeItem);

        //a group of radio button menu items
        ButtonGroup group = new ButtonGroup();
        systemThemeItem = createThemeMenuItem("System theme", KeyEvent.VK_J,
                "Set theme to mimic the system");
        systemThemeItem.setSelected(true);
        group.add(systemThemeItem);
        themeMenu.add(systemThemeItem);
        crossPlatformThemeItem = createThemeMenuItem("Metal theme", KeyEvent.VK_K,
                "Set theme to cross-platform theme");
        group.add(crossPlatformThemeItem);
        themeMenu.add(crossPlatformThemeItem);
        motifThemeItem = createThemeMenuItem("Motif theme", KeyEvent.VK_L, "Set theme to motif theme");
        group.add(motifThemeItem);
        themeMenu.add(motifThemeItem);
    }

    /**
     * Creates a new menu item for putting into a menu
     * @param title The title of the menu item
     * @param keyCode The key to press to activate the menu item
     * @param description Text description for the buttons. Used for speech help
     * @return The created menu item
     */
    private JMenuItem createMenuItem(String title, int keyCode, String description) {
        JMenuItem item = new JMenuItem(title);
        if (keyCode != -1){
            item.setAccelerator(KeyStroke.getKeyStroke(
                    keyCode, InputEvent.CTRL_DOWN_MASK));
        }
        item.getAccessibleContext().setAccessibleDescription(description);
        return item;
    }

    /**
     * Creates a new menu item for putting into a menu
     * @param title The title of the menu item
     * @param keyCode The key to press to activate the menu item
     * @param description Text description for the buttons. Used for speech help
     * @return The created menu item
     */
    private JRadioButtonMenuItem createThemeMenuItem(String title, int keyCode, String description) {
        JRadioButtonMenuItem item = new JRadioButtonMenuItem(title);
        if (keyCode != -1){
            item.setAccelerator(KeyStroke.getKeyStroke(
                    keyCode, InputEvent.CTRL_DOWN_MASK+InputEvent.ALT_DOWN_MASK));
        }
        item.getAccessibleContext().setAccessibleDescription(description);
        return item;
    }

    /**
     * Adds a child component to the given parent component
     * @param parent Parent component
     * @param child Child commponent
     */
    private void addChild(Container parent, Component child) {
        parent.add(child);
    }

    /**
     * @return The item responsible for refreshing the API data
     */
    public JMenuItem getRefreshItem() {
        return refreshItem;
    }

    /**
     * @return The item responsible for quitting the program
     */
    public JMenuItem getQuitItem() {
        return quitItem;
    }

    /**
     * @return The item responsible for changing the theme to System theme
     */
    public JMenuItem getSystemThemeItem() {
        return systemThemeItem;
    }

    /**
     * @return The item responsible for changing the theme to Standard theme
     */
    public JMenuItem getCrossPlatformThemeItem() {
        return crossPlatformThemeItem;
    }

    /**
     * @return The item responsible for changing the theme to Motif theme
     */
    public JMenuItem getMotifThemeItem() {
        return motifThemeItem;
    }

}
