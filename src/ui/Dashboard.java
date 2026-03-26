package ui;
import uiPatterns.NavigationFacade;
import  uiPatterns.UIFactory;
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private static Dashboard instance;
    private NavigationFacade nav;

    private Dashboard() {
        setTitle("MU Blood Bank Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);
        contentPanel.add(new JLabel("New Donor Entry Screen"), "DONOR_FORM");
        contentPanel.add(new JLabel("Current Blood Inventory"), "STOCK_LIST");

        nav = new NavigationFacade(contentPanel, cardLayout);

        JPanel sidebar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        sidebar.setPreferredSize(new Dimension(200, 600));
        sidebar.setBackground(Color.LIGHT_GRAY);

        JButton btnReg = UIFactory.createMenuButton("Register Donor");
        btnReg.addActionListener(e -> nav.openDonorRegistration());

        JButton btnStock = UIFactory.createMenuButton("View Stock");
        btnStock.addActionListener(e -> nav.openBloodStock());

        sidebar.add(btnReg);
        sidebar.add(btnStock);

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

    }

    public static Dashboard getInstance() {
        if (instance == null) instance = new Dashboard();
        return instance;
    }


}