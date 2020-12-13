package dk.kea.taskz.Repositories;
import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {

    @Autowired
    SubprojectRepository subprojectRepository;

    PreparedStatement preparedStatement = null;
    /**
     * Henter alle projekter fra databasen
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
            System.out.println("Happened in ProjectRepository getAllProjectsFromDatabase(): " + e.getMessage());
        }

        return allProjects;
    }

    public void insertProjectIntoDatabase(Project project) {

        String insertProjectIntoDatabase =
                "INSERT INTO projects(Project_Id, Project_Name, Project_StartDate, Deadline) " +
                        "VALUES (?, ?, ?, ?)";

        try{
            preparedStatement = ConnectionService.getConnection().prepareStatement(insertProjectIntoDatabase);
            preparedStatement.setInt(1, project.getProjectId());
            preparedStatement.setString(2, project.getName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(project.getStartDate()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(project.getDeadline()));

            preparedStatement.execute();

        }catch (SQLException e)
        {
            System.out.println("Happened in ProjectRepository insertProjectIntoDatabase: " + e.getMessage());
        }
    }

    /**
     * Method with JDBC query which deletes the chosen project with the specific projectId
     * Preparestament to sent the string deleteQuery,
     * set the parameterIndex at 1 to be our ptojectId and then execute it.
     * When the project is deleted the subprojects and tasks which are connected through
     * foreing keys will be deleted as well because of cascade in our database.
     * @param projectId
     */
    public void deleteWholeProject(int projectId){

        String deleteQuery = "delete from projects where Project_ID = ?";

        try{
            preparedStatement = ConnectionService.getConnection().prepareStatement(deleteQuery);
            preparedStatement.setInt(1, projectId);

            preparedStatement.execute();
        }catch (SQLException e){
            System.out.println("Happened in ProjectRepository deleteWholeProject: " + e.getMessage());
        }
    }

    /**
     * Method with a string with our sql query.
     * The query updates our projects table, joiner the subprojects on the primary key from projects
     * and the foreign key in subprojects, selects the total sum for
     * estimated time in subprojects, groups them by project_id because of foreign keys,
     * then sets the value of estimated time for each project to be the sum of all subprojects, where
     * the primary key in projects is == to the foreign key in subprojects.
     * PrepareStatement to sent the query and then a executeUpdate method.
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

        }catch (SQLException e){
            System.out.println("Happened in ProjectRepository updateProjectEstimatedTime: " + e.getMessage());
        }
    }

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

    /**
     * - FMP
     * Updates column Project_Completed_Time in the database based of a projectID
     * The update value preliminaryTime represents the completed amount of hours within a project
     * @param preliminaryCompletedTime
     * @param projectID
     */

    public void updateProjectCompletedTime(double preliminaryCompletedTime, int projectID) {
        String updateProjectCompletedTime = "UPDATE projects SET Project_Completed_Time = ? WHERE Project_ID = ?";

        try {
            preparedStatement = ConnectionService.getConnection().prepareStatement(updateProjectCompletedTime);
            preparedStatement.setDouble(1, preliminaryCompletedTime);
            preparedStatement.setInt(2, projectID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
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

    public void updateWorkloadPerDay(String workloadPerDay, int projectID) {
        String updateWorkloadPerDay = "UPDATE projects SET Workload_Per_Day = ? WHERE Project_ID = ?;";

        try {
            PreparedStatement preparedStatement = ConnectionService.getConnection().prepareStatement(updateWorkloadPerDay);
            preparedStatement.setString(1, workloadPerDay);
            preparedStatement.setDouble(2, projectID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error happened in ProjectRepository updateWorkLoadPerDay: " + e.getMessage());
        }
    }
}
