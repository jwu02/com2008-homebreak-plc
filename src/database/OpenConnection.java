package database;

import java.sql.*;

public class OpenConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team021";
        String user = "team021";
        String password = "3d2a85e8";

        Connection con = DriverManager.getConnection(url,user,password);
        System.out.println("Established Connection");

        return con;
    }
}
