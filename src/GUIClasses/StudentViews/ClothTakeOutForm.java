package GUIClasses.StudentViews;

import BasicClasses.Others.Cloth;
import BasicClasses.Others.JavaConnection;
import BasicClasses.Requests.ClothTakeOutRequest;
import GUIClasses.Interfaces.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.Date;

public class ClothTakeOutForm extends JFrame implements Views {
    private JTextField clothName;
    private JTextField clothAmount;
    private JButton addButton;
    private JButton finishButton;
    private JPanel mainPanel;
    private JLabel clotheLabel;
    private JLabel amountLabel;
    private JPanel buttonPanel;
    private JPanel titlePanel;
    private JLabel descriptionLabel;
    private JLabel numberLabel;
    private JPanel clothPanel;
    private JPanel clothListPanel;
    private JPanel clothAmountPanel;
    private ClothTakeOutRequest clothList;
    String reportId = "yep it is"; // This part here is only for debugging. It will be removed when the project is complete.
    private int clothCount;
    public final int WIDTH = 400;
    public final int HEIGHT = 200;

    public ClothTakeOutForm(){
        clothList = new ClothTakeOutRequest(reportId);
        setUpGUi();
    }
    @Override
    public void setUpGUi() {
        this.setTitle("Student cloth Take Out Form");
        this.setContentPane(mainPanel);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        clothListPanel.setLayout(new BoxLayout(clothListPanel, BoxLayout.Y_AXIS));
        addButton.addActionListener(new AddButtonListener());
        finishButton.addActionListener(new FinishButtonListener());
        clothAmountPanel.setLayout(new BoxLayout(clothAmountPanel, BoxLayout.Y_AXIS));
    }
    private class FinishButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (clothList.getClothsList().size() != 0) {
                Integer updateStatus = updateDataBase();
                displayUpdateStatus(updateStatus);
                ClothTakeOutForm.this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(ClothTakeOutForm.this,
                        "No cloths added. Make sure to add First","Empty List",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }



    private class AddButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                Cloth tmp = getClothInfo();
                if(tmp.getClothAmount()>0)
                    if(tmp.getClothName().equals(""))
                        JOptionPane.showMessageDialog(mainPanel,"Name can't be empty",
                                "Invalid Input error",JOptionPane.ERROR_MESSAGE);
                    else{
                        clothList.addCloth(tmp);
                        addClothToView(tmp);
                        clear();
                    }
                else
                    JOptionPane.showMessageDialog(mainPanel,"Amount is invalid. cloth not added. " +
                            "Make sure to enter amount greater than zero","Invalid Input error",
                            JOptionPane.INFORMATION_MESSAGE);
            }
            catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(mainPanel,"Amount can't be empty",
                        "Invalid amount error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public Cloth getClothInfo() throws NumberFormatException{
        String name = clothName.getText();
        int amount = Integer.parseInt(clothAmount.getText());
        return new Cloth(name,amount);
    }
    public void addClothToView(Cloth cloth){
        clothCount++;

        JLabel tmp = new JLabel((clothCount)+". "+cloth.getClothName());
        JLabel tmp2 = new JLabel(String.valueOf(cloth.getClothAmount()));
        tmp.setHorizontalTextPosition(JLabel.RIGHT);
        tmp2.setHorizontalTextPosition(JLabel.RIGHT);
        tmp.setHorizontalAlignment(JLabel.CENTER);
        tmp2.setHorizontalAlignment(JLabel.CENTER);

        clothListPanel.add(tmp);
        clothAmountPanel.add(tmp2);

        numberLabel.setText(String.valueOf(clothCount+1));
        ///Resizing the frame when the list is greater than the current size.
        if(clothCount>=5){
            Dimension dimension= this.getSize();
            dimension.setSize(dimension.getWidth(),dimension.getHeight()+15);//Increasing the height of the frame when the list is long.
            this.setSize(dimension);
        }
        clothName.requestFocus();
        this.revalidate();
    }
    public void clear(){
        clothName.setText("");
        clothAmount.setText("");
    }

    public Integer updateDataBase() {
        String url = "jdbc:sqlserver://DESKTOP-AA4PR2S\\SQLEXPRESS;DatabaseName=DMS;" +
                "encrypt=true;trustServerCertificate=true;IntegratedSecurity=true;";
        String query = "INSERT INTO clothRequest(reportID,reporterID,ClothName,Amount,reportedDate)";
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        String reportType = this.getTitle();
        JavaConnection javaConnection = new JavaConnection(url);
        Integer updateStatus = 0;
        for (Cloth c : clothList.getClothsList()) {
            query += "VALUES(\'" + reportId + "\',\'" + c.getClothName()+ "\',\'" +
                    c.getClothAmount()+"\',\'"+ date + "\',\'" + reportType + "\');";
            if (javaConnection.isConnected()) updateStatus = javaConnection.insertQuery(query);
        }
        return updateStatus;
    }
    public void displayUpdateStatus(Integer updateStatus){
        if (updateStatus.equals(1))
            JOptionPane.showMessageDialog(null, "Request sent successfully", "Message sent", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "Sorry couldn't send your request due to connection error", "Connection error", JOptionPane.ERROR_MESSAGE);
    }
}
