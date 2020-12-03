package dk.kea.taskz.Repositories;
import dk.kea.taskz.Models.Member;
import dk.kea.taskz.Services.ConnectionService;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class MemberRepository
{

	PreparedStatement preparedStatement = null;
	ArrayList<Member> memberList = new ArrayList<>();

    public ArrayList<Member> getAllMembersFromDB()
    {
        try
        {
            preparedStatement = ConnectionService.getConnection().prepareStatement("SELECT * FROM taskz.members");
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
            	memberList.add(new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}

        }
        catch(Exception e)
        {
            System.out.println("Error happend in member repository, getAllMembersFromDB" + e.getMessage());
        }
        return memberList;
    }
    
    public ArrayList<String> getAllMemeberCompetances(int Member_ID) {
    	String getAllMembersCompetances = "SELECT Competance FROM taskz.competances WHERE Member_ID = " + Member_ID;
    	ArrayList<String> competancesList = new ArrayList<>();
    	
    	try {
    		PreparedStatement preparedStatement = ConnectionService.getConnection().prepareStatement(getAllMembersCompetances);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				competancesList.add(resultSet.getString(1));
			}
    		
		} catch (SQLException e){
			System.out.println("Error happend in memberRepo: Method: getAllMemeberCompetances(), Error: " + e.getMessage());
		}
    	
    	
    	return competancesList;
	}
}
