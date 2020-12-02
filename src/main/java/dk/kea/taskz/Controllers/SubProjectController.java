package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Services.SubprojectService;
import dk.kea.taskz.Services.TaskService;
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

	@Autowired
	TaskService taskService;

	int activeProjectIDToTest = 1; // This one is only for the header fragment rendering.
	int activeProjectID = -1;
	String projectTotalEstimatedTime;

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
	
	@PostMapping("/deleteSubProject")
	public String deleteSubProject(WebRequest data)
	{
		String id = data.getParameter("deleteSubProject");
		subprojectService.deleteSubProject(Integer.valueOf(id));
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
			System.out.println("Redirects to /projects. No valid project id");
			return "redirect:/projects";
		}
		model.addAttribute("activeProjectID", activeProjectID);
		model.addAttribute("projectName", subprojectService.getParentProjectName(activeProjectID));
		model.addAttribute("popup", false);
		model.addAttribute("taskPopUp", false);
		model.addAttribute("subprojectList",subprojectService.getAllAssociatedSubprojects(activeProjectID));
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

		model.addAttribute("popup", true);
		model.addAttribute("taskPopUp", false);
		model.addAttribute("subprojectList",subprojectService.getAllAssociatedSubprojects(activeProjectID));
		model.addAttribute("stopScroll", true);
		return "subprojects";
	}

	/**
	 * - OVO
	 * Loader data ind fra new project
	 * @param data
	 * @return
	 */
	@PostMapping("/postNewSubproject")
	public String newSubproject(WebRequest data)
	{
		if (activeProjectID == -1)
			return "redirect:/projects";

		String subProjectName = data.getParameter("newSubProject");
		Subproject subproject = new Subproject(subProjectName, activeProjectID);
		subprojectService.createSubproject(subproject);

		return "redirect:/subprojects";
	}
	
	@GetMapping("/deleteSubProjectPopUp")
	public String deleteSubProjectPopUp(Model model)
	{
		if (activeProjectID == -1)
			return "redirect:/projects";

		model.addAttribute("activeProjectID", activeProjectID);
		model.addAttribute("projectName", subprojectService.getParentProjectName(activeProjectID));
		model.addAttribute("popup", false);
		model.addAttribute("taskPopUp", false);
		model.addAttribute("subprojectList",subprojectService.getAllAssociatedSubprojects(activeProjectID));
		model.addAttribute("deletePopUp", true);
		model.addAttribute("stopScroll", true);

		return "subprojects";
	}
}
