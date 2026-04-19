package ui;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.BloodRequest;
import patterns.UIFactory;
import patterns.command.CommandHistory;
import patterns.command.RequestBloodCommand;
import patterns.data.JsonStorage;

public class BloodRequestPanel extends JPanel {
    private JTextField        patientNameField;
    private JComboBox<String> bloodGroupCombo;
    private JSpinner          unitsSpinner;
    private DefaultTableModel tableModel;

    public BloodRequestPanel() {
        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        add(UIFactory.createHeaderLabel("Blood Request Center"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Submit a New Request"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 2);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        form.add(UIFactory.createFormLabel("Patient Name:"), gbc);  
        gbc.gridx = 1; gbc.weightx = 1.0;
        patientNameField = UIFactory.createTextField(40);  
        form.add(patientNameField, gbc);

        gbc.gridx = 2;
        form.add(UIFactory.createFormLabel("Blood Type:"), gbc);
        gbc.gridx = 3;
        bloodGroupCombo = new JComboBox<>(
            new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        form.add(bloodGroupCombo, gbc);

        gbc.gridx = 4;
        form.add(UIFactory.createFormLabel("Units Needed:"), gbc);
        gbc.gridx = 5;
        unitsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        form.add(unitsSpinner, gbc);

        gbc.gridx = 6;
        JButton submitBtn = UIFactory.createPrimaryButton("Add Request");
        submitBtn.addActionListener(_ -> submitRequest());
        form.add(submitBtn, gbc);

        tableModel = new DefaultTableModel(
            new String[]{"Patient Name", "Blood Type", "Units", "Request Date", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
        JButton fulfillBtn  = UIFactory.createPrimaryButton("Mark as Fulfilled");
        JButton undoReqBtn  = new JButton("Undo Last Request");
        undoReqBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));

        fulfillBtn.addActionListener(_ -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select a request first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<BloodRequest> reqs = JsonStorage.getInstance().getRequests();
            BloodRequest selected   = reqs.get(row);
            if (!selected.getStatus().equals("Pending")) {
                JOptionPane.showMessageDialog(this,
                    "This request has already been " + selected.getStatus() + ".",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (JsonStorage.getInstance().fulfillRequest(selected.getId())) {
                JOptionPane.showMessageDialog(this, "Request fulfilled successfully. Stock has been updated.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                loadRequests();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Not enough stock available for blood type " + selected.getBloodGroup() + ".",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        undoReqBtn.addActionListener(_ -> {
            if (!CommandHistory.getInstance().canUndo()) {
                JOptionPane.showMessageDialog(this, "No recent action to undo.",
                    "Undo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            CommandHistory.getInstance().undo();
            loadRequests();
            JOptionPane.showMessageDialog(this, "The last request has been removed.",
                "Undo", JOptionPane.INFORMATION_MESSAGE);
        });

        bottomPanel.add(fulfillBtn);
        bottomPanel.add(undoReqBtn);

        JPanel tableSection = new JPanel(new BorderLayout());
        tableSection.setBorder(BorderFactory.createTitledBorder("Request Overview"));
        tableSection.add(new JScrollPane(table), BorderLayout.CENTER);
        tableSection.add(bottomPanel, BorderLayout.SOUTH);

        JPanel main = new JPanel(new BorderLayout(0, 12));
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
            JOptionPane.showMessageDialog(this, "Patient name cannot be empty.",
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
        JOptionPane.showMessageDialog(this, "Blood request has been submitted successfully!",
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