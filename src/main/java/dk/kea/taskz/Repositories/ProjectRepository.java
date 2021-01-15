package dk.kea.taskz.Repositories;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Services.ConnectionService;
import dk.kea.taskz.Services.ProjectService;
import dk.kea.taskz.Services.SubprojectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {

    @Autowired
    SubprojectRepository subprojectRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    SubprojectService subprojectService;

    private PreparedStatement preparedStatement = null;

    /**
	 * - OVO
	 * Gets all the projects from the database
	 * @return liste af alle projekter
	 */
    public List<Project> getAllProjectsFromDatabase()
    {
        String selectAllProjects = "SELECT Project_Id, Project_Name, Project_StartDate, Deadline, Workload_Per_Day, Project_Estimated_Time, Project_Completed_Time FROM projects";
        List<Project> allProjects = new ArrayList<>();

        try {
            preparedStatement = ConnectionService.getConnection().prepareStatement(selectAllProjects);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                Project project = new Project(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDate(3).toLocalDate(),
                        rs.getDate(4).toLocalDate(),
                        rs.getString(5),
                        rs.getDouble(6),
                        rs.getDouble(7)
                );

                allProjects.add(project);
			}

		} catch (SQLException e) {
			System.out.println("Error happened in ProjectRepository at getAllProjectsFromDatabase(): " + e.getMessage());
		}
		return allProjects;
	}

	/**
	 * - OVO
	 * Inserts a project into the database
	 * @param project
	 */
	public void insertProjectIntoDatabase(Project project) {
		String insertProjectIntoDatabase =
				"INSERT INTO projects(Project_Id, Project_Name, Project_StartDate, Deadline) " +
						"VALUES (?, ?, ?, ?)";
		try {
			preparedStatement = ConnectionService.getConnection().prepareStatement(insertProjectIntoDatabase);
			preparedStatement.setInt(1, project.getProjectId());
			preparedStatement.setString(2, project.getName());
			preparedStatement.setDate(3, java.sql.Date.valueOf(project.getStartDate()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(project.getDeadline()));
            preparedStatement.execute();
        }
        catch (SQLException e)
        {
            System.out.println("Error happened in ProjectRepository at insertProjectIntoDatabase(): " + e.getMessage());
        }
    }

    /**
     * - CMB
     * Method with JDBC query which deletes the chosen project with the specific projectId.
     * PrepareStatement recieves the string, deleteQuery, which sets the parameterIndex 1 to be our
     * projectId and then executes it.
     * When the project is deleted, the subprojects and tasks which are connected through
     * foreign keys, will be deleted as well, because of cascade in our database.
     * @param projectId
     */
    public void deleteWholeProject(int projectId)
    {
        String deleteQuery = "delete from projects where Project_ID = ?";

        try
        {
            preparedStatement = ConnectionService.getConnection().prepareStatement(deleteQuery);
            preparedStatement.setInt(1, projectId);
            preparedStatement.execute();
        }
        catch (SQLException e)
        {
            System.out.println("Error happened in ProjectRepository at deleteWholeProject(): " + e.getMessage());
        }
    }

    /**
     * - CMB
     * Method with a string, with our sql query.
     * The query updates our projects table, and joins the subprojects on the primary key from projects
     * and the foreign key from subprojects.
     * Then selects the total sum for estimated time in subprojects, groups them by project_id because of the foreign keys and
     * then sets the value of estimated time for each project to be the sum of all subprojects, where
     * the primary key in projects is equal to the foreign key in subprojects.
     * PrepareStatement receives the sql-query string and then uses the executeUpdate extension method.
     * @param projectId
     */
    public void updateProjectEstimatedTime(int projectId){

        String updateTotalTime = "update projects p " +
                "inner join (select subprojects.Project_ID, " +
                "sum(subprojects.Subproject_Estimated_time) as mysum " +
                "from subprojects group by " +
                "subprojects.Project_ID) as s on p.Project_ID = s.Project_ID set p.Project_Estimated_Time" +
                " = s.mysum where p.Project_ID = ?";

        try{
            preparedStatement = ConnectionService.getConnection().prepareStatement(updateTotalTime);
            preparedStatement.setInt(1, projectId);
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            System.out.println("Error happened in ProjectRepository at updateProjectEstimatedTime(): " + e.getMessage());
        }
    }

    /**
     * - RBP
     * This method retrieves the Project object from the database.
     * A sqlQuery string is created with the Project_ID and selects all columns from the projects table, where the Project_ID is equal
     * to the Project_ID recieved from the method parameter.
     *
     * The Project object is populated by the information recieved from the ResultSet and returned to the caller.
     * @param projectId
     * @return
     */
    public Project getProjectByProjectId(int projectId)
    {
        String sqlQuery = "SELECT * FROM taskz.projects WHERE Project_ID = " + projectId;
        Project project = new Project();

        try
        {
            preparedStatement = ConnectionService.getConnection().prepareStatement(sqlQuery);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                project.setProjectId(rs.getInt(1));
                project.setName(rs.getString(2));
                project.setStartDate(rs.getDate(3).toLocalDate());
                project.setDeadline(rs.getDate(4).toLocalDate());
                project.setTotalWorkHoursPerDay(rs.getString(5));
                project.setTotalEstimatedTime(rs.getDouble(6));
                project.setCompletedTime(rs.getDouble(7));
            }
        }
        catch(Exception e)
        {
            System.out.println("Error happened in ProjectRepository at getProjectByProjectId()" + e);
        }

        return project;
    }

    /** (FMP, CMB)
     * Updates column Project_Completed_Time in the database based of a projectID
     * To do that we get the total sum of completed time for all the subprojects
     * to the specific project as mysum. It joins on the primary key from the projects table
     * and the foreign key in the subprojects table.
     * Then the project completed time is set to mysum where the primary key in projects is equal to
     * the parameter projectID.
     * @param projectID
     */
    public void updateProjectCompletedTime(int projectID) {

        String updateProjectCompletedTime = "update projects p\n" +
                "left outer join (select subprojects.project_id, sum(subprojects.Subproject_Completed_Time) as mysum\n" +
                "from subprojects group by subprojects.Project_ID) as s\n" +
                "on p.project_id = s.project_id\n" +
                "set p.project_completed_time = s.mysum\n" +
                "where p.project_id = ?";

        try
        {
            preparedStatement = ConnectionService.getConnection().prepareStatement(updateProjectCompletedTime);
            preparedStatement.setInt(1, projectID);
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("Error happened in ProjectRepository at updateProjectCompletedTime()" + e.getMessage());
        }
    }

    /**
     * - FMP
     * Updates column Workload_Per_Day in the database based of a projectID
     * The update value workloadPerDay represents the hours you would need to fill to get the project completed
     * on time
     * @param workloadPerDay
     * @param projectID
     */
    public void updateWorkloadPerDay(String workloadPerDay, int projectID)
    {
        String updateWorkloadPerDay = "UPDATE projects SET Workload_Per_Day = ? WHERE Project_ID = ?";

        try
        {
            PreparedStatement preparedStatement = ConnectionService.getConnection().prepareStatement(updateWorkloadPerDay);
            preparedStatement.setString(1, workloadPerDay);
            preparedStatement.setDouble(2, projectID);
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("Error happened in ProjectRepository updateWorkLoadPerDay: " + e.getMessage());
        }
    }


    public void updateEverything(int projectID, int subprojectId) throws SQLException {

        String updateSubprojectEstimatedTime = "update subprojects s\n" +
                "left outer join (select tasks.subproject_id, coalesce(sum(tasks.task_estimated_time), 0) as mysum\n" +
                "from tasks group by tasks.subproject_id) as t\n" +
                "  on s.subproject_id = t.subproject_id\n" +
                "  set s.subproject_estimated_time = t.mysum\n" +
                "where s.subproject_id = ?";

        String updateSubprojectCompletedTime = "update subprojects s\n" +
                "left outer join (select tasks.subproject_id, sum(tasks.task_estimated_time) as mysum\n" +
                "from tasks where tasks.status = 1 group by tasks.subproject_id) as t\n" +
                "on s.subproject_id = t.subproject_id\n" +
                "set s.subproject_completed_time = t.mysum\n" +
                "where s.subproject_id = ?";

        String updateTotalEstimatedTimeProject = "update projects p " +
                "inner join (select subprojects.Project_ID, " +
                "sum(subprojects.Subproject_Estimated_time) as mysum " +
                "from subprojects group by " +
                "subprojects.Project_ID) as s on p.Project_ID = s.Project_ID set p.Project_Estimated_Time" +
                " = s.mysum where p.Project_ID = ?";

        String updateProjectCompletedTime = "update projects p\n" +
                "left outer join (select subprojects.project_id, sum(subprojects.Subproject_Completed_Time) as mysum\n" +
                "from subprojects group by subprojects.Project_ID) as s\n" +
                "on p.project_id = s.project_id\n" +
                "set p.project_completed_time = s.mysum\n" +
                "where p.project_id = ?";


        try {
            ConnectionService.getConnection().setAutoCommit(false);

            //Subproject estimated time
            preparedStatement = ConnectionService.getConnection().prepareStatement(updateSubprojectEstimatedTime);
            preparedStatement.setInt(1, subprojectId);
            preparedStatement.executeUpdate();

            //Subproject completed time
            preparedStatement = ConnectionService.getConnection().prepareStatement(updateSubprojectCompletedTime);
            preparedStatement.setInt(1, subprojectId);
            preparedStatement.executeUpdate();

            //Project estimated time
            preparedStatement = ConnectionService.getConnection().prepareStatement(updateTotalEstimatedTimeProject);
            preparedStatement.setInt(1, projectID);
            preparedStatement.executeUpdate();

            //project Completed time
            preparedStatement = ConnectionService.getConnection().prepareStatement(updateProjectCompletedTime);
            preparedStatement.setInt(1, projectID);
            preparedStatement.executeUpdate();

            projectService.updateWorkloadForProject(projectID);
            subprojectService.updateWorkloadPerDayForSubproject(subprojectId);
            ConnectionService.getConnection().commit();

            ConnectionService.getConnection().setAutoCommit(true);


        } catch (SQLException e) {
            ConnectionService.getConnection().rollback();
        }
    }

    public void updateWorkhoursProject(int projectID, String workhoursforProject){

        String updateWorkloadPerDayProject = "UPDATE projects SET Workload_Per_Day = ? WHERE Project_ID = ?";

        try{

            //Project Workload
            preparedStatement = ConnectionService.getConnection().prepareStatement(updateWorkloadPerDayProject);
            preparedStatement.setString(1, workhoursforProject);
            preparedStatement.setInt(2, projectID);
            preparedStatement.executeUpdate();
        }catch (SQLException e){

        }
    }

    public void updateWorkhoursSubproject(int subprojectId, String workhourForSubproject){

        String updateWorkloadPerDay = "UPDATE subprojects SET Subproject_Workload_Per_Day = ? WHERE Subproject_ID = ?";

        try{
            //Subproject workload
            preparedStatement = ConnectionService.getConnection().prepareStatement(updateWorkloadPerDay);
            preparedStatement.setString(1, workhourForSubproject);
            preparedStatement.setInt(2, subprojectId);
            preparedStatement.executeUpdate();
        }catch (SQLException e){

        }
    }



}
