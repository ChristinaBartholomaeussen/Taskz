package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
public class SubProjectController
{
	@Autowired
	SubprojectService subprojectService;

	@Autowired
	ProjectService projectService;

	@Autowired
	TaskService taskService;

	@Autowired
	TimeService timeService;

	@Autowired
	CookieService cookieService;

	private static int activeProjectID = -1;

	/**
	 * - OVO
	 * GetMapping for subprojects.
	 * To avoid direct linking to the page, without an active user or a selected Project, we check for the activeUserId and the
	 * activeProjectId. If either is -1, the user will be redirected to the corresponding page needed.
	 *
	 * The the Model recieves a bunch of attributes. The activeProjectId, the entirety of the selected Project through ProjectService
	 * and four attributes used by Thymeleaf to check if we use a GetMapping from a PopUp and disables scrolling on the specific html
	 * page.
	 *
	 * @param model
	 * @return "subprojects"
	 */
	@GetMapping("/subprojects")
	public String subprojects(Model model, HttpServletRequest request) {
		int activeUserId = cookieService.getActiveUserId(request);

		if (activeUserId == -1) {
			return "redirect:/login";
		}

		if (activeProjectID == -1) {
			return "redirect:/projects";
		}

		model.addAttribute("activeProjectID", activeProjectID);
		model.addAttribute("project", projectService.getProjectByProjectId(activeProjectID));
		model.addAttribute("subprojectList", subprojectService.getAllAssociatedSubprojectsAndAssociatedTasks(activeProjectID));
		model.addAttribute("popup", false);
		model.addAttribute("taskPopUp", false);
		model.addAttribute("deletePopUp", false);
		model.addAttribute("stopScroll", false);

		return "subprojects";
	}

	/**
	 * - OVO
	 * This postmapping gets called from the projects.html.
	 * It has an hidden input field, which is recieved from the html page and sets the activeProjectId field.
	 *
	 * @param data
	 * @return
	 */
	@PostMapping("/postSubprojects")
	public String postSubprojects(WebRequest data)
	{
		activeProjectID = Integer.parseInt(data.getParameter("projectId"));

		return "redirect:/subprojects";
	}

	/**
	 * - RBP + FMP
	 * PostMapping which sends a subproject id from subprojects.html and passes this id on to SubprojectRepository, which
	 * deletes the specific subproject and the corresponding tasks from the database.
	 *
	 * Updates Workload_Per_Day from new Estimated_Time values because of the deletion of the subproject
	 * @param data
	 * @return
	 */
	@PostMapping("/deleteSubProject")
	public String deleteSubProject(WebRequest data)
	{
		String id = data.getParameter("deleteSubProject");

		subprojectService.deleteSubProject(Integer.valueOf(id));
		subprojectService.updateSubprojectTotalEstimatedTime(Integer.valueOf(id));
		projectService.updateProjectCompletedTime(activeProjectID);

		subprojectService.updateWorkloadPerDay(subprojectService.getAllAssociatedSubprojectsWithoutTasks(activeProjectID));
		projectService.updateProjectEstimatedTime(activeProjectID);
		projectService.updateWorkloadPerDayForSpecificProject(projectService.getProjectByProjectId(activeProjectID));

		return "redirect:/subprojects";
	}

	/**
	 *  - OVO
	 * Has the same model.addAttributes as above, but changes "popup" to be true,
	 * which lets thymeleaf render the popup.
	 * @param model
	 * @return "subprojects"
	 */
	@GetMapping("/newSubProject")
	public String subprojectsPopUp(Model model)
	{
		if (activeProjectID == -1)
			return "redirect:/projects";

		model.addAttribute("project", projectService.getProjectByProjectId(activeProjectID));
		model.addAttribute("subprojectList",subprojectService.getAllAssociatedSubprojectsAndAssociatedTasks(activeProjectID));
		model.addAttribute("popup", true);
		model.addAttribute("taskPopUp", false);
		model.addAttribute("stopScroll", true);

		return "subprojects";
	}

