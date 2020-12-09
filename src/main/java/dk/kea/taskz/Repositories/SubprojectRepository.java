package dk.kea.taskz.Repositories;
import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubprojectRepository
{
	@Autowired
	ProjectRepository projectRepository;

	PreparedStatement preparedStatement = null;

	public Subproject getSubprojectStartDateAndDeadlineById(int subprojectId){
		String getSubproject = "select subprojects.Subproject_StartDate, subprojects.Subproject_Deadline from taskz.subprojects where subprojects.Subproject_ID =" + subprojectId;

		Subproject subproject = new Subproject();

		try{
			preparedStatement = ConnectionService.getConnection().prepareStatement(getSubproject);
			ResultSet rs = preparedStatement.executeQuery();

			while(rs.next()){
				subproject.setSubprojectStartDate(rs.getDate(1).toLocalDate());
				subproject.setSubprojectDeadline(rs.getDate(2).toLocalDate());
			}
		}catch (SQLException e){
			System.out.println("Error happened in SubprojectRepository at getSubprojectStartDateAndDeadlineById(): " + e);
		}

		return subproject;
	}

    public List<Subproject> getAllAssociatedSubprojects(int projectId) {

        String getAllAssociatedSubprojectsSqlStatement = "SELECT * FROM taskz.subprojects WHERE Project_ID = " + projectId ;
        List<Subproject> subprojectList = new ArrayList<>();
        try {
            preparedStatement = ConnectionService.getConnection().prepareStatement(getAllAssociatedSubprojectsSqlStatement);
            ResultSet rs = preparedStatement.executeQuery();
			updateSubprojectEstimated();
            while(rs.next()) {
				subprojectList.add(new Subproject(
						rs.getInt(1),
						rs.getString(2),
						rs.getInt(3),
						rs.getDouble(5),
						rs.getDate(6).toLocalDate(),
						rs.getDate(7).toLocalDate(),
						rs.getString(8),
						rs.getDouble(9)
				));
            }

        }
        catch(Exception e)
        {
            System.out.println("Error happened in SubprojectRepository at getAllAssociatedSubprojects(): " + e);
        }

        if(subprojectList.size() <= 0){
        	String update = "update projects set projects.Project_Estimated_Time = 0 where projects.Project_id =?";

        	try{
				preparedStatement = ConnectionService.getConnection().prepareStatement(update);
				preparedStatement.setInt(1, projectId);

				preparedStatement.executeUpdate();
			}catch (SQLException e){
				System.out.println("Error");
			}


		}
        return subprojectList;
    }


    
    public void insertSubProjectIntoDB(Subproject subproject) {
    	String insertSubProject = "INSERT INTO taskz.subprojects (Subproject_Name, Project_ID, Subproject_StartDate, Subproject_Deadline) VALUES (?, ?, ?, ?)";
    	
    	try {
    		preparedStatement = ConnectionService.getConnection().prepareStatement(insertSubProject);
    		preparedStatement.setString(1, subproject.getSubprojectName());
    		preparedStatement.setInt(2, subproject.getParentProjectId());
    		preparedStatement.setDate(3, java.sql.Date.valueOf(subproject.getSubprojectStartDate()));
    		preparedStatement.setDate(4, java.sql.Date.valueOf(subproject.getSubprojectDeadline()));

    		preparedStatement.execute();
		} catch (SQLException e) {
			System.out.println("Error in SubProjectRepository. Method: createSubProject: " + e.getMessage());
		}

	}


	public void deleteSubProjectFromDB(int Subproject_ID) {
    	String deleteSubProjectFromDB = "DELETE FROM subprojects WHERE Subproject_ID = ?";

    	try {
			preparedStatement = ConnectionService.getConnection().prepareStatement(deleteSubProjectFromDB);
			preparedStatement.setInt(1, Subproject_ID);

			preparedStatement.execute();
			projectRepository.updateProjectEstimatedTime();

		} catch (SQLException e) {
			System.out.println("Klasse: SubprojectRepository\nMethode: deleteSubProject()\nError: " + e.getMessage());
		}

	}

	public String getParentProjectNameFromDB(int projectId) {
		String selectsProject = "SELECT Project_Name FROM taskz.projects WHERE Project_ID = " + projectId;
		String projectNameToReturn = "";

		try {
			preparedStatement = ConnectionService.getConnection().prepareStatement(selectsProject);
			ResultSet resultSet =  preparedStatement.executeQuery();

			while (resultSet.next()) {
				projectNameToReturn = resultSet.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("Error happened in SubprojectRepository at getProjectName(): " + e.getMessage());
		}

		return projectNameToReturn;
	}
	
	public int getParentProjectIdFromDB(int subproject_ID) {

    	String selectsProject = "SELECT Project_ID FROM taskz.subprojects WHERE Subproject_ID = " + subproject_ID;
    	int idToReturn = 0;
    	
    	try {
    		preparedStatement = ConnectionService.getConnection().prepareStatement(selectsProject);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		
    		while (resultSet.next()) {
    			idToReturn = resultSet.getInt(1);
			}
		}catch (SQLException e) {
			System.out.println("Error happened in SubprojectRepository at getParentProjectIdFromDB(): " + e.getMessage());
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
    		preparedStatement = ConnectionService.getConnection().prepareStatement(updateTotalEstimatedTime);
			preparedStatement.executeUpdate();

		}catch (SQLException e){

			System.out.println("Error happened in ProjectRepository updateProjectEstimatedTime: " + e.getMessage());

		}
	}

	public void updateWorkloadPerDay(String workloadPerDay, int subprojectID) {
		String updateWorkloadPerDay = "UPDATE subprojects SET Subproject_Workload_Per_Day = ? WHERE Subproject_ID = ?";

		try {
			PreparedStatement preparedStatement = ConnectionService.getConnection().prepareStatement(updateWorkloadPerDay);
			preparedStatement.setString(1, workloadPerDay);
			preparedStatement.setDouble(2, subprojectID);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error happened in ProjectRepository updateWorkLoadPerDay: " + e.getMessage());
		}
	}

	public List<Subproject> selectAllSubprojects() {
    	String selectAllSubprojects = "SELECT Subproject_ID, Subproject_Name, Project_ID, Subproject_Estimated_Time, Subproject_StartDate, Subproject_Deadline, Subproject_Workload_Per_Day, Subproject_Completed_Time FROM subprojects";

    	List<Subproject> allSubprojects = new ArrayList<>();

    	try {
    		preparedStatement = ConnectionService.getConnection().prepareStatement(selectAllSubprojects);
    		ResultSet rs = preparedStatement.executeQuery();

    		while (rs.next()) {
    			Subproject subproject = new Subproject(
						rs.getInt(1),
						rs.getString(2),
						rs.getInt(3),
						rs.getDouble(4),
						rs.getDate(5).toLocalDate(),
						rs.getDate(6).toLocalDate(),
						rs.getString(7),
						rs.getDouble(8)
				);
    			allSubprojects.add(subproject);
			}
		} catch (SQLException e) {
			System.out.println("Error happened in subprojectRepository selectAllSubprojects: " + e.getMessage());
		}
    	return allSubprojects;
	}

	public void updateSubprojectCompletedTime(double preliminaryCompletedTime, int subprojectID) {
		String updateSubprojectCompletedTime = "UPDATE subprojects SET Subproject_Completed_Time = ? WHERE Subproject_ID = ?";

		try {
			preparedStatement = ConnectionService.getConnection().prepareStatement(updateSubprojectCompletedTime);
			preparedStatement.setDouble(1, preliminaryCompletedTime);
			preparedStatement.setInt(2, subprojectID);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error happened in subprojectRepository updateSubprojectCompletedTime: " + e.getMessage());
		}

	}
}
