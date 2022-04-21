package view;

import model.Program;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * The class creates an custom CellRenderer for the program table
 * @author id18hll
 * @version 1.0
 */
public class TableCellRenderer implements javax.swing.table.TableCellRenderer {

    private TableCellComponent cellComponent;

    /**
     * Constructor for the class. Initializes the cell renderer and centers the title of the cell
     * @param table The table to render
     */
    public TableCellRenderer(JTable table) {
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().
                getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        cellComponent = new TableCellComponent();
    }

    /**
     * Adjusts the render properties of a cell
     * @param table Table for which the renderer works on
     * @param value ??
     * @param isSelected Status of if the cell is elected or not
     * @param hasFocus Status of if the cell is in focus or not
     * @param row The row of the cell
     * @param column The column of the cell
     * @return Returns the component in the cell to render
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Program program = (Program) value;
        cellComponent.updateData(program, isSelected, table);
        return cellComponent;
    }
}