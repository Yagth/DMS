package GUIClasses;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
    private Image img;
    private int width;
    private int height;
    public DisplayPanel(int width, int height){
        this.img = new ImageIcon("Images/DMS-background.jpg").getImage();
        img = img.getScaledInstance(width, -1, Image.SCALE_SMOOTH);

        this.width = width;
        this.height = height;
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        System.out.println(img);
        g.drawImage(img, 0,0, this);
    }
}
