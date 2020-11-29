package dk.kea.taskz.Repositories;

import dk.kea.taskz.Models.Member;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class MemberRepository
{
    public List<Member> getAllMembersFromDB()
    {

        try
        {
            Connection connection = DriverManager.getConnection("den1.mysql2.gear.host","taskz","taskz!");
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM members");

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
