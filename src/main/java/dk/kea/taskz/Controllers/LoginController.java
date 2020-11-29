package dk.kea.taskz.Controllers;

import dk.kea.taskz.Services.MemberService;
import dk.kea.taskz.Services.SubprojectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class LoginController
{
    @Autowired
    private MemberService memberService;

    @Autowired
    private SubprojectService subprojectService;

    @GetMapping({"/","/login"})
    public String login()
    {
        return "login";
    }

    @PostMapping("/postLogin")
	public String postLogin(WebRequest data)
    {
        String username = data.getParameter("username");
        String password = data.getParameter("password");
        
        if(memberService.verifyLogin(username,password)) {
            return "redirect:/projects";
		}

        return "redirect:/login";
    }

}
