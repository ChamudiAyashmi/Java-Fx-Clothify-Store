package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    public Connection connection;
    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection= DriverManager.getConnection("jdbc:mysql://localhost/ClothfyStore", "root", "1234");
    }
    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        return dbConnection!=null ? dbConnection : (dbConnection= new DBConnection());
    }
    public Connection getConnection(){
        return connection;
    }

}
