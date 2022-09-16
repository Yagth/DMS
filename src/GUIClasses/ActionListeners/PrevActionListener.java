package GUIClasses.ActionListeners;

import GUIClasses.Interfaces.TableViews;
import GUIClasses.TableViewPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrevActionListener implements ActionListener {
    private TableViewPage parentComponent;
    public PrevActionListener(TableViewPage parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.decrementPageNumber();
        TableViews parentComponent = (TableViews) this.parentComponent;
        parentComponent.setButtonVisibility();
    }
}
