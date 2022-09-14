package GUIClasses.ActionListeners.ProctorView.ProctorPage;

import GUIClasses.ProctorViews.ProctorPage;
import GUIClasses.ProctorViews.StudentView;
import com.microsoft.sqlserver.jdbc.SQLServerStatement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class SeeStudentMenuListener extends MenuItemListener{
    public SeeStudentMenuListener(ProctorPage parentComponent){
        super(parentComponent);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        hideParentComponent();
        try{
            new StudentView(parentComponent,parentComponent.getProctor());
        } catch (NullPointerException ex){
            ex.printStackTrace();//For debugging only.
            JOptionPane.showMessageDialog(parentComponent,"Couldn't Open this page due to some error.",
                    "Error opening page",JOptionPane.ERROR_MESSAGE);
            parentComponent.setVisible(true);
        }
    }
}
