package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ProjectController {

	ProjectService projectService = new ProjectService();
	List<Project> projectList = projectService.getProjectByIdNameDeadlineEstimatedTime();
	boolean deadlineIsAfterStartDate = false;
	int activeProjectIDToTest = 0; // This one is only for the header fragment rendering.

	/**
	 * Needs to be initialized, but the default value is -1.
	 * If we hit a breakpoint here and the value still is -1, we know there is no activeproject
	 * recieved from the LoginController mapping.
	 */
	int activeProjectID = -1;

	/**
	 * - OVO
	 * Getmapping for project. 
	 * Sætter en model attribute med en boolean som styre om popup html skal være aktiv
	 * - FMP
	 * Sætter en model attribute, til og hente en liste af alle projekter fra databasen.
	 * @param model
	 * @return projects
	 */
	@GetMapping("/projects")
	public String projects(Model model)
	{
		projectList = projectService.getProjectByIdNameDeadlineEstimatedTime();

		model.addAttribute("activeProjectID", activeProjectID);
		model.addAttribute("activeProjectIDToTest", activeProjectIDToTest);
		model.addAttribute("popup", false);
		model.addAttribute("projectList", projectList);
		model.addAttribute("deletePopUp", false);

		return "projects";
	}

	/**
	 * - OVO
	 * En ny getmapping som bliver kaldt af "New Projects" linket i project siden.
	 * Egentlig returnere den projekt siden igen, men også sætter boolean til true.
	 * Det gør at popup bliver aktiv.
	 * - FMP
	 * Tilføjet date attribute, såfremt at minimums datoen bliver sat til i dag.
	 * Tilføjet deadLineIsAfterStartDate, for at undgå at deadline bliver sat før start datoen
	 * @param model
	 * @return projects
	 */
	@GetMapping("/newProject")
	public String newProject(Model model) {

		model.addAttribute("popup", true);
		model.addAttribute("projectList", projectList);

		LocalDate date = LocalDate.now();

		model.addAttribute("date", date);
		model.addAttribute("deadlineIsAfterStartDate", deadlineIsAfterStartDate);

		return "projects";
	}

	/**
	 * - FMP
	 * Postmapping for 'create new project'
	 * Sender et projekt objekt til databasen, hvori objektet bliver gemt
	 * Konvertering fra html date datatype (String) til LocalDate
	 * If skal sikre at man ikke sætter deadline, før projekt start, dette udløser en pop up, med en fejlbesked
	 * @param projectData
	 * @return redirect:/projects
	 */

	@PostMapping("/postNewProject")
	public String postNewProject(WebRequest projectData) {

		String projectStartDate = projectData.getParameter("projectStartDate");
		LocalDate convertedProjectStartDate = LocalDate.parse(projectStartDate);

		String projectDeadline = projectData.getParameter("projectDeadline");
		LocalDate convertedProjectDeadline = LocalDate.parse(projectDeadline);

		if (convertedProjectDeadline.compareTo(convertedProjectStartDate) < 0) {
			deadlineIsAfterStartDate = true;
			return "redirect:/newProject";
		} else {

			Project newProject = new Project(projectData.getParameter("projectName"), convertedProjectStartDate, convertedProjectDeadline);

			projectService.addProjectToDatabase(newProject);

			return "redirect:/projects";
		}
	}

	/**
	 * - FMP/RBP
	 * Postmapping for 'delete project'
	 * Henter en liste over alle projekter, hvorefter den henter projectId fra objektet
	 * Herefter looper vi igennem listen, og forsøger at finde, hvor projektId'et matcher med det projektId, vi gerne vil slette
	 * Hvis det er fundet sletter metoden projektet fra databasen
	 * @param deleteProjectData
	 * @return
	 */

	@PostMapping("/deleteProject")
	public String deleteProject(WebRequest deleteProjectData) {

		List<Project> projectList = projectService.getProjectByIdNameDeadlineEstimatedTime();

		int projectToDeleteId = Integer.parseInt(deleteProjectData.getParameter("projectId"));

		for(Project project : projectList)
			if(project.getProjectId() == projectToDeleteId) {
				projectService.deleteProject(projectToDeleteId);
			}

		return "redirect:/projects";
	}
	
	@PostMapping("/postpopupDeleteProject")
	public String postpopupDeleteProject(WebRequest data) {
		activeProjectID = Integer.valueOf(data.getParameter("activeProjectId"));
		return "redirect:/deletePopup";
	}

		projectService.updateWorkloadPerDay(projectList);

	@GetMapping("/deletePopup")
	public String deletePopip(Model model) {
		projectList = projectService.getProjectByIdNameDeadlineEstimatedTime();

		model.addAttribute("activeProjectID", activeProjectID);
		model.addAttribute("activeProjectIDToTest", activeProjectIDToTest);
		model.addAttribute("popup", false);
		model.addAttribute("projectList", projectList);
		model.addAttribute("deletePopUp", true);
		return "projects";
	}
}
