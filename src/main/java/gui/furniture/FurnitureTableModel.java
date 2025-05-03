package gui.furniture;

import OSPDataStruct.SimQueue;
import furniture.Furniture;
import furniture.FurnitureState;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class FurnitureTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Order ID", "ID", "Type", "State"};
    private List<Furniture> furnitureList;

    public FurnitureTableModel(List<Furniture> furnitureList) {
        this.furnitureList = furnitureList;
    }

    public void setFurnitureList(List<Furniture> newList) {
        this.furnitureList.clear();
        this.furnitureList.addAll(newList);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return furnitureList.size();
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
                return Integer.class;
            case 2:
                return String.class;
            case 3:
                return FurnitureState.class;
            default:
                return Object.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Furniture f = furnitureList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> f.getOrderId();
            case 1 -> f.getId();
            case 2 -> f.getType();
            case 3 -> f.getState();
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
