package dk.kea.taskz.Services;

import dk.kea.taskz.Repositories.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CompetenceService {

	@Autowired
	CompetenceRepository competenceRepository;

	/**
	 * returns all the competences
	 *
	 * @return
	 */
	public ArrayList<String> getAllCompetences() {
		return competenceRepository.getAllSkillsFromDB();
	}

}
