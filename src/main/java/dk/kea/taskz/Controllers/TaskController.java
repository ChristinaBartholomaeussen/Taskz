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

	TaskService taskService = new TaskService();
	int subprojectsID = -1;
	
	@PostMapping("/newTask")
	public String newTaskPost(WebRequest data) {
		subprojectsID = Integer.parseInt(data.getParameter("subprojectsID"));
		return "redirect:/newTask";
	}
	
	/**
	 *  - OVO
	 * Activates the pop up for new task.
	 * @param model
	 * @return subprojects
	 */
	@GetMapping("/newTask")
	public String taskPopUp(Model model) {
		model.addAttribute("subprojectsID", subprojectsID);
		model.addAttribute("popup", false);
		model.addAttribute("TaskPopUp", true);
		return "subprojects";
	}

	/**
	 *  - OVO
	 *  Send from data to the database. To create an task.
	 * @param data
	 * @return
	 */
	@PostMapping("/newTaskData")
	public String newTaskData(WebRequest data) {
		String subprojectId = data.getParameter("subProjectId");
		String taskName = data.getParameter("taskName");
		int priority = Integer.parseInt(data.getParameter("priority"));
		int complexity = Integer.parseInt(data.getParameter("complexity"));
		String estimatedTime = data.getParameter("estimatedTime");
		String deadline = data.getParameter("deadline");
		String member = data.getParameter("TeamMembers");

		Task task = new Task(Integer.valueOf(subprojectId), taskName, Priority.values()[priority], Complexity.values()[complexity], LocalDate.parse(deadline),  Double.valueOf(estimatedTime), Status.INACTIVE, member);
		taskService.insertTask(task);
		
		return "redirect:/subprojects";
	}


	/**
	 *  - OVO
	 *  Sletter en task fra databasen.
	 * @param data
	 * @return
	 */
	@PostMapping("/deleteTask")
	public String deleteTask(WebRequest data) {
		int idTask = Integer.parseInt(data.getParameter("deleteTask"));
		taskService.deleteTask(idTask);
		return "redirect:/subprojects";
	}

	/**
	 *  - OVO 
	 *  Retunere en enkelt task. Men methoden skal ikke se sådan her ud.
	 *  Det her er bare et eksempel.
	 */
	
	/*
	@GetMapping("/getSingleTask")
	public String getSingleTask() {
		
		System.out.println(taskService.getASpecificTask(3));
		return "subprojects";
	} */

}
