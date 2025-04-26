package gui.furniture;

import furniture.Furnitures;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;

public class FurnituresTablePanel extends JPanel {
    private final JTable table;
    private final FurnituresTableModel tableModel;

    public FurnituresTablePanel(Queue<Furnitures> queue) {
        super(new BorderLayout());
        tableModel = new FurnituresTableModel(queue);
        table = new JTable(tableModel);

        Font font = new Font("SansSerif", Font.PLAIN, 16);
        table.setFont(font);
        table.setRowHeight(font.getSize() + 4);
        table.getTableHeader().setFont(font);

        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setMaxWidth(50);

        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setQueue(Queue<Furnitures> queue) {
        tableModel.setQueue(queue);
    }
}
