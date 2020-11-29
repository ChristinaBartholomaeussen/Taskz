package dk.kea.taskz.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {

    private Connection connection;

    /** Christina
     * Oprettelse af forbindelse til databasen
     * @return connection
     */

    public Connection establishConnection(){

        connection = null;

        try{
        connection = DriverManager.getConnection("den1.mysql2.gear.host","taskz","taskz!");

        }catch (SQLException e){
            System.out.println("No Connection");
        }

        return connection;

        }

}
