package dk.kea.taskz.Services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class ConnectionService {



    /** Christina
     * Oprettelse af forbindelse til databasen
     * @return connection
     */

    public Connection establishConnection(){

        Connection connection = null;

        connection = null;
        try{

        connection = DriverManager.getConnection("jdbc:mysql://den1.mysql2.gear.host:3306/taskz","taskz","taskz!");
			System.out.println("-- Connection OK --");

        }catch (SQLException e){
            System.out.println("No Connection: " + e.getMessage());
        }
        return connection;
        }
}
