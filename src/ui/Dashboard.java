package ui;

import java.awt.*;
import javax.swing.*;
import uiPatterns.NavigationFacade;
import uiPatterns.UIFactory;

public class Dashboard extends JFrame {
    private static Dashboard instance;

    private Dashboard() {
        setTitle("MU Blood Bank Manager");
        setSize(1050, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);

        contentPanel.add(new HomePanel(),              "HOME");
        contentPanel.add(new DonorRegistrationPanel(), "DONOR_FORM");
        contentPanel.add(new BloodStockPanel(),        "STOCK_LIST");
        contentPanel.add(new BloodRequestPanel(),      "BLOOD_REQUEST");
        contentPanel.add(new DonorListPanel(),         "DONOR_LIST");
        contentPanel.add(new SettingsPanel(),          "SETTINGS");

        NavigationFacade nav = new NavigationFacade(contentPanel, cardLayout);

        JPanel sidebar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        sidebar.setPreferredSize(new Dimension(200, 680));
        sidebar.setBackground(new Color(220, 220, 220));

        JButton btnHome     = UIFactory.createMenuButton("Home");
        JButton btnReg      = UIFactory.createMenuButton("Register Donor");
        JButton btnStock    = UIFactory.createMenuButton("View Stock");
        JButton btnRequest  = UIFactory.createMenuButton("Request Blood");
        JButton btnDonors   = UIFactory.createMenuButton("View Donors");
        JButton btnSettings = UIFactory.createMenuButton("Settings");

        btnHome    .addActionListener(e -> nav.openHome());
        btnReg     .addActionListener(e -> nav.openDonorRegistration());
        btnStock   .addActionListener(e -> nav.openBloodStock());
        btnRequest .addActionListener(e -> nav.openBloodRequest());
        btnDonors  .addActionListener(e -> nav.openDonorList());
        btnSettings.addActionListener(e -> nav.openSettings());

        sidebar.add(btnHome);
        sidebar.add(btnReg);
        sidebar.add(btnStock);
        sidebar.add(btnRequest);
        sidebar.add(btnDonors);
        sidebar.add(btnSettings);

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    public static Dashboard getInstance() {
        if (instance == null) instance = new Dashboard();
        return instance;
    }
}