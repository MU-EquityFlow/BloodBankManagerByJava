package ui;

import uiPatterns.NavigationFacade;
import uiPatterns.UIFactory;
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private static Dashboard instance;
    private NavigationFacade nav;

    private Dashboard() {
        setTitle("MU Blood Bank Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);

        contentPanel.add(UIFactory.createHeaderLabel("Welcome to MU Blood Bank"), "HOME");
        contentPanel.add(UIFactory.createHeaderLabel("New Donor Entry Screen"), "DONOR_FORM");
        contentPanel.add(UIFactory.createHeaderLabel("Current Blood Inventory"), "STOCK_LIST");
        contentPanel.add(UIFactory.createHeaderLabel("Blood Request Form"), "BLOOD_REQUEST");
        contentPanel.add(UIFactory.createHeaderLabel("Registered Donors List"), "DONOR_LIST");

        nav = new NavigationFacade(contentPanel, cardLayout);

        JPanel sidebar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        sidebar.setPreferredSize(new Dimension(200, 600));
        sidebar.setBackground(new Color(220, 220, 220));

        JButton btnHome = UIFactory.createMenuButton("Home");
        btnHome.addActionListener(e -> nav.openHome());

        JButton btnReg = UIFactory.createMenuButton("Register Donor");
        btnReg.addActionListener(e -> nav.openDonorRegistration());

        JButton btnStock = UIFactory.createMenuButton("View Stock");
        btnStock.addActionListener(e -> nav.openBloodStock());

        JButton btnRequest = UIFactory.createMenuButton("Request Blood");
        btnRequest.addActionListener(e -> nav.openBloodRequest());

        JButton btnDonors = UIFactory.createMenuButton("View Donors");
        btnDonors.addActionListener(e -> nav.openDonorList());

        sidebar.add(btnHome);
        sidebar.add(btnReg);
        sidebar.add(btnStock);
        sidebar.add(btnRequest);
        sidebar.add(btnDonors);

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    public static Dashboard getInstance() {
        if (instance == null) instance = new Dashboard();
        return instance;
    }
}