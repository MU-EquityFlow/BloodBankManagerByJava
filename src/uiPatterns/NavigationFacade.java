package uiPatterns;

import java.awt.*;
import javax.swing.*;

public class NavigationFacade {
    private JPanel container;
    private CardLayout layout;

    public NavigationFacade(JPanel container, CardLayout layout) {
        this.container = container;
        this.layout = layout;
    }

    public void openHome() { layout.show(container, "HOME"); }
    public void openDonorRegistration() { layout.show(container, "DONOR_FORM"); }
    public void openBloodStock() { layout.show(container, "STOCK_LIST"); }
    public void openBloodRequest() { layout.show(container, "BLOOD_REQUEST"); }
    public void openDonorList() { layout.show(container, "DONOR_LIST"); }
    
    // --- NEW ROUTE ---
    public void openSettings() { layout.show(container, "SETTINGS"); }
}