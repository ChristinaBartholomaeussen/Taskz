package dk.kea.taskz.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class TaskController {

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
		String taskName = data.getParameter("taskName");
		String priority = data.getParameter("priority");
		String complexity = data.getParameter("complexity");
		String estimatedTime = data.getParameter("estimatedTime");
		String deadline = data.getParameter("deadline");
		String teamMembers = data.getParameter("TeamMembers");

		System.out.println("Task name: "+ taskName + "\nPriority: " + priority + "\nComplexity: " + complexity + "\nEstimated time: " + estimatedTime + "\nDeadline: " + deadline + "\nTeammembers: " + teamMembers);
		
		return "redirect:/subprojects";
	}
}
