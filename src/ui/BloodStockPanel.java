package ui;

import uiPatterns.data.JsonStorage;
import uiPatterns.memento.StockCaretaker;
import model.BloodStock;
import uiPatterns.UIFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BloodStockPanel extends JPanel {
    private DefaultTableModel tableModel;
    private static final String[] GROUPS =
        {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    public BloodStockPanel() {
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(UIFactory.createHeaderLabel("Blood Inventory"), BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new String[]{"Blood Group", "Current Units", "Set To"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return col == 2; }
        };
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton applyBtn = UIFactory.createPrimaryButton("Apply Updates");
        JButton undoBtn  = new JButton("Undo Last Update");
        undoBtn.setFont(new Font("Arial", Font.PLAIN, 13));

        applyBtn.addActionListener(e -> applyUpdates(table));
        undoBtn .addActionListener(e -> undoLastUpdate());

        btnPanel.add(applyBtn);
        btnPanel.add(undoBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) { loadStock(); }
        });
        loadStock();
    }

    private void loadStock() {
        tableModel.setRowCount(0);
        BloodStock stock = JsonStorage.getInstance().getStock();
        for (String g : GROUPS)
            tableModel.addRow(new Object[]{g, stock.get(g), stock.get(g)});
    }

    private void applyUpdates(JTable table) {
        if (table.isEditing()) table.getCellEditor().stopCellEditing();
        BloodStock current = JsonStorage.getInstance().getStock();
        StockCaretaker.getInstance().save(current.createMemento());
        BloodStock updated = new BloodStock(current.getAll());
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String group = (String) tableModel.getValueAt(i, 0);
            try {
                int val = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
                if (val >= 0) updated.set(group, val);
            } catch (NumberFormatException ignored) {}
        }
        JsonStorage.getInstance().saveStock(updated);
        loadStock();
        JOptionPane.showMessageDialog(this, "Stock updated successfully!",
            "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void undoLastUpdate() {
        if (!StockCaretaker.getInstance().canUndo()) {
            JOptionPane.showMessageDialog(this, "Nothing to undo.",
                "Undo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        BloodStock stock = JsonStorage.getInstance().getStock();
        stock.restore(StockCaretaker.getInstance().undo());
        JsonStorage.getInstance().saveStock(stock);
        loadStock();
        JOptionPane.showMessageDialog(this, "Stock reverted to previous state.",
            "Undo", JOptionPane.INFORMATION_MESSAGE);
    }
}