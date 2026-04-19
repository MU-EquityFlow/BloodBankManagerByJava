package ui;

import java.awt.*;
import java.time.LocalDate;
import java.util.UUID;
import javax.swing.*;
import model.Donor;
import patterns.UIFactory;
import patterns.command.AddDonorCommand;
import patterns.command.CommandHistory;

public class DonorRegistrationPanel extends JPanel {
    private JTextField      nameField;
    private JTextField      phoneField;
    private JComboBox<String> bloodGroupCombo;

    public DonorRegistrationPanel() {
        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(UIFactory.createHeaderLabel("Register New Donor"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(UIFactory.createFormLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        nameField = UIFactory.createTextField(20);
        form.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(UIFactory.createFormLabel("Blood Group:"), gbc);
        gbc.gridx = 1;
        bloodGroupCombo = new JComboBox<>(
            new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        bloodGroupCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        form.add(bloodGroupCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(UIFactory.createFormLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        phoneField = UIFactory.createTextField(20);
        form.add(phoneField, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        JButton saveBtn = UIFactory.createPrimaryButton("Save Donor");
        saveBtn.addActionListener(e -> registerDonor());
        form.add(saveBtn, gbc);

        

        add(form, BorderLayout.CENTER);
    }

    private void registerDonor() {
        String name  = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.",
                "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Donor donor = new Donor(
            UUID.randomUUID().toString(), name,
            (String) bloodGroupCombo.getSelectedItem(),
            phone, LocalDate.now().toString());
        CommandHistory.getInstance().execute(new AddDonorCommand(donor));
        nameField.setText("");
        phoneField.setText("");
        JOptionPane.showMessageDialog(this, "Donor registered successfully!",
            "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}