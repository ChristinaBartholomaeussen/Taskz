package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@Controller
public class PopUpController {

    @GetMapping("/projectPopUp")
    public String projectPopUp(Model model) {
        LocalDate date = LocalDate.now();

        model.addAttribute("date", date);

        return "projectPopUp";
    }

    @PostMapping("/postProjectPopUp")
    public String postProjectPopUp(WebRequest taskData) {

        String projectName = taskData.getParameter("projectName");

        Project project = new Project(projectName);

        System.out.println(project.toString());

        return "redirect:/projectPopUp";
    }
}
