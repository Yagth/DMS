package GUIClasses;

import BasicClasses.Others.JavaConnection;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class TableViewPage extends JFrame {
    private int pageNumber;
    protected static final int ROW_PER_PAGE = 15;
    private int totalPage;
    public TableViewPage(){
        pageNumber = 1;
    }
    public void incrementPageNumber(){
        pageNumber++;
    }

    public void decrementPageNumber(){
        pageNumber--;
    }

    public void setPageNumber(int totalItem){
        float row = ROW_PER_PAGE;
        int pageNumber = (int) Math.ceil(totalItem / row);
        this.pageNumber = pageNumber;
    }

    public int loadPageSize(String query){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        int total = 0;
        ResultSet resultSet;
        float row = ROW_PER_PAGE;

        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    total = resultSet.getInt("TotalNo");
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
            total = (int) Math.ceil(total / row);
        }
        return total;
    }

    public int getPageNumber() {
        return pageNumber;
    }
    public int getTotalPage(){
        return this.totalPage;
    }

    public void loadAndSetTotalPage(String query){
        this.totalPage = loadPageSize(query);
    }
    public void resetPageNumber(){
        pageNumber = 1;
    }
}
