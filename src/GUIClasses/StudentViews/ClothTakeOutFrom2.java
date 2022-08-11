package GUIClasses.StudentViews;

import javax.swing.*;
import javax.swing.table.TableColumn;

public class ClothTakeOutFrom2 extends JFrame{
    private JPanel mainPanel;
    private JTable table1;
    private JScrollPane myTable;
    private JTable clothList;

    ClothTakeOutFrom2(){
        this.setSize(400,200);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        clothList.addColumn(new TableColumn(2));
        clothList.setCellSelectionEnabled(false);
    }
}
