package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Services.ProjectService;
import dk.kea.taskz.Services.SubprojectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DiagramController
{
    @Autowired
    ProjectService projectService;

    @Autowired
    SubprojectService subprojectService;

    /**
     * Getmapping for Gantt chart for all projects, subprojects and tasks.
     * Calles projectservice to get a list of all projects created and then populates each projects subproject list with subprojects and
     * the corresponding tasks for each subproject.
     *
     * @param model
     * @return
     */
    @GetMapping("/gantt")
    public String gant(Model model)
    {
        List<Project> allProjects = projectService.getAllProjects();

        for(Project p : allProjects)
        {
            p.setAssociatedSubprojects(subprojectService.getAllAssociatedSubprojects(p.getProjectId()));
        }

        model.addAttribute("projects",allProjects);

        return "/gantt";
    }
}
