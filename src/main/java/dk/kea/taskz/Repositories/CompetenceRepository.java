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
	
	
	public ArrayList<String> getAllCompetencesOnceFromDB() {
		String listOfCompetences = "SELECT DISTINCT Competance FROM taskz.competances";
		ArrayList<String> competencesList = new ArrayList<>();
		
		try {
			PreparedStatement preparedStatement = ConnectionService.getConnection().prepareStatement(listOfCompetences);
	
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				competencesList.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			System.out.println("Klasse: CompetencesRepository\nMethode: getAllCompetencesOnceFromDB()\nError: " +e.getMessage());
		}
		return competencesList;
	}
	
}
