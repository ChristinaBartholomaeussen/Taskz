package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Repositories.ProjectRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    ProjectRepository projectRepository = new ProjectRepository();

    List<Project> projectList = new ArrayList<>();

    public List<Project> getProjectList() {

        return  projectList;
    }


    /** Christina
     * Henter listen fra Repository, som henter det specfikke ID, navn, deadline og estimeret time
     * @return
     */
    public List<Project> getProjectByIdNameDeadlineEstimatedTime() {
        projectList = projectRepository.selectProjectFromDatabaseByIdNameDeadlineEstimatedTime();

        return projectList;
    }

    /** Christina
     * Indsaetter nyt object af Project i databasen ved hjaelp af metode i repository
     * @param project
     */
    public void addProjectToDatabase(Project project) {

        projectRepository.insertProjectIntoDatabase(project);

    }

    public void deleteProject(int projectId){

        projectRepository.deleteWholeProject(projectId);
    }
}
