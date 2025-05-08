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
            case 0:
                return Integer.class;
            case 1:
                return Character.class;
            case 2:
                return Boolean.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            case 6:
                return Double.class;
            default:
                return Object.class;
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