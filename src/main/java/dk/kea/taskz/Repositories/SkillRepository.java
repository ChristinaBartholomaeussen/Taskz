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
	 * Gets all the competences from the database
	 *
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
			System.out.println("Error happened in CompetencesRepository at getAllSkillsFromDB(): " +e.getMessage());
		}
		return skills;
	}
}
