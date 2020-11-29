package dk.kea.taskz.Repositories;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Services.ConnectionService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

ConnectionService connection = new ConnectionService();


    /**
     * Henter alle projecter fra databasen
     * @return liste af alle projekter
     */
    public List<Project> selectAllProjectsFromDatabase(){

    String selectAllProjects = "SELECT * FROM projects";

    List<Project> allProjects = new ArrayList<>();

    try{
        PreparedStatement preparedStatement = connection.establishConnection().prepareStatement(selectAllProjects);

        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            Project project = new Project(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getDate(3).toLocalDate(),
                    rs.getDate(4).toLocalDate(),
                    rs.getDouble(5),
                    rs.getDouble(6)
                    );
            allProjects.add(project);
        }

    }catch (SQLException e){
        System.out.println(e.getMessage());
    }

    System.out.println(allProjects.toString());
    return allProjects;

    }

}
