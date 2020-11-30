package dk.kea.taskz.Controllers;

import dk.kea.taskz.Services.SubprojectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class SubProjectController
{
	@Autowired
	SubprojectService subprojectService;

	int activeProject = -1;

	@PostMapping("/postSubprojects")
	public String postSubprojects(WebRequest data)
	{
		activeProject = Integer.parseInt(data.getParameter("projectId"));

		return "subprojects";
	}

	/**
	 * - OVO
	 *  Sætter getmapping på subprojects
	 * @param model
	 * @return
	 */
	@GetMapping("/subprojects")
	public String subproject(Model model) {
		model.addAttribute("popup", false);
		model.addAttribute("taskPopUp", false);
		model.addAttribute("subprojectList",subprojectService.getAllAssociatedSubprojects(activeProject));

		return "subprojects";
	}

	/**
	 *  - OVO
	 * Åbner newtask pop up vinduet.
	 * @param model
	 * @return
	 */
	@GetMapping("/newSubProject")
	public String subprojectsPopUp(Model model) {
		model.addAttribute("popup", true);
		model.addAttribute("taskPopUp", false);
		return "subprojects";
	}

	/**
	 * - OVO
	 * Loader data ind fra new project
	 * @param data
	 * @return
	 */
	@PostMapping("/postNewSubproject")
	public String newSubproject(WebRequest data){
		System.out.println(data.getParameter("newSubProject"));
		return "redirect:/subprojects";
	}


}
