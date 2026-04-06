package ui;

import command.CommandHistory;
import command.RequestBloodCommand;
import data.JsonStorage;
import model.BloodRequest;
import uiPatterns.UIFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BloodRequestPanel extends JPanel {
    private JTextField        patientNameField;
    private JComboBox<String> bloodGroupCombo;
    private JSpinner          unitsSpinner;
    private DefaultTableModel tableModel;

    public BloodRequestPanel() {
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(UIFactory.createHeaderLabel("Blood Request Management"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("New Request"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(UIFactory.createFormLabel("Patient Name:"), gbc);
        gbc.gridx = 1;
        patientNameField = UIFactory.createTextField(15);
        form.add(patientNameField, gbc);

        gbc.gridx = 2;
        form.add(UIFactory.createFormLabel("Blood Group:"), gbc);
        gbc.gridx = 3;
        bloodGroupCombo = new JComboBox<>(
            new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        form.add(bloodGroupCombo, gbc);

        gbc.gridx = 4;
        form.add(UIFactory.createFormLabel("Units:"), gbc);
        gbc.gridx = 5;
        unitsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        form.add(unitsSpinner, gbc);

        gbc.gridx = 6;
        JButton submitBtn = UIFactory.createPrimaryButton("Submit Request");
        submitBtn.addActionListener(e -> submitRequest());
        form.add(submitBtn, gbc);

        tableModel = new DefaultTableModel(
            new String[]{"Patient", "Blood Group", "Units", "Date", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton fulfillBtn  = UIFactory.createPrimaryButton("Fulfill Selected");
        JButton undoReqBtn  = new JButton("Undo Last Submission");
        undoReqBtn.setFont(new Font("Arial", Font.PLAIN, 13));

        fulfillBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Select a request first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<BloodRequest> reqs = JsonStorage.getInstance().getRequests();
            BloodRequest selected   = reqs.get(row);
            if (!selected.getStatus().equals("Pending")) {
                JOptionPane.showMessageDialog(this,
                    "This request is already " + selected.getStatus(),
                    "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (JsonStorage.getInstance().fulfillRequest(selected.getId())) {
                JOptionPane.showMessageDialog(this, "Request fulfilled. Stock deducted.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                loadRequests();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Insufficient stock for " + selected.getBloodGroup() + ".",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        undoReqBtn.addActionListener(e -> {
            if (!CommandHistory.getInstance().canUndo()) {
                JOptionPane.showMessageDialog(this, "Nothing to undo.",
                    "Undo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            CommandHistory.getInstance().undo();
            loadRequests();
            JOptionPane.showMessageDialog(this, "Last request submission undone.",
                "Undo", JOptionPane.INFORMATION_MESSAGE);
        });

        bottomPanel.add(fulfillBtn);
        bottomPanel.add(undoReqBtn);

        JPanel tableSection = new JPanel(new BorderLayout());
        tableSection.setBorder(BorderFactory.createTitledBorder("All Requests"));
        tableSection.add(new JScrollPane(table), BorderLayout.CENTER);
        tableSection.add(bottomPanel, BorderLayout.SOUTH);

        JPanel main = new JPanel(new BorderLayout(0, 10));
        main.add(form, BorderLayout.NORTH);
        main.add(tableSection, BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) { loadRequests(); }
        });
        loadRequests();
    }

    private void submitRequest() {
        String name = patientNameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient name.",
                "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        BloodRequest req = new BloodRequest(
            UUID.randomUUID().toString(), name,
            (String) bloodGroupCombo.getSelectedItem(),
            (Integer) unitsSpinner.getValue(),
            LocalDate.now().toString(), "Pending");
        CommandHistory.getInstance().execute(new RequestBloodCommand(req));
        patientNameField.setText("");
        loadRequests();
        JOptionPane.showMessageDialog(this, "Blood request submitted!",
            "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadRequests() {
        tableModel.setRowCount(0);
        for (BloodRequest r : JsonStorage.getInstance().getRequests())
            tableModel.addRow(new Object[]{
                r.getPatientName(), r.getBloodGroup(),
                r.getUnits(), r.getDate(), r.getStatus()});
    }
}