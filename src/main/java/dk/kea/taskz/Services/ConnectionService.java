package dk.kea.taskz.Services;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionService
{
    /**
     * - CMB
     * Singleton, because we only need to establish the connection once.
     * A static Connection object is declared, but the value is set to null.
     * Then a block of code with our url, user and password is created, which uses DriverManager try to connect to
     * our database. It is made static, as we only need the information to be stored in memory once.
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

    /**
     * - CMB
     * A static method of Connection is created, which returns the connection.
     * This makes the navigation on our application faster, as we don't need to instantiate a new object of our
     * class and then call the method with all the information every single time we try to establish a connection.
     * @return
     */
    public static Connection getConnection()
    {
        return connection;
    }

}
