package GUIClasses.ActionListeners.ProctorView.DormitoryView;

import BasicClasses.Rooms.Dormitory;
import GUIClasses.ProctorViews.DormitoryView;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FocusListenerForTF implements FocusListener {
    private DormitoryView parentComponent;
    private String buildingNo;
    private String roomNo;
    public FocusListenerForTF(DormitoryView parentComponent){
        this.parentComponent = parentComponent;
    }

    @Override
    public void focusGained(FocusEvent e) {
        buildingNo = parentComponent.getBuildingNo();
        roomNo = parentComponent.getRoomNo();
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(buildingNo.equals("") && roomNo.equals("")){
            parentComponent.clearDorms();
            parentComponent.loadDorms();
            parentComponent.addDataToTable(null);
        }else{
            buildingNo = parentComponent.getBuildingNo();
            roomNo = parentComponent.getRoomNo();
        }
    }
}
