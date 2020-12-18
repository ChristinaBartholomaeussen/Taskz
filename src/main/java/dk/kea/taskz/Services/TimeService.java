package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Models.Subproject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TimeService {

    /**(CMB)
     * Method to check wheter the startDato and deadline for a new subproject
     * is between or equal to the chosen projects startDate and deadline.
     * It takes the a project as parameters and two LocalDates startdate and deadline.
     * If the startdate for subproject and dedline is not between it will return false.
     *
     * @param project
     * @param startdate
     * @param deadline
     * @return
     */

   public boolean isSubprojectStartDateAndDeadlineBetweenProject(Project project, LocalDate startdate, LocalDate deadline){

       if(startdate.isBefore(project.getStartDate()) && deadline.isAfter(project.getDeadline()))
       {
           return false;
       }

       return true;
    }


    /**(CMB)
     * Method to check if the deadline for a task is between the
     * chosen subproject. It checks if the deadline is equal to
     * deadline and startdate for the subproject too, to make
     * sure that the tasks is created correct
     *
     * @param subproject
     * @param deadline
     * @return
     */
    public boolean isTaskDeadlineBetweenSubprojectStartDateAndDeadline(Subproject subproject, LocalDate deadline){

       if(deadline.isAfter(subproject.getSubprojectStartDate()) && deadline.isBefore(subproject.getSubprojectDeadline()))
        {
           return true;

        }else if(deadline.isEqual(subproject.getSubprojectDeadline()) || deadline.isEqual(subproject.getSubprojectStartDate())){
           return true;
       }

        return false;
    }

    /**(CMB)
     * Method to check is the deadline is before startdate.
     * Can be used for both projects and subproject, that's
     * why it takes two Localdates and not a Project and subproject
     * as parameters.
     *
     * @param startdate
     * @param deadline
     * @return
     */
    public boolean isDeadlineBeforeStartDate(LocalDate startdate, LocalDate deadline){

       if(startdate.isBefore(deadline)){
           return true;
       }
       return false;
    }

}
