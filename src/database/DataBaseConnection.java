package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {

    private static Connection conn = null;
    private DataBaseConnection(){}
    public static Connection getConnection(){
        if(conn != null){
            return conn;
        }
        else {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatapp","root","");
            }catch(Exception e){
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("Opened database successfully");
            return conn;
        }
    }
}
