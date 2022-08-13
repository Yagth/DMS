package GUIClasses.StudentViews;

import GUIClasses.Interfaces.RequestViews;

import javax.swing.*;
import java.awt.*;

public class ExtendDormStayForm extends JFrame implements RequestViews {
    private JPanel mainPanel;
    private JPanel innerPanel;
    private JTextPane descriptionPane;
    private JLabel titleLabel;
    private JButton submitButton;
    private JScrollPane descriptionSP;
    private static final int WIDTH = 550;
    private static final int HEIGHT = 250;

    public ExtendDormStayForm(){
        setUpGUi();
    }

    @Override
    public Integer updateDataBase() {
        return null;
    }

    @Override
    public void displayUpdateStatus(Integer updateStatus) {

    }

    @Override
    public void setUpGUi() {
        this.setTitle("Extend Dorm Stay Request");
        this.setContentPane(mainPanel);
        this.setSize(new Dimension(WIDTH,HEIGHT));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
