package BasicClasses.Others;

import GUIClasses.ProctorViews.DormitoryView;
import GUIClasses.ProctorViews.LoadingClass;

public class LoadingThread extends Thread{
    private LoadingClass parentComponent;
    public LoadingThread(LoadingClass parentComponent){
        super();
        this.parentComponent = parentComponent;
    }

    @Override
    public void run(){
        System.out.println("Inside run method.");
        parentComponent.fill();
    }
}
