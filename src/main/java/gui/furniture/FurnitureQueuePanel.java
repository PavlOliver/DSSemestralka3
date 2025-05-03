package gui.furniture;

import OSPDataStruct.SimQueue;
import furniture.Furniture;

import javax.swing.*;
import java.awt.*;

public class FurnitureQueuePanel extends JPanel {
    private final FurnitureQueueTableModel tableModel;
    private final JTable table;

    /**
     * Constructs the panel bound to the given furniture queue.
     * @param storage the SimQueue<Furniture> to display
     */
    public FurnitureQueuePanel(SimQueue<Furniture> storage) {
        super(new BorderLayout());
        this.tableModel = new FurnitureQueueTableModel(storage);
        this.table = new JTable(tableModel);

        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

    }

    /**
     * Update the panel to display a new SimQueue instance.
     * @param newStorage the new SimQueue<Furniture>
     */
    public void setStorage(SimQueue<Furniture> newStorage) {
        tableModel.setStorage(newStorage);
    }

    /**
     * Manually trigger refresh of the table view.
     */
    public void updateView() {
        tableModel.fireTableDataChanged();
    }

    /**
     * Gives access to the underlying JTable for further customization.
     */
    public JTable getTable() {
        return table;
    }
}




