package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Models.Subproject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TimeService {

   public boolean isSubprojectStartDateAndDeadlineBetweenProject(Project project, LocalDate startdate, LocalDate deadline){

       if(startdate.isBefore(project.getStartDate()) || deadline.isAfter(project.getDeadline()))
       {

           return false;
       }

       return true;
    }

    public boolean isTaskDeadlineBetweenSubprojectStartDateAndDeadline(Subproject subproject, LocalDate deadline){

       if(deadline.isAfter(subproject.getSubprojectStartDate()) && deadline.isBefore(subproject.getSubprojectDeadline()))
        {

           return true;

        }else if(deadline.isEqual(subproject.getSubprojectDeadline()) || deadline.isEqual(subproject.getSubprojectStartDate())){
           return true;
       }

        return false;
    }

    public boolean isDeadlineBeforeStartDate(LocalDate startdate, LocalDate deadline){

       if(startdate.isBefore(deadline)){
           return true;
       }
       return false;
    }

}
