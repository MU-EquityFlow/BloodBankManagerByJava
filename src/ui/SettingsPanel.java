package ui;

import uiPatterns.data.JsonStorage;
import uiPatterns.UIFactory;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    public SettingsPanel() {
        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        add(UIFactory.createHeaderLabel("System Settings"), BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.gridx  = 0;

        gbc.gridy = 0;
        JLabel info = new JLabel(
            "<html><b>MU Blood Bank Manager</b><br/>Version 1.0" +
            "<br/>Data is stored locally in JSON format inside the <i>storage/</i> folder.</html>");
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        content.add(info, gbc);

        gbc.gridy = 1;
        JSeparator sep = new JSeparator();
        content.add(sep, gbc);

        gbc.gridy = 2;
        JButton clearBtn = new JButton("Clear All Donor Records");
        clearBtn.setFont(new Font("Arial", Font.BOLD, 13));
        clearBtn.setForeground(Color.RED);
        clearBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                "This will permanently delete all donor records. Continue?",
                "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                JsonStorage.getInstance().clearAllDonors();
                JOptionPane.showMessageDialog(this, "All donor records cleared.",
                    "Done", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        content.add(clearBtn, gbc);

        add(content, BorderLayout.CENTER);
    }
}