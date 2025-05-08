package gui.worker;

import worker.Workers;

import javax.swing.*;
import java.awt.*;

public class WorkersPanel extends JPanel {
    private final JTable table;
    private final WorkersTableModel tableModel;

    public WorkersPanel(Workers workers) {
        super(new BorderLayout());
        tableModel = new WorkersTableModel(workers);
        table = new JTable(tableModel);

        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setWorkers(Workers workers) {
        tableModel.setWorkers(workers);
    }
}