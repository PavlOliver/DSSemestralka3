package gui.workingplace;

import workingplace.WorkingPlaces;

import javax.swing.*;
import java.awt.*;

public class WorkingPlacesPanel extends JPanel {
    private final JTable table;
    private final WorkingPlacesTableModel tableModel;

    /**
     * Constructs the panel with the given WorkingPlaces.
     * @param workingPlaces the WorkingPlaces instance
     */
    public WorkingPlacesPanel(WorkingPlaces workingPlaces) {
        super(new BorderLayout());
        tableModel = new WorkingPlacesTableModel(workingPlaces);
        table = new JTable(tableModel);

        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Updates the displayed WorkingPlaces and refreshes the table.
     * @param workingPlaces the new WorkingPlaces instance
     */
    public void setWorkingPlaces(WorkingPlaces workingPlaces) {
        tableModel.setWorkingPlaces(workingPlaces);
    }
}
