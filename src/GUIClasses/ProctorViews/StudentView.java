package GUIClasses.ProctorViews;

import BasicClasses.Persons.Proctor;
import GUIClasses.Interfaces.TableViews;
import GUIClasses.Interfaces.Views;

import javax.swing.*;

public class StudentView extends JFrame implements Views, TableViews {
    private JPanel mainPanel;
    private JPanel upperPanel;
    private JFormattedTextField searchTF;
    private JButton searchButton;
    private JComboBox filterCondition;
    private JButton filterButton;
    private JPanel studentListPanel;
    private JTable studentListTable;
    private JPanel buttonPanel;
    private JButton nextButton;
    private JButton prevButton;
    private JButton backButton;
    private ProctorPage parentComponent;
    private Proctor proctor;
    public StudentView(ProctorPage parentComponent,Proctor proctor){
        this.parentComponent = parentComponent;
        this.proctor = proctor;
    }

    @Override
    public void setUpTable() {

    }

    @Override
    public void addDataToTable(Object object) {

    }

    @Override
    public void refreshTable() {

    }

    @Override
    public void setUpGUi() {

    }
}
