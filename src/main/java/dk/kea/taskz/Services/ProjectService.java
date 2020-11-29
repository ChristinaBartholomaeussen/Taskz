package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    List<Project> projectList = new ArrayList<>();

    public ProjectService() {
        projectList.add(new Project("Host Migration", LocalDate.now(), LocalDate.now().plusDays(7)));
        projectList.add(new Project("Frontend Development", LocalDate.now(), LocalDate.now().plusDays(14)));
    }

    public List<Project> getProjectList()
    {
        return  projectList;
    }

}
