package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Subproject;
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

    @Autowired
    TaskService taskService;

    private List<Subproject> subprojectList;

    /**
	 * - RBP
	 * Makes a call to the subProjectRepository with the projectId parameter, to return a list with subprojects associated to the
	 * corresponding project
	 *
	 * @param projectId
	 * @return
	 */
    public List<Subproject> getAllAssociatedSubprojects(int projectId) {
        List<Subproject> allAssociatedSubprojects = subprojectRepository.getAllAssociatedSubprojects(projectId);

        for(Subproject subproject : subprojectRepository.getAllAssociatedSubprojects(projectId))
            subproject.setTaskList(taskRepository.getAllAssociatedTasksToSubproject(subproject.getSubprojectId()));

        return allAssociatedSubprojects;
    }

    /**
     * -FMP
     * Returns a list of all subprojects to the corresponding Project based on the projectId recieved from the method parameter,
     * by using the SubprojectRepository method getAllAssociatedSubprojects()
     *
     * @param projectID
     * @return
     */
    public List<Subproject> getAllAssociatedSubprojectsWithoutTasks(int projectID) {

        return subprojectRepository.getAllAssociatedSubprojects(projectID);
    }

    /**
     * - RBP
     * Passes a Subproject object recieved from the method parameter to the SubprojectRepository method,
     * insertSubProjectIntoDB()
     * @param subproject
     */
    public void createSubproject(Subproject subproject){
    	subprojectRepository.insertSubProjectIntoDB(subproject);
	}

    /**
     * - CMB
     * Recieves a integer id from the method parameter, which is used by the SubprojectRepository method,
     * deleteSubprojectFromDB(), to delete a Subproject from the Database
     * @param id
     */
	public void deleteSubProject(int subprojectId)
    {
        subprojectRepository.deleteSubProjectFromDB(subprojectId);
    }

    /**
     * - RBP
     * Returns a parent project id based on the subproject id recieved in the method parameter, which is used
     * by the SubprojectRepository method getParentProjectIdFromDB().
     * @param id
     * @return
     */
	public int getParentId(int subprojectId) {
    	return subprojectRepository.getParentProjectIdFromDB(subprojectId);
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
    public void updateSubprojectCompletedTime(int projectId){

        for(Subproject s : getAllSubprojects()){
            if(s.getParentProjectId() == projectId){
                subprojectRepository.updateSubprojectCompletedTime(s.getSubprojectId());
            }
        }
    }


    /**
     * - OVO
     * Recieves a subprojectId from the method parameter, which is passed on to the Subproject Repository
     * method updateSubprojectEstimated(), which is used to update the corresponding Subproject total estimated time
     * @param subprojectId
     */
    public void updateSubprojectTotalEstimatedTime(int subprojectId) {
        subprojectRepository.updateSubprojectEstimated(subprojectId);
    }

    /**
     * Returns a Subproject object based on the subprojectId recieved from the method parameter, which is used by the SubprojectRepository
     * method getSubprojectStartDateAndDeadlineById()
     * @param subprojectId
     * @return
     */
    public Subproject getSubprojectStartDateDeadline(int subprojectId){
        return subprojectRepository.getSubprojectStartDateAndDeadlineById(subprojectId);
    }
}
