package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Repositories.ProjectRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    ProjectRepository projectRepository = new ProjectRepository();

    List<Project> projectList = new ArrayList<>();

    public ProjectService() {
        projectList.add(new Project("Host Migration", LocalDate.now(), LocalDate.now().plusDays(7)));
        projectList.add(new Project("Frontend Development", LocalDate.now(), LocalDate.now().plusDays(14)));
    }

    public List<Project> getProjectList()
    {
        return  projectList;
    }


    /** Christina
     * Henter listen fra Repository, som henter det specfikke ID, navn, deadline og estimeret time
     * @return
     */
    public List<Project> getProjectByIdNameDeadlineEstimatedTime(){
        projectList = projectRepository.selectProjectFromDatabaseByIdNameDeadlineEstimatedTime();

        return projectList;
    }

    /** Christina
     * Indsaetter nyt object af Project i databasen ved hjaelp af metode i repository
     * @param project
     */
    public void addProjectToDatabase(Project project){

        projectRepository.insertProjectIntoDatabase(project);

    }
}
