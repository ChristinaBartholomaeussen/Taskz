package dk.kea.taskz.Repositories;

import dk.kea.taskz.Services.ConnectionService;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class CompetancesRepository {
	
	
	public ArrayList<String> getAllCompetancesOnceFromDB() {
		String listOfCompetances = "SELECT DISTINCT Competance FROM taskz.competances";
		ArrayList<String> competancesList = new ArrayList<>();
		
		try {
			PreparedStatement preparedStatement = ConnectionService.getConnection().prepareStatement(listOfCompetances);
	
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				competancesList.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			System.out.println("Klasse: CompetancesRepository\nMethode: getAllCompetancesOnceFromDB()\nError: " +e.getMessage());
		}
		return competancesList;
	}
	
}
