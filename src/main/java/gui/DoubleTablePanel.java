package gui;
import javax.swing.*;
import java.awt.*;
import java.util.List;


public class DoubleTablePanel extends JPanel {
    private final DoubleTableModel model;
    private final JTable table;

    public DoubleTablePanel() {
        super(new BorderLayout());
        model = new DoubleTableModel();
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setData(List<Object[]> data) {
        model.setData(data);
    }
}
