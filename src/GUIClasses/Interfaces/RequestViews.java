package GUIClasses.Interfaces;

public interface RequestViews extends Views{
    public Integer updateDataBase();
    public void displayUpdateStatus(Integer updateStatus);
    public void showParentComponent();
    public Integer getCurrentClothRequestId();
}
