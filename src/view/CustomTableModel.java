package view;


import model.Program;

import javax.swing.table.DefaultTableModel;

/**
* The class is responsible for creating a custom table model for the programs table to use
* @author id18hll
* @version 1.0
*/
public class CustomTableModel extends DefaultTableModel {

    /**
     * Constructor for the class. Initializes the table model wih the given rows and columns
     * @param rowCount Amount of rows of the table model
     * @param columnCount Amount of columns of the table model
     */
    public CustomTableModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    /**
     * Sets the title of a column
     * @param column Column to add title to
     * @return The title of the column
     */
    @Override
    public String getColumnName(int column) {
        return "Program";
    }

    /**
     * Sets if the cell is editable or not
     * @param row The row of the cell
     * @param column The column of the cell
     * @return If the cell is editable
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /**
     * Sets the amount of columns in the table model
     * @return The amount of columns
     */
    @Override
    public int getColumnCount() {
        return 1;
    }

    /**
     * Sets the given class for a column
     * @param columnIndex the column to identify a class with
     * @return The Class to identify a column with
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Program.class;
    }
}
