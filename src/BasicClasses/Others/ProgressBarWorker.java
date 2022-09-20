package BasicClasses.Others;

import GUIClasses.ActionListeners.ProctorView.AllocateDormView.NewStudentsDormAllocation;
import GUIClasses.ProctorViews.DormitoryView;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProgressBarWorker extends SwingWorker<Boolean,Integer> {
    DormitoryView parentComponent;
    NewStudentsDormAllocation actionListener;
    int totalStudents;
    boolean updateStatus;

    public ProgressBarWorker(DormitoryView parentComponent, Integer totalStudents, NewStudentsDormAllocation actionListener){
        this.parentComponent = parentComponent;
        this.totalStudents = totalStudents;
        this.actionListener = actionListener;
    }
    public boolean getUpdateStatus(){
        return updateStatus;
    }
    @Override
    protected Boolean doInBackground() throws Exception {
        boolean updateStatus1;
        String query = "SELECT COUNT(*) AS TotalNo FROM STUDENT WHERE BuildingNumber IS NULL AND RoomNumber IS NULL " +
                "AND Place != 'ADDIS ABABA' AND isEligible = 1 ";
        int remainingStudents = actionListener.getTotalStudentNo(query);
        actionListener.setRemainingStudents(remainingStudents);
        totalStudents = remainingStudents;
        do{
            actionListener.loadAvailableDorms();
            actionListener.sortDormOnBuildingNo();
            int totalSpace = actionListener.getTotalSpace();
            actionListener.setTotalSpace(totalSpace);

            System.out.println("TotalSpace: "+totalSpace);

            actionListener.loadNewStudents();
            updateStatus1 = actionListener.allocate();
            actionListener.incrementPageNumber();
            if(actionListener.getPageNumber()>actionListener.getTotalPage()) actionListener.resetPageNumber();
            actionListener.updateDormInfo();
            publish(remainingStudents);
        }while(remainingStudents>0);
        return updateStatus1;
    }
    @Override
    protected void process(List<Integer> chunks) {
        int remainingStudents = chunks.get(chunks.size()-1);
        JProgressBar tmp = parentComponent.getLoadingProgressBar();
        tmp.setMinimum(0);
        tmp.setMaximum(totalStudents);
        tmp.setVisible(true);
        tmp.setValue(totalStudents - remainingStudents);
    }

    @Override
    protected void done() {
        boolean updateStatus1;
        try {
            updateStatus1 = get();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        }
        updateStatus = updateStatus1;
    }
}
