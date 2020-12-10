package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Services.ProjectService;
import dk.kea.taskz.Services.SubprojectService;
import dk.kea.taskz.Services.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	SubprojectService subprojectService;

	@Autowired
	TimeService timeService;

	List<Project> projectList;

	List<Subproject> subprojectList;
	boolean deadlineIsAfterStartDate = false;

	/**
	 * Used only for the header fragment rendering.
	 */
	int headerDisplayFlag = 0;

	/**
	 * Needs to be initialized, but the default value is -1.
	 * If we hit a breakpoint here and the value still is -1, we know there is no activeproject
	 * recieved from the LoginController mapping.
	 */
	int activeProjectID = -1;

	/**
	 * - OVO
	 * Getmapping for project.
	 * Sets different addtributes, så the subproject page reacts right.
	 * - FMP
	 * Updates Workload_Per_Day for projects and subprojects to insure that the displayed information is updated
	 * and accurate
	 *
	 * @param model
	 * @return projects
	 */

	@GetMapping("/projects")
	public String projects(Model model)
	{
		projectList = projectService.getAllProjects();
		projectService.updateWorkloadPerDay(projectList);
		subprojectList = subprojectService.getAllSubprojects();
		subprojectService.updateWorkloadPerDay(subprojectList);

		model.addAttribute("activeProjectID", activeProjectID);
		model.addAttribute("activeProjectIDToTest", headerDisplayFlag);
		model.addAttribute("popup", false);
		model.addAttribute("projectList", projectList);
		model.addAttribute("deletePopUp", false);

		return "projects";
	}

	/**
	 * - RBP
	 * Postmapping for 'delete project'
	 * -FMP
	 * Gets a list of all projects, then collects the associated projectID from the project we wish to delete
	 * Loops through the list and removes the project if the projectID matches any of the projectID's in the list
	 * @param deleteProjectData
	 * @return
	 */
	@PostMapping("/deleteProject")
	public String deleteProject(WebRequest deleteProjectData) {

		List<Project> projectList = projectService.getAllProjects();

		int projectToDeleteId = Integer.parseInt(deleteProjectData.getParameter("projectId"));

		for(Project project : projectList)
			if(project.getProjectId() == projectToDeleteId) {
				projectService.deleteProject(projectToDeleteId);
			}

		return "redirect:/projects";
	}

	/**
	 * - OVO
	 * En ny getmapping som bliver kaldt af "New Projects" linket i project siden.
	 * Egentlig returnere den projekt siden igen, men også sætter boolean til true.
	 * Det gør at popup bliver aktiv.
	 * @return projects
	 */
	@GetMapping("/newProject")
	public String newProject(Model model) {

		model.addAttribute("popup", true);
		model.addAttribute("projectList", projectService.getAllProjects());

		return "projects";
	}

	/**
	 * - FMP
	 * Postmapping for 'create new project'
	 * Inserts a project into the database
	 * Checks the start date and deadline of the project, to insure that out rules are met
	 * @param projectData
	 * @return redirect:/projects
	 */

	@PostMapping("/postNewProject")
	public String postNewProject(WebRequest projectData) {

		String projectStartDate = projectData.getParameter("projectStartDate");
		String projectDeadline = projectData.getParameter("projectDeadline");


		if (timeService.isDeadlineBeforeStartDate(LocalDate.parse(projectStartDate), LocalDate.parse(projectDeadline)) == false) {
			return "redirect:/newProject";

		} else {

			Project newProject = new Project(projectData.getParameter("projectName"), LocalDate.parse(projectStartDate), LocalDate.parse(projectDeadline));
			projectService.addProjectToDatabase(newProject);
			return "redirect:/projects";
		}
	}

	/**
	 * Postmapping der modtager et Integer fra projects.html, som bruges til at designere det aktive project
	 * og derefter redirectes videre til deleteProjectPopup-mappingen som bruger det aktive project ID til at vide hvilket
	 * project der er "peget" på og skal slettes
	 * deleteProjectPopup-get
	 * @param data
	 * @return
	 */

	@PostMapping("/postpopupDeleteProject")
	public String postpopupDeleteProject(WebRequest data) {
		activeProjectID = Integer.valueOf(data.getParameter("activeProjectId"));
		return "redirect:/deletePopup";
	}

	/**
	 * Mapping der har det aktive projekt id
	 * @param model
	 * @return
	 */
	@GetMapping("/deletePopup")
	public String deletePopip(Model model) {
		model.addAttribute("activeProjectID", activeProjectID);
		model.addAttribute("activeProjectIDToTest", headerDisplayFlag);
		model.addAttribute("popup", false);
		model.addAttribute("projectList", projectService.getAllProjects());
		model.addAttribute("deletePopUp", true);
		return "projects";
	}


}
