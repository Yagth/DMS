package BasicClasses.Others;

import javax.swing.*;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;

public class JavaConnection {
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    public JavaConnection(String url){
        try{
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"Couldn't Connect to server","Connection error",JOptionPane.ERROR_MESSAGE);
            connection = null;
            statement = null;
            resultSet = null;
        }
    }

    public JavaConnection(String url, String query){
        this(url);
        try{
            statement.executeQuery(query);
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"Couldn't Execute the query","Connection error",JOptionPane.ERROR);
            resultSet = null;
        }
    }

    public int insertQuery(String query){
        try{
            statement.execute(query);
            return 1;
        }catch (SQLException ex){
            return 0;
        }
    }

    public ResultSet selectQuery(String query){
        ResultSet tmpResultSet = null;
        try{
            tmpResultSet = statement.executeQuery(query);
        }catch (SQLException ex){
            return null;
        }
        return tmpResultSet;
    }
    public boolean isConnected(){
        return !(connection.equals(null));//Connection is not initialised or, it is null if the connection is not successful.
    }
}
