package dk.kea.taskz.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class PopUpController {

    @GetMapping("/projectPopUp")
    public String getProjectPopUp(Model model) {
        LocalDate date = LocalDate.now();

        model.addAttribute("date", date);

        return "taskPopUp";
    }
}
