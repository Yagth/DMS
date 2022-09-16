package GUIClasses;

import BasicClasses.Others.JavaConnection;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class TableViewPage extends JFrame {
    private int pageNumber;
    protected static final int ROW_PER_PAGE = 10;
    private int totalPage;

    public TableViewPage(String tableName){
        pageNumber = 1;
        totalPage = loadPageSize(tableName);
    }
    public void incrementPageNumber(){
        pageNumber++;
    }

    public void decrementPageNumber(){
        pageNumber--;
    }

    public int loadPageSize(String tableName){
        JavaConnection javaConnection = new JavaConnection(JavaConnection.URL);
        String query = "SELECT COUNT(*) TotalNo FROM "+tableName;
        int total = 0;
        ResultSet resultSet;

        if(javaConnection.isConnected()){
            resultSet = javaConnection.selectQuery(query);
            try{
                while(resultSet.next()){
                    total = resultSet.getInt("TotalNo");
                }
            } catch (SQLException ex){
                ex.printStackTrace();//For debugging only.
            }
            total = (int) Math.ceil(total /ROW_PER_PAGE);
        }
        return total;
    }

    public int getPageNumber() {
        return pageNumber;
    }
    public int getTotalPage(){
        return this.totalPage;
    }

    public void setTotalSize(int totalSizePage){
        this.totalPage = totalSizePage;
    }
}
