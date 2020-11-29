package dk.kea.taskz.Repositories;

import dk.kea.taskz.Services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class SubprojectRepository
{
    @Autowired
    ConnectionService connectionService;

    public int getNextAvailableProjectIdFromDB(int parentProjectId)
    {
        String nextAvailableProjectIdSqlStatement = "SELECT COUNT(*) FROM taskz.subprojects where Project_ID = " + parentProjectId;
        int nextAvailableProjectId = -1;

        try
        {
            PreparedStatement ps = connectionService.establishConnection().prepareStatement(nextAvailableProjectIdSqlStatement);
            ResultSet rs = ps.executeQuery();
        }
        catch(Exception e)
        {
            System.out.println("Error happened: " + e);
        }

        return nextAvailableProjectId;
    }

}
