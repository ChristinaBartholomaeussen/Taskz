package dk.kea.taskz.Repositories;
import dk.kea.taskz.Models.Member;
import dk.kea.taskz.Services.ConnectionService;
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
			preparedStatement = ConnectionService.getConnection().prepareStatement("select m.member_id, m.email, " +
					"m.password, m.first_name, m.last_name, group_concat(Distinct c.competence SEPARATOR ' , ' ) as competence," +
					"j.jobtitle_description\n" +
					"from members m\n" +
					"left outer join jobtitles j on m.jobtitle_id = j.jobtitle_id\n" +
					"left outer join members_competence mc on m.member_id = mc.membermember_id\n" +
					"left outer join competences c on mc.competencecompetence_id = c.competence_id\n" +
					"group by m.member_id");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Member member = new Member(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7)
				);
				memberList.add(member);
			}

		} catch (Exception e) {
			System.out.println("Error happened in member repository, getAllMembersFromDB" + e.getMessage());
		}
		return memberList;
	}


	/**
	 * -OVO
	 * Gets a single member from the database withe the ID
	 *
	 * @param member_ID
	 * @return
	 */
	public Member getSingleMEmberFromDBWthID(int member_ID) {

		Member member = new Member();

    	String getMemberQuery = "select m.member_id, m.email, m.password, m.first_name, m.last_name, group_concat(Distinct c.competence SEPARATOR ' , ' ) as competence, j.jobtitle_description\n" +
				"from members m\n" +
				"left outer join jobtitles j on m.jobtitle_id = j.jobtitle_id\n" +
				"left outer join members_competence mc on m.member_id = mc.membermember_id\n" +
				"left outer join competences c on mc.competencecompetence_id = c.competence_id\n" +
				"where m.member_id = ?";

    	try {
    		
    	preparedStatement = ConnectionService.getConnection().prepareStatement(getMemberQuery);
    	preparedStatement.setInt(1, member_ID);
    	
    	ResultSet resultSet = preparedStatement.executeQuery();
    	
    	while(resultSet.next()) {
    		member = new Member(
    				resultSet.getInt(1),
					resultSet.getString(2),
					resultSet.getString(3),
					resultSet.getString(4),
					resultSet.getString(5),
					resultSet.getString(6),
					resultSet.getString(7));
		}


		} catch (SQLException e) {
			System.out.println("Klasse: MemberRepo, Methode: getSingleMemberFRomDBWithID(), Error: " + e.getMessage());
		}
		return member;
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
