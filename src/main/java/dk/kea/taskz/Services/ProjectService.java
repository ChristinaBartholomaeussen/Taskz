package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Repositories.ProjectRepository;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    ProjectRepository projectRepository = new ProjectRepository();
    Project project = new Project();

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



    public void calculateTotalEstimatedTimeForProject(int projectId)
    {
        double sum  = 0;

        List<Double> total = projectRepository.calculateEstimatedTimeForProject(projectId);

        for(Double d : total){
            sum += d;
        }

        projectRepository.insertTotalEstimatedTime(sum, projectId);

    }







}


