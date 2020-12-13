package dk.kea.taskz.Controllers;

import dk.kea.taskz.Services.CookieService;
import dk.kea.taskz.Services.MemberService;
import dk.kea.taskz.Services.SubprojectService;
import dk.kea.taskz.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
	
	@Autowired
	MemberService memberService;

	@Autowired
	TaskService taskService;

	@Autowired
	SubprojectService subprojectService;
	
	@Autowired
	CookieService cookieService;

	int activeUserId = -1;

	/**
	 * - OVO
	 * A GetMapping for Profile. It checks whether a cookie matches a member id, and then loads the right data.
	 *
	 * @param model
	 * @param request
	 * @return "userProfile"
	 */
	@GetMapping("/profile")
	public String yourProfile(Model model, HttpServletRequest request) {

		int activeUserId = cookieService.getActiveUserId(request);
		
		if (activeUserId == -1) {
			return "redirect:/login";
		}

		model.addAttribute("member", memberService.getSingleMember(activeUserId));
		model.addAttribute("tasks", taskService.getAllTasks(activeUserId));
		model.addAttribute("earliestDeadLine", taskService.getEarlistDeadline(activeUserId));

		return "userProfile";
	}
}
