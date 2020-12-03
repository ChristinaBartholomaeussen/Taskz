package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Enums.Complexity;
import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Repositories.CompetancesRepository;
import dk.kea.taskz.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CompetanceService {
	
	@Autowired
	CompetancesRepository competancesRepository;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	TaskRepository taskRepository;
	
	public ArrayList<String> getAllCompetances() {
		return competancesRepository.getAllCompetancesOnceFromDB(); 
	}
	
	public boolean calculateIfTaskIsWrongAssigned(int memberId, Task task) {

		if (!memberService.getAllCompetance(memberId).contains(task.getTag()) && task.getComplexity() == Complexity.HARD || task.getComplexity() == Complexity.VERY_HARD) {
			taskRepository.setATaskToRelocateResources();
			return true;
		}
		return false;
	}
}
