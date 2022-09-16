package GUIClasses.Interfaces;

public interface TableViews extends Views{
    boolean nextButtonIsVisible();
    boolean prevButtonIsVisible();
    void setButtonVisibility();
    void setUpTable();
    void addDataToTable(Object object);
    void refreshTable();
}
