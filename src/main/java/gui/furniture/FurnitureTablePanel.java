package gui.furniture;

import furniture.Furniture;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FurnitureTablePanel extends JPanel {
    private final JTable table;
    private final FurnitureTableModel tableModel;

    public FurnitureTablePanel(List<Furniture> furnitureList) {
        super(new BorderLayout());
        tableModel = new FurnitureTableModel(furnitureList);
        table = new JTable(tableModel);


        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setFurnitureList(List<Furniture> furnitureList) {
        tableModel.setFurnitureList(furnitureList);
    }

}

