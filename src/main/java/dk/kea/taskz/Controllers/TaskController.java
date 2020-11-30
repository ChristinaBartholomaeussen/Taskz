package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Enums.Complexity;
import dk.kea.taskz.Models.Enums.Priority;
import dk.kea.taskz.Models.Enums.Status;
import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@Controller
public class TaskController {

	//Slet efter test
	TaskService ts = new TaskService();
	
	/**
	 *  - OVO
	 * Sætter en popup op for at lave en ny task.
	 * @param model
	 * @return subprojects
	 */
	@GetMapping("/newTask")
	public String taskPopUp(Model model) {
		model.addAttribute("popup", false);
		model.addAttribute("TaskPopUp", true);
		return "subprojects";
	}
	
	@PostMapping("/newTaskData")
	public String newTaskData(WebRequest data) {
		String subprojectId = data.getParameter("subProjectId");
		String taskName = data.getParameter("taskName");
		int priority = Integer.parseInt(data.getParameter("priority"));
		int complexity = Integer.parseInt(data.getParameter("complexity"));
		String estimatedTime = data.getParameter("estimatedTime");
		String deadline = data.getParameter("deadline");
		String teamMembers = data.getParameter("TeamMembers");

		//System.out.println("Subproject ID: " + subprojectId + "\nTask name: "+ taskName + "\nPriority: " + priority + "\nComplexity: " + complexity + "\nEstimated time: " + estimatedTime + "\nDeadline: " + deadline + "\nTeammembers: " + teamMembers);
		
		Task task = new Task(Integer.valueOf(subprojectId), taskName, Priority.values()[priority], Complexity.values()[complexity], LocalDate.parse(deadline),  Double.valueOf(estimatedTime), Status.INACTIVE);
		ts.insertTask(task);

		System.out.println(task);
		return "redirect:/subprojects";
	}
}
