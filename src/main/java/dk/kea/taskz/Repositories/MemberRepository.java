package dk.kea.taskz.Repositories;
import dk.kea.taskz.Models.Member;
import dk.kea.taskz.Services.ConnectionService;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class MemberRepository {

	PreparedStatement preparedStatement = null;


	/**(OVO, CMB)
	 * Gets all the members from the database.
	 * Group_concat(combined the competences for each member in
	 * one column and separates them by ','.
	 * It joins the tables jobtitles where the jobtitle is equal
	 * so that we can access the jobtitle description.
	 * Joiner the many-many table members_competences where
	 * the member_id is equal.
	 * Joins the competences table where the compentence_id
	 * is equal to the competence_id in the many-many table.
	 * Groups them by the member_id.
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
			System.out.println("Error happened in MemberRepository at getAllMembersFromDB()" + e.getMessage());
		}
		return memberList;
	}


	/**(OVE, CMB)
	 * Gets a single member from the database withe the ID
	 * Selects the id, email, password, firstname, lastname
	 * competence and jobtitle_description.
	 * To access those we need to join the jobtitle table,
	 * members_competence table and comptences table.
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

    	try
		{
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
			System.out.println("Error happened in MemberRepository at getSingleMemberFRomDBWithID(): " + e.getMessage());
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
    	String getIdQuery = "SELECT Member_ID FROM taskz.members WHERE Email = ? AND Password = ?";
    	int id = 0;
    	try
		{
    		preparedStatement = ConnectionService.getConnection().prepareStatement(getIdQuery);
    		preparedStatement.setString(1, Email);
    		preparedStatement.setString(2, Password);
    		
    		ResultSet resultSet = preparedStatement.executeQuery();
    		
    		while (resultSet.next()) {
    			id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Error happened in MemberRepository at getActiveUserIDFromDB(): " + e.getMessage());
		}
    	
    	return id;
	}

}
