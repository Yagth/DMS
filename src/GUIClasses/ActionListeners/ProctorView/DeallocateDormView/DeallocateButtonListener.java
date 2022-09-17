package GUIClasses.ActionListeners.ProctorView.DeallocateDormView;

import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.Request;
import GUIClasses.ProctorViews.DeallocateDormView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeallocateButtonListener implements ActionListener {
    private DeallocateDormView parentComponent;
    String buildingNumber;

    public DeallocateButtonListener(DeallocateDormView parentComponent){
        this.parentComponent = parentComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(parentComponent.getSelectedCondition().equals("deallocate Batch of students")){
            buildingNumber = parentComponent.getFromBuildingNumber();
            if(!(buildingNumber.equals(""))){
                String year = parentComponent.getYearTF().getText();
                String query = null, query2 = null;
                String query3= "INSERT INTO ProctorControlsStock(EID,ActionType,ActionDate,BuildingNumber) "+
                        " VALUES('"+parentComponent.getProctor().getpId()+"' , 'Deallocate Dorm', '"+
                        Request.getCurrentDate()+"' , '"+parentComponent.getFromBuildingNumber()+"')";
                if(year.equals("")){
                    JOptionPane.showMessageDialog(parentComponent,"Year can't be empty on this condition");
                }
                else{
                    int yearInt = Integer.parseInt(year);
                    boolean updateStatus = false;
                    query2 ="UPDATE STUDENT SET RoomNumber=null, BuildingNumber=null WHERE year="+yearInt+" AND BuildingNumber='"+buildingNumber+"';";
                    query = "UPDATE Stock SET" +
                                " TotalPillow+=(SELECT COUNT(SID) FROM STUDENT WHERE year="+yearInt+" AND BuildingNumber='"+buildingNumber+"')," +
                                " TotalMatress+=(SELECT COUNT(SID) FROM STUDENT WHERE year="+yearInt+" AND BuildingNumber='"+buildingNumber+"' ),"+
                                " TotalMatressBase+=(SELECT COUNT(SID) FROM STUDENT WHERE year="+yearInt+" AND BuildingNumber='"+buildingNumber+"')"+
                            " WHERE BuildingNumber='"+buildingNumber+"';";
                    int choice = JOptionPane.showConfirmDialog(parentComponent,"Are you sure these students has returned their equipments?",
                            "confirm",JOptionPane.YES_NO_OPTION);
                    if(!(parentComponent.getNumberOfStudentsL().getText().equals("0"))){ // If there are students satisfying the condition.
                        if(choice==0) updateStatus = deallocate(query) & deallocate(query2) & deallocate(query3);
                        System.out.println("Query: "+query);//For debugging only.
                        System.out.println("Query2: "+query2);//FOr debugging only.
                        System.out.println("Query3: "+query3);//FOr debugging only.
                        displayUpdateStatus(updateStatus);
                    }
                    else{
                        JOptionPane.showMessageDialog(parentComponent,"Zero students deallocated");
                    }

                    parentComponent.makeParentComponentVisible();
                    parentComponent.dispose();
                }
            }
            else{
                JOptionPane.showMessageDialog(parentComponent,"From building number can't be empty");
            }
        }
        else{
            buildingNumber = String.valueOf(parentComponent.getProctor().getBuildingNo());
            String query = null, query2 = null;
            boolean updateStatus = false;
            query2 = "UPDATE STUDENT SET BuildingNumber=null, RoomNumber=null WHERE isEligible="+0+";";
            query = "UPDATE Stock SET" +
                        " TotalPillow+=(SELECT COUNT(SID) FROM STUDENT WHERE isEligible=0), " +
                        " TotalMatress+=(SELECT COUNT(SID) FROM STUDENT WHERE isEligible=0), " +
                        " TotalMatress+=(SELECT COUNT(SID) FROM STUDENT WHERE isEligible=0) " +
                    " WHERE BuildingNumber='"+buildingNumber+"';";
            int choice = JOptionPane.showConfirmDialog(parentComponent,"Are you sure you want to deallocate these students?",
                    "confirm",JOptionPane.YES_NO_OPTION);
            if(!(parentComponent.getNumberOfStudentsL().getText().equals("0"))){ // If there are students satisfying the condition.
                if(choice==0) updateStatus = deallocate(query) & deallocate(query2);
                displayUpdateStatus(updateStatus);
            }
            else{
                JOptionPane.showMessageDialog(parentComponent,"Zero students deallocated");
            }
            parentComponent.makeParentComponentVisible();
            parentComponent.dispose();
        }
    }
    private boolean deallocate(String query){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        boolean updateStatus = false;
        if(javaConnection.isConnected()){
            updateStatus = javaConnection.updateQuery(query);
        }
        return updateStatus;
    }

    private void displayUpdateStatus(boolean updateStatus){
        if(updateStatus){
            JOptionPane.showMessageDialog(parentComponent,"Deallocation successful");
        }
        else{
            JOptionPane.showMessageDialog(parentComponent,"Couldn't deallocate students due to some reason.");
        }
    }
}
