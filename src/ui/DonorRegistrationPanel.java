package ui;

import uiPatterns.UIFactory;
import javax.swing.*;
import java.awt.*;

public class DonorRegistrationPanel extends JPanel {
    
    public DonorRegistrationPanel() {
        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Use Factory for Header
        add(UIFactory.createHeaderLabel("Register New Donor"), BorderLayout.NORTH);

        // Form Container
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Name
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(UIFactory.createFormLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(UIFactory.createTextField(20), gbc);

        // Row 2: Blood Group
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(UIFactory.createFormLabel("Blood Group:"), gbc);
        gbc.gridx = 1;
        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        JComboBox<String> bgCombo = new JComboBox<>(bloodGroups);
        bgCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(bgCombo, gbc);

        // Row 3: Phone Number
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(UIFactory.createFormLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        formPanel.add(UIFactory.createTextField(20), gbc);

        // Row 4: Submit Button
        gbc.gridx = 1; gbc.gridy = 3;
        JButton btnSubmit = UIFactory.createPrimaryButton("Save Donor");
        btnSubmit.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Donor saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        formPanel.add(btnSubmit, gbc);

        add(formPanel, BorderLayout.CENTER);
    }
}