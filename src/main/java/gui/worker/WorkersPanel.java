package gui.worker;

import worker.Workers;

import javax.swing.*;
import java.awt.*;

public class WorkersPanel extends JPanel {
    private final JTable table;
    private final WorkersTableModel tableModel;

    /**
     * Vytvorí panel so zoznamom pracovníkov.
     * @param workers inštancia Workers, ktorú chceme zobrazovať
     */
    public WorkersPanel(Workers workers) {
        super(new BorderLayout());
        tableModel = new WorkersTableModel(workers);
        table = new JTable(tableModel);

        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        // Voliteľne: nastaviť šírky stĺpcov
        // table.getColumnModel().getColumn(0).setPreferredWidth(50);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Aktualizuje dáta v tabuľke.
     * @param workers nová inštancia Workers
     */
    public void setWorkers(Workers workers) {
        tableModel.setWorkers(workers);
    }
}