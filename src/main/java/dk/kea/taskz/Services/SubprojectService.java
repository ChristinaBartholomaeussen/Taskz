package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Repositories.SubprojectRepository;
import dk.kea.taskz.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubprojectService
{
    @Autowired
    SubprojectRepository subprojectRepository;

    @Autowired
    TaskRepository taskRepository;

    /**
     * Makes a call to the subProjectRepository with the projectId parameter, to return a list with subprojects associated to the
     * corresponding project
     * /RBP
      * @param projectId
     * @return
     */
    public List<Subproject> getAllAssociatedSubprojects(int projectId)
    {
        List<Subproject> allAssociatedSubprojects = subprojectRepository.getAllAssociatedSubprojects(projectId);

        for(Subproject subproject : allAssociatedSubprojects)
            subproject.setTaskList(taskRepository.getAllAssociatedTasksToSubproject(subproject.getSubprojectId()));

        return allAssociatedSubprojects;
    }
    
    public void createSubproject(Subproject subproject){
    	subprojectRepository.insertSubProjectIntoDB(subproject);
	}
	
	public void deleteSubProject(int id) {subprojectRepository.deleteSubProjectFromDB(id);}
	

}
