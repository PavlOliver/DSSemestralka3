package gui.furniture;

import javax.swing.table.AbstractTableModel;

import OSPDataStruct.SimQueue;
import furniture.Furniture;
import workingplace.WorkingPlace;

public class FurnitureQueueTableModel extends AbstractTableModel {
    private SimQueue<Furniture> storage;
    private final String[] columnNames = {
            "Order ID", "Furniture ID", "Type", "State", "Working Place", "Arrival Time"
    };

    public FurnitureQueueTableModel(SimQueue<Furniture> storage) {
        this.storage = storage;
    }

    /**
     * Replace underlying storage and notify view.
     */
    public void setStorage(SimQueue<Furniture> storage) {
        this.storage = storage;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return storage != null ? storage.size() : 0;
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (storage == null || rowIndex < 0 || rowIndex >= storage.size()) {
            return null;
        }
        Furniture item = storage.get(rowIndex); //skus check ci je item null
        switch (columnIndex) {
            case 0: return item.getOrderId();
            case 1: return item.getId();
            case 2: return item.getType();
            case 3: return item.getState();
            case 4:
                WorkingPlace wp = item.getWorkingPlace();
                return (wp != null ? wp.toString() : "");
            case 5: return item.getArrivalTime();
            default: return null;
        }
    }
}