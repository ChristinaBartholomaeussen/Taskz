package dk.kea.taskz.Services;

import dk.kea.taskz.Repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

	@Autowired
	SkillRepository skillRepository;

	/**
	 * returns all the competences
	 *
	 * @return
	 */

	public List<String> getListOfSkills(){
		return skillRepository.getAllSkillsFromDB();
	}

}
