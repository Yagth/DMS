package GUIClasses.ActionListeners;

import GUIClasses.Interfaces.TableViews;
import GUIClasses.TableViewPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextActionListener implements ActionListener {
    private TableViewPage parentComponent;
    public NextActionListener(TableViewPage parentComponent){
        this.parentComponent = parentComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        parentComponent.incrementPageNumber();
        TableViews parentComponent = (TableViews) this.parentComponent;
        parentComponent.setButtonVisibility();
    }
}
