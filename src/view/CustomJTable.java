package view;

import model.Program;

import javax.swing.*;
import java.awt.*;

/**
 * The class is responsible creating a custom JTable
 * @author id18hll
 * @version 1.0
 */
public class CustomJTable extends JTable {

    private CustomTableModel tableModel;

    /**
     * Constructor for the class. Initializes necessary components and adjusts their properties
     */
    public CustomJTable() {
        tableModel = new CustomTableModel(0, 1);
        setModel(tableModel);


        TableCellRenderer cellRenderer = new TableCellRenderer(this);
        setDefaultRenderer(Program.class, cellRenderer);

        this.setGridColor(Color.decode("#434C5E"));
    }

    /**
     * Clears the table of all entries
     */
    public void clearTable() {
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    /**
     * Adds a row of data to the table
     * @param rowData Data to add
     */
    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    /**
     * Adjusts the height of a row in the table
     * @return The height of a row
     */
    @Override
    public int getRowHeight() {
        return 35;
    }
}
