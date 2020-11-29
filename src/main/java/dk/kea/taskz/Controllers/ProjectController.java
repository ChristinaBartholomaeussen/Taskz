package dk.kea.taskz.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectController {

	
	/**
	 * Getmapping for project. 
	 * Sætter en model attribute med en boolean som styre om popup html skal være aktiv
	 * @param model
	 * @return projects
	 */
	@GetMapping("/projects")
	public String projects(Model model) {
		model.addAttribute("popup", false);
		return "projects";
	}

	/**
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
}
