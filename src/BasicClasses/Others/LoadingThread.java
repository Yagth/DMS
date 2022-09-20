package BasicClasses.Others;

import GUIClasses.ProctorViews.DormitoryView;
import GUIClasses.ProctorViews.LoadingClass;

public class LoadingThread extends Thread{
    private LoadingClass parentComponent;
    public LoadingThread(LoadingClass parentComponent){
        this.parentComponent = parentComponent;
    }
    public LoadingThread(){
        this(new LoadingClass(null));
    }
    public void dispose(){
        parentComponent.dispose();
    }
    @Override
    public void run(){
        System.out.println("Inside run method.");
        parentComponent.setVisible(true);
        parentComponent.fill();
    }
}
