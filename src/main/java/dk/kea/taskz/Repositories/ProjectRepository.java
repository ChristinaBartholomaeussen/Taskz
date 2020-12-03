package dk.kea.taskz.Repositories;
import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Services.ConnectionService;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {


    PreparedStatement preparedStatement = null;
    /**
     * Henter alle projekter fra databasen
     * @return liste af alle projekter
     */
    public List<Project> selectProjectFromDatabaseByIdNameDeadlineEstimatedTime() {

        updateProjectEstimatedTime();

    String selectAllProjects = "SELECT Project_Id, Project_Name, Project_StartDate, Deadline, Workload_Per_Day, Project_Estimated_Time FROM projects";

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
                        rs.getDouble(6)
                        );


                allProjects.add(project);

            }


    } catch (SQLException e) {
        System.out.println("Happened in ProjectRepository selectProjectFromDatabaseByIdNameDeadlineEstimatedTime: " + e.getMessage());
    }

        return allProjects;
    }


    public void insertProjectIntoDatabase(Project project){

        String insertProjectIntoDatabasen =
                "INSERT INTO projects(Project_Id, Project_Name, Project_StartDate, Deadline) " +
                        "VALUES (?, ?, ?, ?)";

        try{
            preparedStatement = ConnectionService.getConnection().prepareStatement(insertProjectIntoDatabasen);
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

    public void updateProjectEstimatedTime(){

        String updateTotalTime = "update projects p " +
                "inner join (select subprojects.Project_ID, " +
                "sum(subprojects.Subproject_Estimated_time) as mysum " +
                "from subprojects group by " +
                "subprojects.Project_ID) as s on p.Project_ID = s.Project_ID set p.Project_Estimated_Time" +
                " = s.mysum where p.Project_ID = s.Project_ID";

        try{
            preparedStatement = ConnectionService.getConnection().prepareStatement(updateTotalTime);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println("Happened in ProjectRepository updateProjectEstimatedTime: " + e.getMessage());
        }
    }

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
