package dk.kea.taskz.Repositories;

import dk.kea.taskz.Models.Member;
import dk.kea.taskz.Services.ConnectionService;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class MemberRepository
{
    ConnectionService connection = new ConnectionService();

    public List<Member> getAllMembersFromDB()
    {
        try
        {
            PreparedStatement ps = connection.establishConnection().prepareStatement("SELECT * FROM members");

            ResultSet rs = ps.executeQuery();

            while(rs.next())
                System.out.println(rs);

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
}
