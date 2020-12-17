package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Repositories.ProjectRepository;
import dk.kea.taskz.Repositories.SubprojectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ProjectService
{
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    SubprojectRepository subprojectRepository;

    @Autowired
    SubprojectService subprojectService;

    List<Project> projectList;

    /** Christina
     * Henter listen fra Repository, som henter det specfikke ID, navn, deadline og estimeret time
     * @return
     */
    public List<Project> getAllProjects() {


        projectList = projectRepository.getAllProjectsFromDatabase();
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

    /**
     * - FMP
     * Updates Workload_Per_Day in the database, based on the arrayList we get from getAllProjects()
     * Collects Total_Estimated_Time from the project and calculates Work_Hours_Per_Day
     * The calculation accounts for a business week eg. 5 days
     */

    public void updateAllProjectsWorkloadPerDay()
    {
        List<Project> projectList = getAllProjects();

        double convertedDaysBetween;

        DecimalFormat df = new DecimalFormat("0.00");

        for (Project project : projectList) {

            long daysBetween = ChronoUnit.DAYS.between(project.getStartDate(), project.getDeadline());
            long numberOfWeeks = daysBetween / 7;

            daysBetween = daysBetween - (2 * numberOfWeeks);
            convertedDaysBetween = (double)daysBetween;
            double workloadPerDay =  (project.getTotalEstimatedTime() - project.getCompletedTime()) / convertedDaysBetween;

            projectRepository.updateWorkloadPerDay(df.format(workloadPerDay), project.getProjectId());
        }
    }

    /**
     * - FMP
     * Same as the method above, just getting a single object from an activeProjectID instead
     * Used where possible to minimize load times
     * @param project
     */

    public void updateWorkloadPerDayForSpecificProject(Project project)
    {
		double convertedDaysBetween;

		DecimalFormat df = new DecimalFormat("0.00");

		long daysBetween = ChronoUnit.DAYS.between(project.getStartDate(), project.getDeadline());
		long numberOfWeeks = daysBetween / 7;

		daysBetween = daysBetween - (2 * numberOfWeeks);
		convertedDaysBetween = (double) daysBetween;
		double workloadPerDay = (project.getTotalEstimatedTime() - project.getCompletedTime()) / convertedDaysBetween;

		projectRepository.updateWorkloadPerDay(df.format(workloadPerDay), project.getProjectId());
	}

	/**
	 * - OVO gets active project ID
	 *
	 * @param activeProjectID
	 * @return
	 */
	public Project getProjectByProjectId(int activeProjectID) {
		return projectRepository.getProjectByProjectId(activeProjectID);
	}

	public void updateProjectEstimatedTime(int projectId) {
		projectRepository.updateProjectEstimatedTime(projectId);
	}

	/**
	 * - FMP
	 * Calculates the completed hours of a project as a total of all the subprojects completed time
	 * Updates Workload_Per_Day in the database for the active project
	 *
	 * @param projectID
	 */

	public void updateProjectCompletedTime(int projectID) {
	    projectRepository.updateProjectCompletedTime(projectID);
    }

    public List<Project> getAllProjectsIncludingAssociatedSubprojectsAndTasks()
    {
        List<Project> allProjects = getAllProjects();

        for (Project p : allProjects) {
            p.setAssociatedSubprojects(subprojectService.getAllAssociatedSubprojects(p.getProjectId()));
        }

        return allProjects;
    }
}


