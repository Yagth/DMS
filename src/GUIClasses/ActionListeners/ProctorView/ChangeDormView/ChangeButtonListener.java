package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import GUIClasses.ActionListeners.ChangeBackButtonListener;
import GUIClasses.ProctorViews.ChangeDormView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeButtonListener implements ActionListener {
    private ChangeDormView parentComponent;
    public ChangeButtonListener(ChangeDormView parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String condition = parentComponent.getSelectedCondition();
        String buildingNo = parentComponent.getBuildingNo();
        String dormNo = parentComponent.getDormNo();
        String destinationBuildingNo = parentComponent.getDestinationBuildingNo();
        String destinationRoomNo = parentComponent.getDestinationRoomNo();
        String query;
        if(condition.equals("Change single student")){
            query = "UPDATE STUDENT SET BuildingNumber='"+destinationBuildingNo+"', " +
                    "RoomNumber='"+destinationRoomNo+"' " +
                    "WHERE BuildingNumber='"+buildingNo+"' AND RoomNumber='"+dormNo+"';";
        }
    }
}
