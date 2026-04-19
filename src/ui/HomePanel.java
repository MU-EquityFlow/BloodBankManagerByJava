package ui;

import patterns.data.JsonStorage;
import model.BloodStock;
import patterns.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;

public class HomePanel extends JPanel {
    private JPanel cardsPanel;
    private JLabel donorCountLabel;

    public HomePanel() {
        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        add(UIFactory.createHeaderLabel("Welcome to MU Blood Bank Manager"), BorderLayout.NORTH);

        donorCountLabel = new JLabel("", SwingConstants.CENTER);
        donorCountLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        cardsPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        cardsPanel.setBorder(BorderFactory.createTitledBorder("Current Blood Stock"));

        JPanel center = new JPanel(new BorderLayout(0, 15));
        center.add(donorCountLabel, BorderLayout.NORTH);
        center.add(cardsPanel, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) { refresh(); }
        });
        refresh();
    }

    private void refresh() {
        donorCountLabel.setText("Total Registered Donors: "
            + JsonStorage.getInstance().getDonors().size());
        cardsPanel.removeAll();
        BloodStock stock = JsonStorage.getInstance().getStock();
        for (Map.Entry<String, Integer> entry : stock.getAll().entrySet()) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(entry.getValue() > 0
                ? new Color(200, 255, 200) : new Color(255, 200, 200));
            card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            JLabel type  = new JLabel(entry.getKey(), SwingConstants.CENTER);
            type.setFont(new Font("Arial", Font.BOLD, 18));
            JLabel count = new JLabel(entry.getValue() + " units", SwingConstants.CENTER);
            count.setFont(new Font("Arial", Font.PLAIN, 14));
            card.add(type,  BorderLayout.CENTER);
            card.add(count, BorderLayout.SOUTH);
            cardsPanel.add(card);
        }
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }
}