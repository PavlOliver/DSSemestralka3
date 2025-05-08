package gui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class DoubleTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Name", "Mean", "Left", "Right"};
    private List<Object[]> data = new ArrayList<>();

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
        return (columnIndex == 0) ? String.class : Double.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    public void setData(List<Object[]> newData) {
        this.data = new ArrayList<>(newData);
        fireTableDataChanged();
    }
}
