package dk.kea.taskz.Repositories;

import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubprojectRepository
{
    @Autowired
    ConnectionService connectionService;

    public List<Subproject> getAllAssociatedSubprojects(int projectId)
    {
        String getAllAssociatedSubprojectsSqlStatement = "SELECT COUNT(*) FROM taskz.subprojects WHERE Project_ID = " + projectId;
        List<Subproject> subprojectList = new ArrayList<>();

        try
        {
            PreparedStatement ps = connectionService.establishConnection().prepareStatement(getAllAssociatedSubprojectsSqlStatement);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                subprojectList.add(new Subproject(rs.getInt(1),rs.getInt(3),rs.getString(2)));
                System.out.println("Added: " + rs.getString(2));
            }

        }
        catch(Exception e)
        {
            System.out.println("Error happened in SubprojectRepository at getAllAssociatedSubprojects(): " + e);
        }

        return subprojectList;
    }
}
