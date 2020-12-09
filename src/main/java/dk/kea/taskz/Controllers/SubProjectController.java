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
	 * Postmapping der modtager et subproject id fra subprojects.html og sender dette id videre til subproject repository,
	 * som sletter det tilhørende subproject og tasks fra databasen.
	 * /RBP
	 * @param data
	 * @return
	 */
	@PostMapping("/deleteSubProject")
	public String deleteSubProject(WebRequest data)
	{
		String id = data.getParameter("deleteSubProject");
		subprojectService.deleteSubProject(Integer.valueOf(id));

		subprojectService.updateSubprojectTotalEstimatedTime();

		subprojectList = subprojectService.getAllSubprojects();

		subprojectService.updateWorkloadPerDay(subprojectList);

		projectService.updateProjectEstimatedTime();

		projectList = projectService.getAllProjects();

		projectService.updateWorkloadPerDay(projectList);

		return "redirect:/subprojects";
	}

	/**
	 * - OVO
	 *  Sætter getmapping på subprojects
	 * @param model
	 * @return
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
	 * Åbner newtask pop up vinduet.
	 * @param model
	 * @return
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

		LocalDate date = LocalDate.now();

		model.addAttribute("date", date);
		model.addAttribute("deadlineIsAfterStartDate", deadlineIsAfterStartDate);

		return "subprojects";
	}

	/**
	 * - OVO
	 * Loader data ind fra new project
	 * @param data
	 * @return
	 */
	@PostMapping("/postNewSubproject")
	public String newSubproject(WebRequest data) {
		if (activeProjectID == -1)
			return "redirect:/projects";

		String subprojectStartDate = data.getParameter("subprojectStartDate");
		LocalDate convertedSubprojectStartDate = LocalDate.parse(subprojectStartDate);

		String subprojectDeadline = data.getParameter("subprojectDeadline");
		LocalDate convertedSubprojectDeadline = LocalDate.parse(subprojectDeadline);



		if (convertedSubprojectDeadline.compareTo(convertedSubprojectStartDate) < 0) {
			deadlineIsAfterStartDate = true;
			return "redirect:/newSubProject";
		} else {
			String subprojectName = data.getParameter("subprojectName");
			Subproject subproject = new Subproject(subprojectName, activeProjectID, convertedSubprojectStartDate, convertedSubprojectDeadline);

			if(timeService.isSubprojectStartDateAndDeadlingBetweenProject(projectService.getProjectByProjectId(activeProjectID), subproject) == false){
				return "redirect:/newSubProject";
			}
			else{
				subprojectService.createSubproject(subproject);
				return "redirect:/subprojects";
			}

		}
	}

	/**
	 * Postmapping der både modtager et subprojectId som der er blevet trykket på "Delete" ved og derefter sætter Model
	 * attributter som kan tilgås via HTML.
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
		model.addAttribute("subprojectList",subprojectService.getAllAssociatedSubprojects(activeProjectID));
		model.addAttribute("deletePopUp", true);
		model.addAttribute("stopScroll", true);

		return "subprojects";
	}

	@PostMapping("/postSeeSubproject")
	public String seeSubProject(WebRequest data) {
		activeProjectID = subprojectService.getParentId(Integer.valueOf(data.getParameter("subprojectId")));
		return "redirect:/subprojects";
	}

	/**
	 * - FMP
	 * Updates the status of a specific task, changing the display values of that element
	 * Redirects to subprojects
	 * @param data
	 * @return
	 */
	@PostMapping("/postChangeStatus")
	public String postChangeStatus(WebRequest data) {
		int idTask = Integer.parseInt(data.getParameter("changeStatus"));

		taskService.updateTaskStatus(idTask);

		subprojectService.updateSubprojectCompletedTime(activeProjectID);

		projectService.updateProjectCompletedTime(activeProjectID);

		subprojectList = subprojectService.getAllSubprojects();

		subprojectService.updateWorkloadPerDay(subprojectList);

		projectList = projectService.getAllProjects();

		projectService.updateWorkloadPerDay(projectList);

		return "redirect:/subprojects";
	}
}
