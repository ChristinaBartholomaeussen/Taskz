package dk.kea.taskz.Repositories;
import dk.kea.taskz.Models.Member;
import dk.kea.taskz.Services.ConnectionService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class MemberRepository {
	PreparedStatement preparedStatement = null;


	/**
	 * - OVO
	 * Gets all the members from the database
	 *
	 * @return
	 */
	public ArrayList<Member> getAllMembersFromDB() {
		ArrayList<Member> memberList = new ArrayList<>();

		try {
			preparedStatement = ConnectionService.getConnection().prepareStatement("SELECT * FROM taskz.members");
			ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
            	memberList.add(new Member(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getInt(6)));
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

	/**
	 * - OVO
	 * Gets one members skills / competence
	 *
	 * @param Member_ID
	 * @return
	 */
    
    public ArrayList<String> getAllMemberCompetences(int Member_ID) {
    	String getAllMembersCompetences = "SELECT Competence FROM taskz.competences WHERE Member_ID = " + Member_ID;

    	ArrayList<String> competencesList = new ArrayList<>();
    	
    	try {
    		preparedStatement = ConnectionService.getConnection().prepareStatement(getAllMembersCompetences);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				competencesList.add(resultSet.getString(1));
			}

		} catch (SQLException e) {
			System.out.println("Error happend in memberRepo: Method: getAllMemberCompetences(), Error: " + e.getMessage());
		}


		return competencesList;
	}

	/**
	 * - OVO
	 * Sets a specific members skill / competence
	 *
	 * @param member
	 */
	public void setMemberCompetance(Member member) {
    	String placeHolder = "";

    	String getCompetanceToMemberFromDB = "select competences.competence\n" +
				"from competences\n" +
				"left outer join members_competence on competences.competence_id = members_competence.competencecompetence_id\n" +
				"left outer join members on members_competence.membermember_id = members.member_id\n" +
				"where members.member_id =" + member.getMemberId();
    	
    	try {
    		preparedStatement = ConnectionService.getConnection().prepareStatement(getCompetanceToMemberFromDB);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		
    		while(resultSet.next()) {
    			placeHolder += resultSet.getString(1) + " ";
			}

			member.setCompetance(placeHolder);

		} catch (SQLException e) {
			System.out.println("Klasse: MemberRepository, Methode: setMemberCompetance(int Member_ID), Error: " + e.getMessage());
		}
	}

	/**
	 * -OVO
	 * Gets a single member from the database withe the ID
	 *
	 * @param Member_ID
	 * @return
	 */
	public Member getSingleMEmberFromDBWthID(int Member_ID) {
    	String getMemberQuery = "SELECT * from Taskz.members WHERE Member_ID = ?";
    	Member memb = new Member();
    	
    	try {
    		
    	preparedStatement = ConnectionService.getConnection().prepareStatement(getMemberQuery);
    	preparedStatement.setInt(1, Member_ID);
    	
    	ResultSet resultSet = preparedStatement.executeQuery();
    	
    	while(resultSet.next()) {
    		memb = new Member(resultSet.getInt(1),
					resultSet.getString(2),
					resultSet.getString(3),
					resultSet.getString(4),
					resultSet.getString(5),
					resultSet.getInt(6));
    		setMemberCompetance(memb);
			return memb;
		}


		} catch (SQLException e) {
			System.out.println("Klasse: MemberRepo, Methode: getSingleMemberFRomDBWithID(), Error: " + e.getMessage());
		}
		return memb;
	}

	/**
	 * - OVO
	 * Gets the right user from the database, where email and password matches.
	 *
	 * @param Email
	 * @param Password
	 * @return
	 */
	public int getActiveUserIDFromDB(String Email, String Password) {
    	String getIdQuerry = "SELECT Member_ID FROM taskz.members WHERE Email = ? AND Password = ?";
    	int id = 0;
    	try {
    		preparedStatement = ConnectionService.getConnection().prepareStatement(getIdQuerry);
    		preparedStatement.setString(1, Email);
    		preparedStatement.setString(2, Password);
    		
    		ResultSet resultSet = preparedStatement.executeQuery();
    		
    		while (resultSet.next()) {
    			id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Klasse: MemberRepo, Methode: getActiveUserIDFromDB(), Error: " + e.getMessage());
		}
    	
    	return id;
	}

}
