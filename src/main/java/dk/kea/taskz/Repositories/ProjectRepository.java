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
     * Henter alle projecter fra databasen
     * @return liste af alle projekter
     */
    public List<Project> selectProjectFromDatabaseByIdNameDeadlineEstimatedTime(){

    String selectAllProjects = "SELCECT Project_Id, Project_Name, Deadline, Project_Estimated_Time FROM projects";

    List<Project> allProjects = new ArrayList<>();

    try{
        PreparedStatement preparedStatement = connection.establishConnection().prepareStatement(selectAllProjects);

        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            Project project = new Project(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getDate(4).toLocalDate(),
                    rs.getDouble(6)
                    );
            allProjects.add(project);
        }

    }catch (SQLException e){
        System.out.println(e.getMessage());
    }

    return allProjects;

    }


    public void insertProjectIntoDatabase(Project project){

        String insertProjectIntoDatabasen =
                "INSERT INTO projects(Project_Id, Project_Name, Project_StartDate, Deadline, Workload_Per_Day, Project_Estimated_Time) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement preparedStatement = connection.establishConnection().prepareStatement(insertProjectIntoDatabasen);
            preparedStatement.setInt(1, project.getProjectId());
            preparedStatement.setString(2, project.getName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(project.getStartDate()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(project.getDeadline()));
            preparedStatement.setNull(5, Types.DOUBLE);
            preparedStatement.setDouble(6, project.getTotalEstimatedTime());

            preparedStatement.execute();

        }catch (SQLException e)
        {
            System.out.println("Error" + e.getMessage());
        }


    }







}
