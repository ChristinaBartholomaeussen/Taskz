package dk.kea.taskz.Repositories;

import dk.kea.taskz.Services.ConnectionService;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class CompetenceRepository
{
	PreparedStatement preparedStatement = null;

	/**
	 * - OVO Gets all the competences from the database
	 *
	 * @return
	 */
	public ArrayList<String> getAllSkillsFromDB() {

		String listOfCompetences = "SELECT competence FROM competences";

		ArrayList<String> competencesList = new ArrayList<>();

		try {
			preparedStatement = ConnectionService.getConnection().prepareStatement(listOfCompetences);
	
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				competencesList.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			System.out.println("Class: CompetencesRepository\nMethod: getAllCompetencesOnceFromDB()\nError: " +e.getMessage());
		}
		return competencesList;
	}
	
}
