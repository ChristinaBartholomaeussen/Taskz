package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Enums.Status;
import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Repositories.SubprojectRepository;
import dk.kea.taskz.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubprojectService
{
    @Autowired
    SubprojectRepository subprojectRepository;

    @Autowired
    TaskRepository taskRepository;

    List<Subproject> subprojectList = new ArrayList<>();

    /**
     * Makes a call to the subProjectRepository with the projectId parameter, to return a list with subprojects associated to the
     * corresponding project
     * /RBP
      * @param projectId
     * @return
     */
    public List<Subproject> getAllAssociatedSubprojects(int projectId) {
        List<Subproject> allAssociatedSubprojects = subprojectRepository.getAllAssociatedSubprojects(projectId);

        for(Subproject subproject : allAssociatedSubprojects)
            subproject.setTaskList(taskRepository.getAllAssociatedTasksToSubproject(subproject.getSubprojectId()));

        return allAssociatedSubprojects;
    }

    public List<Subproject> getAllAssociatedSubprojectsWithoutTasks(int projectID) {

        List<Subproject> allAssociatedSubprojects = subprojectRepository.getAllAssociatedSubprojects(projectID);

        return allAssociatedSubprojects;
    }
    
    public void createSubproject(Subproject subproject){
    	subprojectRepository.insertSubProjectIntoDB(subproject);
	}
	
	public void deleteSubProject(int id) {subprojectRepository.deleteSubProjectFromDB(id);}
	
	public String getParentProjectName(int id) {return subprojectRepository.getParentProjectNameFromDB(id);}
	
	public int getParentId(int id) {
    	return subprojectRepository.getParentProjectIdFromDB(id);
    }

    /**
     * - FMP
     * Gets an arrayList of all subprojects in the database
     * @return
     */

    public List<Subproject> getAllSubprojects() {
        subprojectList = subprojectRepository.selectAllSubprojects();

        return subprojectList;
    }
    

    /**
     * - FMP
     * Updates Workload_Per_Day in the database, based of an arrayList we get from getAllSubprojects()
     * Collects Total_Estimated_Time from the subproject and calculates Work_Hours_Per_Day
     * The calculation accounts for a business week eg. 5 days
     * @param subprojectList
     */

    public void updateWorkloadPerDay(List<Subproject> subprojectList) {

        double convertedDaysBetween = 0;

        DecimalFormat df = new DecimalFormat("0.00");

        for (Subproject subproject : subprojectList) {

            long daysBetween = ChronoUnit.DAYS.between(subproject.getSubprojectStartDate(), subproject.getSubprojectDeadline());
            long numberOfWeeks = daysBetween / 7;

            daysBetween = daysBetween - (2 * numberOfWeeks);
            convertedDaysBetween = (double)daysBetween;
            double workloadPerDay =  (subproject.getSubprojectTotalEstimatedTime() - subproject.getSubprojectCompletedTime()) / convertedDaysBetween;

            subprojectRepository.updateWorkloadPerDay(df.format(workloadPerDay), subproject.getSubprojectId());
        }
    }

    /**
     * - FMP
     * Calculates subprojects total completed time based of individual tasks completed time
     * Updates Workload_Per_Day in the database per subproject
     * @param projectId
     */

    public void updateSubprojectCompletedTime(int projectId) {
        List<Subproject> allAssociatedSubprojects = subprojectRepository.getAllAssociatedSubprojects(projectId);

        double preliminaryCompletedTime = 0;

        for (Subproject subproject : allAssociatedSubprojects) {
            List<Task> associatedTasks = new ArrayList<>();

            associatedTasks = taskRepository.getAllAssociatedTasksToSubproject(subproject.getSubprojectId());

            for(Task task : associatedTasks) {
                if (task.getStatus() == Status.COMPLETED) {
                    preliminaryCompletedTime = preliminaryCompletedTime + task.getEstimatedTime();
                }
            }
            subprojectRepository.updateSubprojectCompletedTime(preliminaryCompletedTime, subproject.getSubprojectId());

            preliminaryCompletedTime = 0;
        }
    }

    public void updateSubprojectTotalEstimatedTime() {
        subprojectRepository.updateSubprojectEstimated();
    }

    public Subproject getSubprojectStartDateDeadline(int subprojectId){
        return subprojectRepository.getSubprojectStartDateAndDeadlineById(subprojectId);
    }
}
