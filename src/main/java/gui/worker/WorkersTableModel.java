package gui.worker;

import worker.Workers;
import worker.Worker;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class WorkersTableModel extends AbstractTableModel {
    private final String[] columnNames = {
            "ID", "Type", "Busy", "Furniture", "Position", "Action", "Utilization"
    };
    private final List<Worker> data = new ArrayList<>();

    public WorkersTableModel(Workers workers) {
        setWorkers(workers);
    }

    public void setWorkers(Workers workers) {
        data.clear();
        if (workers != null) {
            data.addAll(workers.getWorkers());
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
            case 0: return Integer.class;          // ID
            case 1: return Character.class;        // Type
            case 2: return Boolean.class;          // Busy
            case 3: return String.class;           // Furniture
            case 4: return String.class;           // Position
            case 5: return String.class;           // Action
            case 6: return Double.class;           // Utilization
            default: return Object.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Worker w = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return w.getId();
            case 1:
                return w.getType();
            case 2:
                return w.isBusy();
            case 3:
                return w.getCurrentFurniture() != null
                        ? w.getCurrentFurniture().toString()
                        : "";
            case 4:
                return w.getPosition().toString();
            case 5:
                return w.getAction().toString();
            case 6:
                // Priemerná obsadenosť ako pomer 0.0–1.0
                return w.getUtilityWStat().mean();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}