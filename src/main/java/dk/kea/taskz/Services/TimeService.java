package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Models.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TimeService {

   public boolean isSubprojectStartDateAndDeadlingBetweenProject(Project project, Subproject subproject){

       if(subproject.getSubprojectStartDate().isBefore(project.getStartDate()) || subproject.getSubprojectDeadline().isAfter(project.getDeadline()))
       {
           System.out.println("fejl");
           return false;
       }
       System.out.println("OK");
       return true;
    }

    public boolean isTaskDeadlingBetweenSubprojectStartDateAndDeadline(Subproject subproject, Task task){

       if(task.getDeadline().isAfter(subproject.getSubprojectStartDate()) && task.getDeadline().isBefore(subproject.getSubprojectDeadline())){
           System.out.println("task OK");
           return true;

        }
        System.out.println("fejl i task");
        return false;
    }

}
