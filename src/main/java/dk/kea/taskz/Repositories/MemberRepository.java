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

			for (Member member : memberList) {
				setMemberCompetance(member);
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
	
	public void setMemberCompetance(Member member) {
    	String placeHolder = "";
    	String getCompetanceToMemberFromDB = "SELECT Competence, competences.Member_ID, members.First_Name\n" +
				"FROM taskz.competences\n" +
				"INNER JOIN taskz.members ON competences.Member_ID=members.Member_ID\n" +
				"WHERE competences.Member_ID = " + member.getMemberId();
    	
    	try {
    		PreparedStatement preparedStatement = ConnectionService.getConnection().prepareStatement(getCompetanceToMemberFromDB);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		
    		while(resultSet.next()) {
    			placeHolder += resultSet.getString(1) + " ";
			}
    		
    		member.setCompetance(placeHolder);
    		
		} catch(SQLException e) {
			System.out.println("Klasse: MemberRepository, Methode: setMemberCompetance(int Member_ID), Error: " + e.getMessage());
		}
	}
}
