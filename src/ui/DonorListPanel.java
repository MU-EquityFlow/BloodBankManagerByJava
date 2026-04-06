package ui;

import data.JsonStorage;
import model.Donor;
import uiPatterns.UIFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DonorListPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JComboBox<String> filterCombo;

    public DonorListPanel() {
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(UIFactory.createHeaderLabel("Registered Donors"), BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new String[]{"Name", "Blood Group", "Phone", "Date Registered"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(UIFactory.createFormLabel("Filter by Blood Group: "));
        filterCombo = new JComboBox<>(
            new String[]{"All", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        filterCombo.addActionListener(e ->
            loadDonors((String) filterCombo.getSelectedItem()));
        filterPanel.add(filterCombo);

        JPanel center = new JPanel(new BorderLayout(0, 5));
        center.add(filterPanel, BorderLayout.NORTH);
        center.add(new JScrollPane(table), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                filterCombo.setSelectedIndex(0);
                loadDonors("All");
            }
        });
        loadDonors("All");
    }

    private void loadDonors(String filter) {
        tableModel.setRowCount(0);
        for (Donor d : JsonStorage.getInstance().getDonors()) {
            if (filter.equals("All") || filter.equals(d.getBloodGroup()))
                tableModel.addRow(new Object[]{
                    d.getName(), d.getBloodGroup(), d.getPhone(), d.getDate()});
        }
    }
}