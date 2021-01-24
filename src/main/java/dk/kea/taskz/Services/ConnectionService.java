package dk.kea.taskz.Services;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionService {

    private static Connection connection = null;

    static {
        String url = "jdbc:mysql://den1.mysql2.gear.host:3306/taskz";
        String user = "taskz";
        String pass = "taskz!";
        try {
            connection = DriverManager.getConnection(url, user, pass);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    /**
     * FMP - Exam
     * Test to check speed difference between single instance connection and multiple connection objects
     * @return
     */

    public Connection establishConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://den1.mysql2.gear.host:3306/taskz", "taskz", "taskz!");
        } catch (SQLException e) {
            System.out.println("No connection");
        }
        return connection;
    }

}
