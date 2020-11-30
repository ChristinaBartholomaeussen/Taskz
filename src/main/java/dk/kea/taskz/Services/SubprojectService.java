package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Repositories.SubprojectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubprojectService
{
    @Autowired
    SubprojectRepository subprojectRepository;

    public List<Subproject> getAllAssociatedSubprojects(int projectId)
    {
        return subprojectRepository.getAllAssociatedSubprojects(projectId);

    }
    
    public void createSubproject(Subproject subproject){
    	subprojectRepository.insertSubProjectIntoDB(subproject);
	}
	
	public void deleteSubProject(int id) {subprojectRepository.deleteSubProjectFromDB(id);}
	

}