	/**
	 * - OVO
	 * PostMapping to create a new Subproject. Redirects if no project has been selected.
	 * Calls methods in TimeService to check for valid dates and checks the Subproject named, to follow our naming rules of
	 * Subprojects.
	 * Creates a new subproject object which is passed on the the createSubproject method of SubprojectService.
	 *
	 * Creates the subproject
	 * @param data
	 * @return "redirect:/subprojects"
	 */
	@PostMapping("/postNewSubproject")
	public String newSubproject(WebRequest data) {
		if (activeProjectID == -1)
			return "redirect:/projects";

		String subprojectName = data.getParameter("subprojectName");
		String subprojectStartDate = data.getParameter("subprojectStartDate");
		String subprojectDeadline = data.getParameter("subprojectDeadline");
		LocalDate convertedSubprojectStartDate = LocalDate.parse(subprojectStartDate);
		LocalDate convertedSubprojectDeadline = LocalDate.parse(subprojectDeadline);

		if (timeService.isSubprojectStartDateAndDeadlineBetweenProject(projectService.getProjectByProjectId(activeProjectID), convertedSubprojectStartDate, convertedSubprojectDeadline) == false
				|| timeService.isDeadlineBeforeStartDate(convertedSubprojectStartDate, convertedSubprojectDeadline) == false || subprojectName.equals(" ")) {

			return "redirect:/newSubProject";
		}

		Subproject subproject = new Subproject(subprojectName, activeProjectID, convertedSubprojectStartDate, convertedSubprojectDeadline);

		long start = System.currentTimeMillis();

		subprojectService.createSubproject(subproject);

		long end = System.currentTimeMillis();

		long total = end - start;

		System.out.println(total);

		return "redirect:/subprojects";
	}

	/**
	 * - OVO 
	 * 	PostMapping that takes in a WebRequest and Model. It recieves the specific ID for the subproject to delete
	 * 	and loads all the background attributes.
	 * @param model
	 * @param data
	 * @return
	 */
	@PostMapping("/deleteSubProjectPopUp")
	public String deleteSubProjectPopUp(Model model, WebRequest data)
	{
		if (activeProjectID == -1)
			return "redirect:/projects";

		String subprojectToDelete = data.getParameter("subprojectToDelete");
		
		model.addAttribute("subprojectToDelete", subprojectToDelete);
		model.addAttribute("activeProjectID", activeProjectID);
		model.addAttribute("project", projectService.getProjectByProjectId(activeProjectID));
		model.addAttribute("subprojectList", subprojectService.getAllAssociatedSubprojectsAndAssociatedTasks(activeProjectID));
		model.addAttribute("popup", false);
		model.addAttribute("taskPopUp", false);
		model.addAttribute("deletePopUp", true);
		model.addAttribute("stopScroll", true);

		return "subprojects";
	}

	/**
	 * - OVO
	 * Gets the redirect from project.html and then recives the activeProjectID and sets it globally
	 * in SubprojectController
	 *
	 * @param data
	 * @return
	 */
	@PostMapping("/postOpenSubproject")
	public String seeSubProject(WebRequest data)
	{
		activeProjectID = subprojectService.getParentId(Integer.valueOf(data.getParameter("subprojectId")));
		return "redirect:/subprojects";
	}

	/**
	 * - FMP
	 * Updates the status of a specific task, changing the calculation for completedTime and Workload_Per_Day
	 * Updates Workload_Per_Day based of the newly updated values
	 * @param data
	 * @return
	 */
	@PostMapping("/postChangeStatus")
	public String postChangeStatus(WebRequest data) {

		taskService.updateTaskStatus(Integer.parseInt(data.getParameter("changeStatus")));
		subprojectService.updateSubprojectCompletedTime(activeProjectID);
		projectService.updateProjectCompletedTime(activeProjectID);
		subprojectService.updateWorkloadPerDay(subprojectService.getAllAssociatedSubprojectsWithoutTasks(activeProjectID));
		projectService.updateWorkloadPerDayForSpecificProject(projectService.getProjectByProjectId(activeProjectID));

		return "redirect:/subprojects";
	}
}
