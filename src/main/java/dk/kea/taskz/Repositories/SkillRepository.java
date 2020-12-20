package dk.kea.taskz.Repositories;

import dk.kea.taskz.Services.ConnectionService;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class SkillRepository
{
	PreparedStatement preparedStatement = null;

	/**
	 * - CMB
	 * Selects the skill_description from the table skills
	 * and adds them to an arraylist of strings.
	 * After that it returns the list for further use.
	 * @return
	 */
	public ArrayList<String> getAllSkillsFromDB() {

		String listOfCompetences = "select skills.skill_description\n" +
				" from skills";

		ArrayList<String> skills = new ArrayList<>();

		try {
			preparedStatement = ConnectionService.getConnection().prepareStatement(listOfCompetences);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String skill = resultSet.getString(1);
				skills.add(skill);
			}
		} catch (SQLException e) {
			System.out.println("Error happened in SkillRepository at getAllSkillsFromDB(): " +e.getMessage());
		}
		return skills;
	}
}
