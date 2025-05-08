package gui.furniture;

import OSPDataStruct.SimQueue;
import furniture.Furniture;

import javax.swing.*;
import java.awt.*;

public class FurnitureQueuePanel extends JPanel {
    private final FurnitureQueueTableModel tableModel;
    private final JTable table;

    public FurnitureQueuePanel(SimQueue<Furniture> storage) {
        super(new BorderLayout());
        this.tableModel = new FurnitureQueueTableModel(storage);
        this.table = new JTable(tableModel);

        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(false);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

    }

    public void setStorage(SimQueue<Furniture> newStorage) {
        tableModel.setStorage(newStorage);
    }

    public void updateView() {
        tableModel.fireTableDataChanged();
    }

    public JTable getTable() {
        return table;
    }
}




