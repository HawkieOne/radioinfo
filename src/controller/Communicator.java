package controller;

import view.Frame;

import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The class is responsible for staring the model an view and handling the communication between the model and view.
 * The class also starts threads for downloading API data and playing audio.
 * @author id18hll
 * @version 1.0
 */
public class Communicator {

    private Frame frame;
    private APIWorker swingWorker;
    private AudioWorker audioWorker;

    /**
     * Constructor for the class. Initializes and shows the view frame, sets the theme of the application and add
     * listeners for components in the view.
     */
    public Communicator() {
        try {
            SwingUtilities.invokeAndWait(() -> frame = new Frame());
        } catch (InterruptedException | InvocationTargetException e) {
            SwingUtilities.invokeLater(() -> frame.
                    showPopup("The frame could not be set properly." + "\n"
                    + e.getMessage()));
            System.exit(0);
        }

        changeTheme(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(() -> frame.showUI());
        startTimer();
        startAPIWorker();
        audioWorker = null;
        SwingUtilities.invokeLater(() -> addListeners());
    }

    /**
     * Adds different listeners.
     */
    private void addListeners() {
        // Listeners for channels
        JComboBox<String> dropDown = frame.getChannelsDropdown();
        addChannelSelectedListener(dropDown);
        addChannelDownListener(dropDown);
        addChannelUpListener(dropDown);
        addChannelEnterListener(dropDown);

        // Listeners for programs
        JTable table = frame.getProgramTable();
        addProgramsListener(table);
        addProgramsEnterListener(table);
        addProgramsCtrlCListener(table);
        addPlayAudioListener();

        // Listeners for menus
        addRefreshItemListener();
        addQuitItemListener();
        addSystemThemeItemListener();
        addCrossPlatformThemeItemListener();
        addMotifThemeItemListener();
    }

    /**
     * Adds a listener for detecting when a channel is selected.
     */
    private void addChannelSelectedListener(JComboBox<String> dropDown) {
        dropDown.addActionListener(e -> {
            int index = dropDown.getSelectedIndex();
            frame.showProgramsForChannel(index);
        });
    }

    /**
     * Adds a listener for detecting when the down key is pressed for the dropdown menu.
     */
    private void addChannelDownListener(JComboBox<String> dropDown) {
        new KeyListener().addKeyListener(dropDown, "DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = dropDown.getSelectedIndex();
                if (index + 1 < dropDown.getItemCount()) {
                    dropDown.setSelectedIndex(index + 1);
                }
            }
        });
    }

    /**
     * Adds a listener for detecting when the up key is pressed for the dropdown menu.
     */
    private void addChannelUpListener(JComboBox<String> dropDown) {
        new KeyListener().addKeyListener(dropDown, "UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = dropDown.getSelectedIndex();
                if (index - 1 >= 0) {
                    dropDown.setSelectedIndex(index - 1);
                }
            }
        });
    }

    /**
     * Adds a listener for detecting when the enter key is pressed for the dropdown menu.
     * THE CLASS IS NOT FULLY FUNCTIONAL. IT ONLY WORkS FOR SHOWING THE POPUP.
     */
    private void addChannelEnterListener(JComboBox<String> dropDown) {
        new KeyListener().addKeyListener(dropDown, KeyEvent.VK_ENTER, "ENTER", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dropDown.isPopupVisible()) {
                    dropDown.hidePopup();
                } else {
                    dropDown.showPopup();
                }
            }
        });
    }

    /**
     * Adds a listener for detecting when a program is selected
     */
    private void addProgramsListener(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0 && col >= 0) {
                    frame.showInfoForProgram(row);
                }
            }
        });
    }

    /**
     * Adds a listener for detecting when the enter key is pressed for the program table.
     */
    private void addProgramsEnterListener(JTable table) {
        new KeyListener().addKeyListener(table, KeyEvent.VK_ENTER, "ENTER", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                frame.showInfoForProgram(row);
            }
        });
    }

    /**
     * Adds a listener for detecting when the CTRL+C keysis pressed for the program table.
     */
    private void addProgramsCtrlCListener(JTable table) {
        new KeyListener().addKeyListener(table, KeyEvent.VK_C, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getChannelsDropdown().showPopup();
                frame.getChannelsDropdown().grabFocus();
            }
        });
    }

    /**
     * Adds a listener for playing audio
     */
    private void addPlayAudioListener() {
        JButton listenButton = frame.getListenButton();
        listenButton.addActionListener(e -> {
//            if (audioWorker == null) {
                AudioWorker musicWorker = new AudioWorker(frame.getActiveChannel().getAudioURL(), frame);
//                listenButton.setText("Pausa");
                musicWorker.execute();
//            } else {
//                audioWorker.cancel(true);
//                audioWorker = null;
//                listenButton.setText("Spela upp kanal");
//            }
        });
    }

    /**
     * Starts a worker for fetching data from the API.
     */
    private void startAPIWorker() {
        SwingUtilities.invokeLater(() -> frame.getProgramsOverview().setProgress(0));
        swingWorker = new APIWorker(frame, false);
        swingWorker.execute();
    }

    /**
     * Starts a worker for fetching data from the API. Used for updating data.
     */
    private void startWorkerUpdate() {
        SwingUtilities.invokeLater(() -> frame.getProgramsOverview().setProgress(0));
        swingWorker = new APIWorker(frame, true);
        swingWorker.execute();
    }

    /**
     * Starts a timer for updating data every hour
     */
    private void startTimer() {
        Timer timer = new Timer();

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                startWorkerUpdate();
            }
        };
        timer.scheduleAtFixedRate(tt, 60*60*1000, 60*60*1000);
    }


    /**
     * Adds a listener for refreshing the API data manually.
     */
    private void addRefreshItemListener() {
        JMenuItem refreshItem = frame.getRefreshItem();
        refreshItem.addActionListener(e ->
                startWorkerUpdate());
    }

    /**
     * Adds a listener for quitting the application.
     */
    private void addQuitItemListener() {
        JMenuItem quitItem = frame.getQuitItem();
        quitItem.addActionListener(e -> quit());
    }

    /**
     * Adds a listener for changing the theme to the system theme
     */
    private void addSystemThemeItemListener() {
        JMenuItem refreshItem = frame.getSystemThemeItem();
        refreshItem.addActionListener(e -> changeTheme(UIManager.getSystemLookAndFeelClassName()));
    }

    /**
     * Adds a listener for changing the theme to the standard theme
     */
    private void addCrossPlatformThemeItemListener() {
        JMenuItem refreshItem = frame.getCrossPlatformThemeItem();
        refreshItem.addActionListener(e -> changeTheme(UIManager.getCrossPlatformLookAndFeelClassName()));
    }

    /**
     * Adds a listener for changing the theme to the motif theme
     */
    @SuppressWarnings("SpellCheckingInspection")
    private void addMotifThemeItemListener() {
        JMenuItem motifItem = frame.getMotifThemeItem();
        motifItem.addActionListener(e -> changeTheme("com.sun.java.swing.plaf.motif.MotifLookAndFeel"));
    }

    /**
     * Changes the theme of the application
     * @param string The theme to change to
     */
    private void changeTheme(String string) {
        try {
            UIManager.setLookAndFeel(string);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            SwingUtilities.invokeLater(() ->
                    frame.showPopup("The theme could not be set properly. Using standard theme." + "\n"
                    + e.getMessage()));
        }
        SwingUtilities.updateComponentTreeUI(frame.getFrame());
        SwingUtilities.invokeLater(() -> frame.getFrame().pack());
    }

    /**
     * Quits the application
     */
    private void quit() {
        System.exit(0);
    }
}
