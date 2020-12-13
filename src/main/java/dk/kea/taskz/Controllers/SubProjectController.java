package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Services.ProjectService;
import dk.kea.taskz.Services.SubprojectService;
import dk.kea.taskz.Services.TaskService;
import dk.kea.taskz.Services.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SubProjectController
{
	@Autowired
	SubprojectService subprojectService;

	@Autowired
	ProjectService projectService;

	@Autowired
	TaskService taskService;

	@Autowired
	TimeService timeService;

	int activeProjectIDToTest = 1; // This one is only for the header fragment rendering.
	static int activeProjectID = -1;
	boolean deadlineIsAfterStartDate = false;

	List<Subproject> subprojectList = new ArrayList<>();
	List<Project> projectList = new ArrayList<>();
	List<Task> taskList = new ArrayList<>();

	/**
	 * Postmapping der bliver ramt efter man tr
	 * @param data
	 * @return
	 */
	@PostMapping("/postSubprojects")
	public String postSubprojects(WebRequest data)
	{
		activeProjectID = Integer.parseInt(data.getParameter("projectId"));

		return "redirect:/subprojects";
	}

	/**
	 * - RBP
	 * Postmapping der modtager et subproject id fra subprojects.html og sender dette id videre til subproject repository,
	 * som sletter det tilhørende subproject og tasks fra databasen.
	 * - FMP
	 * Updates Workload_Per_Day from new Estimated_Time values because of the deletion of the subproject
	 * @param data
	 * @return
	 */
	@PostMapping("/deleteSubProject")
	public String deleteSubProject(WebRequest data)
	{
		String id = data.getParameter("deleteSubProject");

		subprojectService.deleteSubProject(Integer.valueOf(id));
		subprojectService.updateSubprojectTotalEstimatedTime();
		subprojectService.updateWorkloadPerDay(subprojectService.getAllAssociatedSubprojectsWithoutTasks(activeProjectID));

		projectService.updateProjectEstimatedTime();
		projectService.updateWorkloadPerDayV2(projectService.getProjectByProjectId(activeProjectID));

		return "redirect:/subprojects";
	}

	/**
	 * - OVO
	 * A GetMapping for subprojects.
	 * has a bunch of model.addAttribute, which is used to load differing things, when needed.
	 *
	 * @param model
	 * @return "subprojects"
	 */
	@GetMapping("/subprojects")
	public String subprojects(Model model)
	{
		if (activeProjectID == -1) {
			return "redirect:/projects";
		}

		model.addAttribute("activeProjectID", activeProjectID);
		model.addAttribute("project", projectService.getProjectByProjectId(activeProjectID));
		model.addAttribute("popup", false);
		model.addAttribute("taskPopUp", false);
		model.addAttribute("subprojectList", subprojectService.getAllAssociatedSubprojects(activeProjectID));
		model.addAttribute("deletePopUp", false);
		model.addAttribute("stopScroll", false);
		model.addAttribute("activeProjectIDToTest", activeProjectIDToTest);

		return "subprojects";
	}

	/**
	 *  - OVO
	 * Has the same model.addAttributes as above, but chances "popup" to be true
	 * Which let thymeleaf render the popup
	 * @param model
	 * @return "subprojects"
	 */
	@GetMapping("/newSubProject")
	public String subprojectsPopUp(Model model)
	{
		if (activeProjectID == -1)
			return "redirect:/projects";
		activeProjectIDToTest = 1;

		model.addAttribute("popup", true);
		model.addAttribute("taskPopUp", false);
		model.addAttribute("project", projectService.getProjectByProjectId(activeProjectID));
		model.addAttribute("subprojectList",subprojectService.getAllAssociatedSubprojects(activeProjectID));
		model.addAttribute("stopScroll", true);
		model.addAttribute("activeProjectIDToTest", activeProjectIDToTest);

		return "subprojects";
	}

	/**
	 * - OVO
	 * Creates the subproject
	 * @param data
	 * @return "redirect:/subprojects"
	 */
	@PostMapping("/postNewSubproject")
	public String newSubproject(WebRequest data) {
		if (activeProjectID == -1)
			return "redirect:/projects";

		String subprojectStartDate = data.getParameter("subprojectStartDate");
		LocalDate convertedSubprojectStartDate = LocalDate.parse(subprojectStartDate);

		String subprojectDeadline = data.getParameter("subprojectDeadline");
		LocalDate convertedSubprojectDeadline = LocalDate.parse(subprojectDeadline);

		String subprojectName = data.getParameter("subprojectName");

		if (timeService.isSubprojectStartDateAndDeadlineBetweenProject(projectService.getProjectByProjectId(activeProjectID), convertedSubprojectStartDate, convertedSubprojectDeadline) == false
				|| timeService.isDeadlineBeforeStartDate(convertedSubprojectStartDate, convertedSubprojectDeadline) == false || subprojectName.equals(" ")) {

			return "redirect:/newSubProject";
		}

		Subproject subproject = new Subproject(subprojectName, activeProjectID, convertedSubprojectStartDate, convertedSubprojectDeadline);
		subprojectService.createSubproject(subproject);
		return "redirect:/subprojects";
	}

	/**
	 * - OVO 
	 * 	PostMapping that takes in a WebRequest and Model. it gets the specific ID for the subproject to delete
	 * 	And then loads all the background attributes.
	 * @param model
	 * @param data
	 * @return
	 */
	@PostMapping("/deleteSubProjectPopUp")
	public String deleteSubProjectPopUp(Model model, WebRequest data)
	{
		if (activeProjectID == -1)
			return "redirect:/projects";

		String subprojectToDelete = data.getParameter("subprojectToDelete");

		activeProjectIDToTest = 1;

		model.addAttribute("subprojectToDelete", subprojectToDelete);
		model.addAttribute("activeProjectID", activeProjectID);
		model.addAttribute("project", projectService.getProjectByProjectId(activeProjectID));
		model.addAttribute("popup", false);
		model.addAttribute("taskPopUp", false);
		model.addAttribute("subprojectList", subprojectService.getAllAssociatedSubprojects(activeProjectID));
		model.addAttribute("deletePopUp", true);
		model.addAttribute("stopScroll", true);

		return "subprojects";
	}

	/**
	 * - OVO
	 * Gets the redirect from project.html
	 * And then recives the activeProjectID and sets it globally in the subprojectController
	 *
	 * @param data
	 * @return
	 */
	@PostMapping("/postSeeSubproject")
	public String seeSubProject(WebRequest data) {
		activeProjectID = subprojectService.getParentId(Integer.valueOf(data.getParameter("subprojectId")));
		return "redirect:/subprojects";
	}

	/**
	 * - FMP
	 * Updates the status of a specific task, changing the calculation for completedTime and Workload_Per_Day
	 * Updates Workload_Per_Day based of the newly updated values
	 * @param data
	 * @return
	 */
	@PostMapping("/postChangeStatus")
	public String postChangeStatus(WebRequest data) {
		int idTask = Integer.parseInt(data.getParameter("changeStatus"));

		taskService.updateTaskStatus(idTask);

		subprojectService.updateSubprojectCompletedTime(activeProjectID);

		projectService.updateProjectCompletedTime(activeProjectID);

		subprojectService.updateWorkloadPerDay(subprojectService.getAllAssociatedSubprojectsWithoutTasks(activeProjectID));

		projectService.updateWorkloadPerDayV2(projectService.getProjectByProjectId(activeProjectID));

		return "redirect:/subprojects";
	}
}
