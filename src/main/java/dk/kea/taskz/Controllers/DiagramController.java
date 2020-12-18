package dk.kea.taskz.Controllers;

import dk.kea.taskz.Services.CookieService;
import dk.kea.taskz.Services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DiagramController {
	@Autowired
	ProjectService projectService;

	@Autowired
	CookieService cookieService;

	/**
	 * - RBP
	 * Getmapping for Gantt chart for all projects, subprojects and tasks.
	 * Calls ProjectService to get a list of all existing Projects and populates each Project subproject list with Subprojects and
	 * the corresponding Tasks.
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/gantt")
	public String gant(Model model, HttpServletRequest request)
	{
		int activeUserId = cookieService.getActiveUserId(request);

		if (activeUserId == -1) {
			return "redirect:/login";
		}

		model.addAttribute("projects", projectService.getAllProjectsIncludingAssociatedSubprojectsAndTasks());

		return "/gantt";
	}
}
