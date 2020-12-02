package dk.kea.taskz.Services;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class ConnectionService {


    private static ConnectionService single_instance = null;

    public static ConnectionService getInstance(){
        if(single_instance == null){
            single_instance = new ConnectionService();
            System.out.println("Connection ok");
        }
        return single_instance;
    }

    /** Christina
     * Oprettelse af forbindelse til databasen
     * @return connection
     */
    Connection connection = null;

    public Connection establishConnection(){

        try{

        connection = DriverManager.getConnection("jdbc:mysql://den1.mysql2.gear.host:3306/taskz","taskz","taskz!");

        }catch (SQLException e){
            System.out.println("No Connection: " + e.getMessage());
        }


        return connection;
        }
}
