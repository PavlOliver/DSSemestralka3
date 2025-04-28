package gui.workingplace;

import workingplace.WorkingPlace;
import workingplace.WorkingPlaces;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class WorkingPlacesTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Worker", "Furniture", "Occupied"};
    private final List<WorkingPlace> data = new ArrayList<>();

    public WorkingPlacesTableModel(WorkingPlaces workingPlaces) {
        setWorkingPlaces(workingPlaces);
    }

    public void setWorkingPlaces(WorkingPlaces workingPlaces) {
        data.clear();
        if (workingPlaces != null) {
            data.addAll(workingPlaces.getWorkingPlaces());
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Boolean.class;
            default:
                return Object.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        WorkingPlace wp = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return wp.getId();
            case 1:
                return wp.getCurrentWorker() != null ? wp.getCurrentWorker().toString() : "";
            case 2:
                return wp.getCurrentFurniture() != null ? wp.getCurrentFurniture().toString() : "";
            case 3:
                return wp.isOccupied();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
