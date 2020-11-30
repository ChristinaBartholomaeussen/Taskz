package dk.kea.taskz.Repositories;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Services.ConnectionService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

ConnectionService connection = new ConnectionService();

    /**
     * Henter alle projekter fra databasen
     * @return liste af alle projekter
     */
    public List<Project> selectProjectFromDatabaseByIdNameDeadlineEstimatedTime() {

    String selectAllProjects = "SELECT Project_Id, Project_Name, Project_StartDate, Deadline, Project_Estimated_Time FROM projects";

    List<Project> allProjects = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.establishConnection().prepareStatement(selectAllProjects);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                Project project = new Project(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDate(3).toLocalDate(),
                        rs.getDate(4).toLocalDate(),
                        rs.getDouble(5)
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
            PreparedStatement preparedStatement = connection.establishConnection().prepareStatement(insertProjectIntoDatabasen);
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

    public void deleteWholeProject(int projectId){

        String deleteQuery = "delete from projects where Project_ID = ?";

        try{
            PreparedStatement preparedStatement = connection.establishConnection().prepareStatement(deleteQuery);
            preparedStatement.setInt(1, projectId);

            preparedStatement.execute();
        }catch (SQLException e){
            System.out.println("Happened in ProjectRepository deleteWholeProject: " + e.getMessage());
        }

    }


    public List<Double> calculateEstimatedTimeForProject(int projectId){

        String calculate = "select tasks.Task_Estimated_Time from projects\n" +
                "join subprojects on projects.Project_ID = subprojects.Project_ID\n" +
                "left outer join tasks on subprojects.Subproject_ID = tasks.SubProject_ID\n" +
                "where projects.Project_ID = ?";

        List<Double> allTasksToCalculate = new ArrayList<>();

        try{

            PreparedStatement preparedStatement = connection.establishConnection().prepareStatement(calculate);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                double i = rs.getInt(1);

                allTasksToCalculate.add(i);
            }


        }catch (SQLException e){
            System.out.println("Happened in ProjectRepository calculateEstimatedTimeForProject: " + e.getMessage());
        }

        return allTasksToCalculate;
    }



	
}
