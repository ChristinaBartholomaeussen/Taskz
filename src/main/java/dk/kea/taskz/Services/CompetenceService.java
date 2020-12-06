package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Enums.Complexity;
import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Repositories.CompetenceRepository;
import dk.kea.taskz.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CompetenceService
{
	
	@Autowired
	CompetenceRepository competenceRepository;
	
	public ArrayList<String> getAllCompetences() {
		return competenceRepository.getAllCompetencesOnceFromDB();
	}
	
}
