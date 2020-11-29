package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

	ProjectService projectService = new ProjectService();

	List<Project> projectList = projectService.getProjectList();
	
	/**
	 * - OVO
	 * Getmapping for project. 
	 * Sætter en model attribute med en boolean som styre om popup html skal være aktiv
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
	 * @param model
	 * @return projects
	 */
	@GetMapping("/newProject")
	public String newProject(Model model){
		model.addAttribute("popup", true);
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

		//projectList.add(new Project(projectData.getParameter("projectName"), projectData.getParameter(), projectData.getParameter("projectDeadline")));

		return "projects";
	}

	@PostMapping("/deleteProject")
	public String deleteProject(WebRequest data)
	{
		int projectToDeleteId = Integer.parseInt(data.getParameter("projectId"));

		for(Project project : projectList)
			if(project.getProjectId() == projectToDeleteId)
				System.out.println("Deleting project: " + project.getName());

		return "redirect:/projects";
	}
}
