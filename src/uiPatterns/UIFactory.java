package uiPatterns;
import javax.swing.*;
import java.awt.*;

public class UIFactory {

    public static JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(240, 240, 240));
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(180, 40));
        return btn;
    }
}