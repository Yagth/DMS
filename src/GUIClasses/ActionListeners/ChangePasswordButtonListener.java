package GUIClasses.ActionListeners;

import BasicClasses.Enums.UserStatus;
import BasicClasses.Others.JavaConnection;
import GUIClasses.ChangePasswordForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordButtonListener implements ActionListener {
    private ChangePasswordForm parentComponent;
    public ChangePasswordButtonListener(ChangePasswordForm parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String oldPassword = parentComponent.getOldPassword();
        String newPassword = parentComponent.getNewPassword();
        String newPassword2 = parentComponent.getConfirmPassword();

        if(!(newPassword.equals(newPassword2)))
            JOptionPane.showMessageDialog(parentComponent,"Password field don't match");
        else if(!isOldPasswordValid(oldPassword))
            JOptionPane.showMessageDialog(parentComponent,"Incorrect old password");
        else{
            boolean updateStatus = changePassword(newPassword);
            if(updateStatus)
                JOptionPane.showMessageDialog(parentComponent,"Password changed successfully.");
            else
                JOptionPane.showMessageDialog(parentComponent,"Couldn't change password due to some reason.");
            parentComponent.goBackToParent();
        }
    }

    public boolean changePassword(String newPassword){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query;
        if(parentComponent.getUserStatus().equals(UserStatus.STUDENT))
            query = "UPDATE STUDENT SET password='"+newPassword+"' WHERE SID='"+parentComponent.getId()+"' ";
        else
            query = "UPDATE PROCTOR SET password='"+newPassword+"' WHERE EID='"+parentComponent.getId()+"' ";
        return javaConnection.updateQuery(query);
    }
    public boolean isOldPasswordValid(String oldPassword){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query;
        if(parentComponent.getUserStatus().equals(UserStatus.STUDENT))
            query= "SELECT Password FROM STUDENT WHERE SID='"+parentComponent.getId()+"' ";
        else
            query= "SELECT Password FROM PROCTOR WHERE EID='"+parentComponent.getId()+"' ";
        String password = "";
        ResultSet resultSet;
        resultSet = javaConnection.selectQuery(query);

        try{
            while(resultSet.next()){
                password = resultSet.getString("Password");
            }
        } catch (SQLException ex){
            ex.printStackTrace();//For debugging only.
        }

        return password.equals(oldPassword);
    }
}
