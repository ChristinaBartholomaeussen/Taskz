package dk.kea.taskz.Controllers;

import dk.kea.taskz.Services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginController
{

    @Autowired
    private MemberService memberService;

    /**
     * The "root"-mapping which points to the login screen and shows login.html
     * /RBP
     * @return
     */
    @GetMapping({"/","/login"})
    public String login()
    {
        return "login";
    }

	/**
     * The postmapping that will be used after we press the Login-button from login.html.
     * Declares two variables, username and password which are sent to the service, which gets a list from the
     * database of all the users.
     * If the password and username do not match the information on the list, the user will be redirected back to the
     * login page. If they match, the project-overview screen will be shown.
     * @param data
     * @return
     */
    @PostMapping("/postLogin")
	public String postLogin(WebRequest data, HttpServletResponse response)
    {
        String username = data.getParameter("username");
        String password = data.getParameter("password");

        
		
        
        if(memberService.verifyLogin(username,password)) {
			Cookie ck = new Cookie("id", Integer.toString(memberService.getId(username, password)));
			response.addCookie(ck);
            return "redirect:/projects";
		}

        return "redirect:/login";
    }



}
