package GUIClasses;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
    private Image img;
    private int width;
    public DisplayPanel(int width){
        this.img = new ImageIcon("Images/DMS-background.jpg").getImage();
        img = img.getScaledInstance(width, -1, Image.SCALE_SMOOTH);

        this.width = width;
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0,0, this);
    }
}
