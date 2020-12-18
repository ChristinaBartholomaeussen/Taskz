package dk.kea.taskz.Services;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionService
{
    /**(CMB)
     * Singleton, because we only need to establish the connection once
     * Static variable of connection which is null in the beginning
     * Static block of code with our url, user and password which tries to connect to out database with DriverManager,
     * Static because we only need the information to be stored once in our memory
     * Static method of Connection which returns the connection, which makes the navigation on our application faster,
     * because we don't need to instance a new object of our class and then call the method with all the
     * information every single time
     */

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
