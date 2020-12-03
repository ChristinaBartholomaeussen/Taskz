package dk.kea.taskz.Services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService
{
    private static Connection connection = null;

    static
    {
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

    public static Connection getConnection()
    {
        return connection;
    }

}
