package dk.kea.taskz.Repositories;

import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Services.ConnectionService;
import dk.kea.taskz.Services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.security.auth.Subject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Component
public class SubprojectRepository
{
    @Autowired
    ConnectionService connectionService;
    
    ConnectionService conServ = new ConnectionService();

    public List<Subproject> getAllAssociatedSubprojects(int projectId)
    {
		updateSubprojectEstimated();
        String getAllAssociatedSubprojectsSqlStatement = "SELECT * FROM taskz.subprojects WHERE Project_ID = " + projectId ;
        List<Subproject> subprojectList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();

        try
        {
            PreparedStatement ps = connectionService.establishConnection().prepareStatement(getAllAssociatedSubprojectsSqlStatement);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                subprojectList.add(new Subproject(rs.getInt(1),rs.getInt(3),rs.getString(2)));
            }

        }
        catch(Exception e)
        {
            System.out.println("Error happened in SubprojectRepository at getAllAssociatedSubprojects(): " + e);
        }

        return subprojectList;
    }
    
    public void insertSubProjectIntoDB(Subproject subproject) {
    	String insertSubProject = "INSERT INTO taskz.subprojects (Subproject_Name, Project_ID, Time_Spent, Subproject_Estimated_Time) VALUES (?, ?, ?, ?)";
    	
    	try {
    		PreparedStatement preparedStatement = connectionService.establishConnection().prepareStatement(insertSubProject);
    		
    		preparedStatement.setString(1, subproject.getSubprojectName());
    		preparedStatement.setInt(2, subproject.getParentProjectId());
    		preparedStatement.setInt(3, 0);
    		preparedStatement.setDouble(4, 0.0);
    		
    		preparedStatement.execute();
		} catch (SQLException e) {
			System.out.println("Error in SubProjectRepository. Method: createSubProject: " + e.getMessage());
		}
	}
	
	public void deleteSubProjectFromDB(int Subproject_ID) {
    	String deleteSubProjectFromDB = "DELETE FROM subprojects WHERE Subproject_ID = ?";
    	
    	try {
			PreparedStatement preparedStatement = connectionService.establishConnection().prepareStatement(deleteSubProjectFromDB);
			preparedStatement.setInt(1, Subproject_ID);
			
			preparedStatement.execute();
		} catch (SQLException e) {
			System.out.println("Klasse: SubprojectRepository\nMethode: deleteSubProject()\nError: " + e.getMessage());
		}
	}

	public String getParentProjectNameFromDB(int projectId) {
		String selectsProject = "SELECT Project_Name FROM taskz.projects WHERE Project_ID = " + projectId;
		String projectNameToReturn = "";

		try {
			PreparedStatement preparedStatement = connectionService.establishConnection().prepareStatement(selectsProject);
			ResultSet resultSet =  preparedStatement.executeQuery();

			while (resultSet.next()) {
				projectNameToReturn = resultSet.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("Klasse: ProjectRepository\nMethode: getProjectName\nError: " + e.getMessage());
		}

		return projectNameToReturn;
	}
	
	public int getParentProjectIdFromDB(int subproject_ID) {

    	String selectsProject = "SELECT Project_ID FROM taskz.subprojects WHERE Subproject_ID = " + subproject_ID;
    	int idToReturn = 0;
    	
    	try {
    		PreparedStatement preparedStatement = conServ.establishConnection().prepareStatement(selectsProject);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		
    		while (resultSet.next()) {
    			idToReturn = resultSet.getInt(1);
			}
		}catch (SQLException e) {
			System.out.println("Klasse: ServiceRepository\nMethode: getParentProjectIdFromDB\n" + e.getMessage());
		}

    	return idToReturn;
	}


	public void updateSubprojectEstimated(){

    	String updateTotalEstimatedTime = "update subprojects s\n" +
				"right outer join (select tasks.SubProject_ID, sum(tasks.Task_Estimated_Time) as mysum\n" +
				"\t\tfrom tasks group by tasks.SubProject_ID) as t\n" +
				"        on s.Subproject_ID = t.SubProject_ID\n" +
				"\t\tset s.Subproject_Estimated_Time = t.mysum\n" +
				"        where s.Subproject_ID = t.SubProject_ID";

    	try{
    		PreparedStatement preparedStatement = connectionService.establishConnection().prepareStatement(updateTotalEstimatedTime);
			preparedStatement.executeUpdate();
		}catch (SQLException e){

			System.out.println("Happened in ProjectRepository updateProjectEstimatedTime: " + e.getMessage());

		}

	}
}
