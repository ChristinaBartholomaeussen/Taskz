package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Repositories.ProjectRepository;
import dk.kea.taskz.Services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
public class LoginController
{

    ProjectRepository projectRepository = new ProjectRepository();
    @Autowired
    private MemberService memberService;

    
    @GetMapping({"/","/login"})
    public String login()
    {

        return "login";
    }

    @PostMapping("/postLogin")
	public String postLogin(WebRequest data)
    {

        List<Project> projectList = projectRepository.selectAllProjectsFromDatabase();
        System.out.println(projectList);

	
        String username = data.getParameter("username");
        String password = data.getParameter("password");
        
        if(memberService.verifyLogin(username,password)) {
            return "redirect:/projects";
		}

        
        return "redirect:/login";
    }

}
