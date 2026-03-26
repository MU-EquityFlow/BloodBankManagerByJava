package uiPatterns;
import javax.swing.*;
import java.awt.*;
public class NavigationFacade {
    private JPanel container;
    private CardLayout layout;

    public NavigationFacade(JPanel container, CardLayout layout) {
        this.container = container;
        this.layout = layout;
    }

    public void openDonorRegistration() {
        layout.show(container, "DONOR_FORM");
    }

    public void openBloodStock() {
        layout.show(container, "STOCK_LIST");
    }
}