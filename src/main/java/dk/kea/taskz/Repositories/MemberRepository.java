package dk.kea.taskz.Repositories;
import dk.kea.taskz.Models.Member;
import dk.kea.taskz.Services.ConnectionService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class MemberRepository
{
	PreparedStatement preparedStatement = null;


    public ArrayList<Member> getAllMembersFromDB()
    {
        ArrayList<Member> memberList = new ArrayList<>();

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
            System.out.println("Error happened in member repository, getAllMembersFromDB" + e.getMessage());
        }
        return memberList;
    }
    
    public ArrayList<String> getAllMemberCompetences(int Member_ID) {
    	String getAllMembersCompetences = "SELECT Competence FROM taskz.competences WHERE Member_ID = " + Member_ID;
    	ArrayList<String> competencesList = new ArrayList<>();
    	
    	try {
    		PreparedStatement preparedStatement = ConnectionService.getConnection().prepareStatement(getAllMembersCompetences);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				competencesList.add(resultSet.getString(1));
			}
    		
		} catch (SQLException e){
			System.out.println("Error happend in memberRepo: Method: getAllMemberCompetences(), Error: " + e.getMessage());
		}
    	
    	
    	return competencesList;
	}
}
