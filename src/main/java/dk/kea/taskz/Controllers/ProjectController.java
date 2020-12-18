package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Services.CookieService;
import dk.kea.taskz.Services.ProjectService;
import dk.kea.taskz.Services.SubprojectService;
import dk.kea.taskz.Services.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpServletRequest;
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

	@Autowired
	CookieService cookieService;

	List<Project> projectList;
	List<Subproject> subprojectList;

	/**
	 * - RBP
	 * Needs to be initialized, but the default value is -1.
	 * If we hit a breakpoint here and the value still is -1, we know there is no activeproject
	 * recieved from the LoginController mapping.
	 */
	int activeProjectID = -1;

	/**
	 * - OVO / FMP
	 * Getmapping for project.
	 * Sets different addtributes, så the subproject page reacts right.
	 *
	 * Updates Workload_Per_Day for projects and subprojects to insure that the displayed information is updated
	 * and accurate
	 *
	 * @param model
	 * @return projects
	 */
	@GetMapping("/projects")
	public String projects(Model model, HttpServletRequest request) {

		int activeUserId = cookieService.getActiveUserId(request);

		if (activeUserId == -1) {
			return "redirect:/login";
		}

		projectService.updateAllProjectsWorkloadPerDay();
		subprojectService.updateWorkloadPerDay(subprojectList);

		model.addAttribute("activeProjectID", activeProjectID);
		model.addAttribute("projectList", projectService.getAllProjects());
		model.addAttribute("popup", false);
		model.addAttribute("deletePopUp", false);

		return "projects";
	}

	/**
	 * - RBP + FMP
	 * Postmapping for 'delete project'.
	 * Gets a list of all projects, then collects the associated projectID from the project we wish to delete
	 * Loops through the list and removes the project if the projectID matches any of the projectID's in the list
	 * @param deleteProjectData
	 * @return
	 */
	@PostMapping("/deleteProject")
	public String deleteProject(WebRequest deleteProjectData)
	{
		List<Project> projectList = projectService.getAllProjects();
		int projectToDeleteId = Integer.parseInt(deleteProjectData.getParameter("projectId"));

		for(Project project : projectList)
		{
			if(project.getProjectId() == projectToDeleteId)
			{
				projectService.deleteProject(projectToDeleteId);
			}
		}

		return "redirect:/projects";
	}

	/**
	 * - OVO
	 * A Getmapping for "new Project" and sets the Popup model attribute to become true, so the popup works.
	 * Adds a list to the Model, which contains all existing Projects.
	 *
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
	 * Inserts a project into the database. Checks the start date,deadline and name of the project, to ensure that our
	 * naming rules are met.
	 *
	 * @param projectData
	 * @return redirect:/projects
	 */
	@PostMapping("/postNewProject")
	public String postNewProject(WebRequest projectData) {

		String projectStartDate = projectData.getParameter("projectStartDate");
		String projectDeadline = projectData.getParameter("projectDeadline");

		if (timeService.isDeadlineBeforeStartDate(LocalDate.parse(projectStartDate), LocalDate.parse(projectDeadline)) == false || projectData.getParameter("projectName").equals(" ")) {
			return "redirect:/newProject";

		} else {

			Project newProject = new Project(projectData.getParameter("projectName"), LocalDate.parse(projectStartDate), LocalDate.parse(projectDeadline));
			projectService.addProjectToDatabase(newProject);
			return "redirect:/projects";
		}
	}

	/**
	 * - OVO
	 * A postmapping that takes an Integer from the projects.html page. It is used to store the project Id, which we
	 * use to delete the specific project.
	 * @param data
	 * @return
	 */
	@PostMapping("/postpopupDeleteProject")
	public String postpopupDeleteProject(WebRequest data)
	{
		activeProjectID = Integer.valueOf(data.getParameter("activeProjectId"));
		return "redirect:/deletePopup";
	}

	/**
	 * - OVO
	 * A getmapping for the deleteproject popup.
	 * The model recieves the activeProjectId, a list of all existing Projects and sets the popup and deletePopUp attributes.
	 * @param model
	 * @return
	 */
	@GetMapping("/deletePopup")
	public String deletePopip(Model model) {
		model.addAttribute("activeProjectID", activeProjectID);
		model.addAttribute("projectList", projectService.getAllProjects());
		model.addAttribute("popup", false);
		model.addAttribute("deletePopUp", true);
		return "projects";
	}
}
