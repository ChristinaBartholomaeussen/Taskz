package dk.kea.taskz.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class SubProjectController {

	/**
	 * - OVO
	 *  Sætter getmapping på subprojects
	 * @param model
	 * @return
	 */
	@GetMapping("/subprojects")
	public String subProject(Model model) {
		model.addAttribute("popup", false);
		model.addAttribute("taskPopUp", false);
		return "subprojects";
	}

	/**
	 *  - OVO
	 * Åbner newtask pop up vinduet.
	 * @param model
	 * @return
	 */
	@GetMapping("/newSubProject")
	public String subProjectsPopUp(Model model) {
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
	public String newSubProject(WebRequest data){
		System.out.println(data.getParameter("newSubProject"));
		return "redirect:/subprojects";
	}


}
