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
	public String projects(Model model) {


		model.addAttribute("popup", false);
		model.addAttribute("projectList", projectList);

		return "projects";
	}

	/**
	 * - OVO
	 * En ny getmapping som bliver kaldt af "New Projects" linket i project siden.
	 * Egentlig returnere den projekt siden igen, men også sætter boolean til true.
	 * Det gør at popup bliver aktiv.
	 * - FMP
	 * Tilføjet date attribute, såfremt at minimums datoen bliver sat til i dag.
	 * @param model
	 * @return projects
	 */
	@GetMapping("/newProject")
	public String newProject(Model model) {


		model.addAttribute("popup", true);
		model.addAttribute("projectList", projectList);

		LocalDate date = LocalDate.now();

		model.addAttribute("date", date);

		return "projects";
	}

	/**
	 * DO NOT TOUCH - ONGOING - FREDE
	 * @param projectData
	 * @return
	 */

	@PostMapping("/postNewProject")
	public String postNewProject(WebRequest projectData) {

		String projectStartDate = projectData.getParameter("projectStartDate");

		LocalDate convertedProjectStartDate = LocalDate.parse(projectStartDate);

		String projectDeadline = projectData.getParameter("projectDeadline");

		LocalDate convertedProjectDeadline = LocalDate.parse(projectDeadline);

		Project newProject = new Project(projectData.getParameter("projectName"), convertedProjectStartDate, convertedProjectDeadline);

		projectService.addProjectToDatabase(newProject);

		return "redirect:/projects";
	}

	@PostMapping("/deleteProject")
	public String deleteProject(WebRequest data) {

		List<Project> projectList = projectService.getProjectByIdNameDeadlineEstimatedTime();

		int projectToDeleteId = Integer.parseInt(data.getParameter("projectId"));

		for(Project project : projectList)
			if(project.getProjectId() == projectToDeleteId)
				System.out.println("Deleting project: " + project.getName());

		return "redirect:/projects";
	}
}
