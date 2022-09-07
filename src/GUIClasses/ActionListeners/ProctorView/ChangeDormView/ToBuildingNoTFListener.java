package GUIClasses.ActionListeners.ProctorView.ChangeDormView;

import BasicClasses.Rooms.Dormitory;
import GUIClasses.ProctorViews.ChangeDormView;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ToBuildingNoTFListener implements FocusListener {
    private ChangeDormView parentComponent;
    public ToBuildingNoTFListener(ChangeDormView parentComponent){this.parentComponent = parentComponent;}
    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    public int getTotalAvailableSpace(){
        int totalSpace = 0;
        for(Dormitory dormitory: availableDorms){
            totalSpace += dormitory.getMaxCapacity() - dormitory.getNoOfStudents();
        }
        return totalSpace;
    }
}
