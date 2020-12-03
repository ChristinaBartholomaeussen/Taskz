package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    ProjectRepository projectRepository = new ProjectRepository();

    List<Project> projectList = new ArrayList<>();

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

    public void updateWorkloadPerDay(List<Project> projectList) {

        double convertedDaysBetween = 0;

        DecimalFormat df = new DecimalFormat("0.00");

        for (Project project : projectList) {

            long daysBetween = ChronoUnit.DAYS.between(project.getStartDate(), project.getDeadline());
            long numberOfWeeks = daysBetween / 7;

            daysBetween = daysBetween - (2 * numberOfWeeks);
            convertedDaysBetween = (double)daysBetween;
            double workloadPerDay =  project.getTotalEstimatedTime() / convertedDaysBetween ;

            projectRepository.updateWorkloadPerDay(df.format(workloadPerDay), project.getProjectId());
        }
    }

    public Project getProjectByProjectId(int activeProjectID)
    {
        return projectRepository.getProjectByProjectId(activeProjectID);
    }
}


