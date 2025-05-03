package gui.furniture;

import furniture.Furnitures;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class FurnituresTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "size", "furnitures"};
    private final List<Furnitures> data = new ArrayList<>();

    public FurnituresTableModel(Queue<Furnitures> queue) {
        setQueue(queue);
    }

    public void setQueue(Queue<Furnitures> queue) {
        data.clear();
        if (queue != null) {
            // vraj sa tym viem vyhnut toArray issues ta som zvedavy
            data.addAll(queue);
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
        return switch (columnIndex) {
            case 0 -> Integer.class;
            case 1 -> Integer.class;
            case 2 -> String.class;
            default -> Object.class;
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Furnitures f = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return f.getId();
            case 1:
                return f.getAllFurnituresInOrder().size();
            case 2:
                return f.furnituresInOrder();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
