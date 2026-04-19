package ui;
import patterns.NavigationFacade;
import patterns.UIFactory;
import java.awt.*;
import javax.swing.*;
public class Dashboard extends JFrame {
    private static Dashboard instance;
    private Dashboard() {
        setTitle("MU Blood Bank Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);

        contentPanel.add(new HomePanel(),              "HOME");
        contentPanel.add(new DonorRegistrationPanel(), "DONOR_FORM");
        contentPanel.add(new BloodStockPanel(),        "STOCK_LIST");
        contentPanel.add(new BloodRequestPanel(),      "BLOOD_REQUEST");
        contentPanel.add(new DonorListPanel(),         "DONOR_LIST");

        NavigationFacade nav = new NavigationFacade(contentPanel, cardLayout);

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