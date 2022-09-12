package BasicClasses.Others;

import javax.swing.*;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;

public class JavaConnection {

    public static String URL = "jdbc:sqlserver://DMS-SERVER\\SQLEXPRESS;DatabaseName=DMS;" +
                "encrypt=true;trustServerCertificate=true;IntegratedSecurity=true;";
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    public JavaConnection(String url){
        try{
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        }catch (SQLException ex){
            ex.printStackTrace();//For debugging purposes
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
            ex.printStackTrace();//For debugging purposes
            JOptionPane.showMessageDialog(null,"Couldn't Execute the query","Connection error",JOptionPane.ERROR);
            resultSet = null;
        }
    }

    public int insertQuery(String query){
        try{
            statement.execute(query);
            return 1;
        }catch (SQLException ex){
            System.out.println("Query: "+query);//For debugging only.
            ex.printStackTrace();//For debugging purposes.
            return 0;
        }
    }
    public boolean updateQuery(String query){
        try{
            statement.execute(query);
            return true;
        }catch (SQLException ex){
            System.out.println("Query: "+query);//For debugging purposes.
            ex.printStackTrace();//For debugging purposes.
            return false;
        }
    }

    public ResultSet selectQuery(String query){
        ResultSet tmpResultSet;
        try{
            tmpResultSet = statement.executeQuery(query);
        }catch (SQLException ex){
            System.out.println("Query: "+query);//For debugging purposes.
            ex.printStackTrace();//For debugging purposes.
            return null;
        }
        return tmpResultSet;
    }
    public boolean isConnected(){
        return !(connection.equals(null));//Connection is not initialised or, it is null if the connection is not successful.
    }
}
