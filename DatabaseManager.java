package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private Connection conn = null;

    public Connection connect(String database, String server, String user, String password){
        String db_url = "jdbc:mysql://" + server + "/" + database;

        try {
            //System.out.println("Before...");
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            this.conn = DriverManager.getConnection(db_url, user, password);
        } catch (SQLException e1){
            e1.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        }

        return this.conn;
    }

    public  String getJDBC_DRIVER() { return  this.JDBC_DRIVER; }

    public  void  setJDBC_DRIVER(String JDBC_DRIVER) { this.JDBC_DRIVER = JDBC_DRIVER; }

    Connection getConn() { return  this.conn; }
}
