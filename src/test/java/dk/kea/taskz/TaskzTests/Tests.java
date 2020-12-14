package dk.kea.taskz.TaskzTests;

import com.mysql.cj.protocol.Resultset;
import dk.kea.taskz.Models.Enums.Complexity;
import dk.kea.taskz.Models.Enums.Priority;
import dk.kea.taskz.Models.Enums.Status;
import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Repositories.ProjectRepository;
import dk.kea.taskz.Repositories.SubprojectRepository;
import dk.kea.taskz.Repositories.TaskRepository;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class Tests
{
    private Connection connection()
    {
        String url = "jdbc:mysql://den1.mysql2.gear.host:3306/taskz";
        String user = "taskz";
        String pass = "taskz!";

        try
        {
            return DriverManager.getConnection(url,user,pass);
        }
        catch(Exception e)
        {
            System.out.println("Error happened in Tests at connection()");
            return null;
        }
    }

    @Test
    public void insertProjectIntoDatabase()
    {
        // ARRANGE
        ProjectRepository projectRepository = new ProjectRepository();
        Project projectToBeInserted = new Project("UNITTESTPROJECT", LocalDate.now(), LocalDate.now().plusDays(7));
        Project projectToTest = new Project();

        // ACT
        projectRepository.insertProjectIntoDatabase(projectToBeInserted);

        Connection connection;

        String url = "jdbc:mysql://den1.mysql2.gear.host:3306/taskz";
        String user = "taskz";
        String pass = "taskz!";
        try
        {
            connection = DriverManager.getConnection(url, user, pass);
            String sqlQueryToGetProjectFromDatabase = "SELECT taskz.projects.Project_Name, taskz.projects.Project_StartDate, taskz.projects.Deadline FROM projects WHERE Project_Name = 'UNITTESTPROJECT'";

            PreparedStatement ps = connection.prepareStatement(sqlQueryToGetProjectFromDatabase);

            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                projectToTest.setName(rs.getString(1));
                projectToTest.setStartDate(rs.getDate(2).toLocalDate());
                projectToTest.setDeadline(rs.getDate(3).toLocalDate());
            }

            String deleteEvidence = "DELETE FROM projects WHERE Project_Name = 'UNITTESTPROJECT'";

            ps = connection.prepareStatement(deleteEvidence);
            ps.execute();

        }
        catch(Exception e)
        {
            System.out.println("Error happened in Tests.java at insertProjectIntoDabase()");
        }

        // ASSERT
        assertEquals(projectToTest.getName(),projectToBeInserted.getName());
        assertEquals(projectToTest.getStartDate(),projectToBeInserted.getStartDate());
        assertEquals(projectToTest.getDeadline(),projectToBeInserted.getDeadline());
        assertEquals(projectToTest,projectToBeInserted);
    }

    @Test
    public void testInsertSubprojectToDatabase()
    {
        // ARRANGE
        ProjectRepository projectRepository = new ProjectRepository();
        projectRepository.insertProjectIntoDatabase(new Project("UNITTESTPROJECT", LocalDate.now(), LocalDate.now().plusDays(7)));
        SubprojectRepository subprojectRepository = new SubprojectRepository();

        int projectId = 0;
        Subproject subprojectToBeInserted = new Subproject("UNITTESTSUBPROJECT",projectId,LocalDate.now(),LocalDate.now().plusDays(7));
        Subproject subprojectToTest = new Subproject();

        try
        {
            String sqlGetProjectIdOfTestProject = "SELECT Project_ID FROM projects WHERE Project_Name  = 'UNITTESTPROJECT'";
            PreparedStatement ps = connection().prepareStatement(sqlGetProjectIdOfTestProject);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                subprojectToBeInserted.setParentProjectId(rs.getInt(1));
                subprojectToTest.setParentProjectId(rs.getInt(1));
            }

            // ACT
            subprojectRepository.insertSubProjectIntoDB(subprojectToBeInserted);

            String sqlGetSubprojectFromDatabase = "SELECT Subproject_Name, Subproject_StartDate, Subproject_Deadline FROM subprojects WHERE Subproject_Name = 'UNITTESTSUBPROJECT'";
            ps = connection().prepareStatement(sqlGetSubprojectFromDatabase);
            rs = ps.executeQuery();

            while(rs.next())
            {
                subprojectToTest.setSubprojectName(rs.getString(1));
                subprojectToTest.setSubprojectStartDate(rs.getDate(2).toLocalDate());
                subprojectToTest.setSubprojectDeadline(rs.getDate(3).toLocalDate());
            }

            String deleteEvidence = "DELETE FROM projects WHERE Project_Name = 'UNITTESTPROJECT'";
            PreparedStatement psThree = connection().prepareStatement(deleteEvidence);
            psThree.execute();
        }
        catch(Exception e)
        {
            System.out.println("Error happened in Tests at testInsertSubprojectToDatabase(): " + e.getMessage());
        }

        // ASSERT
        assertEquals(subprojectToTest,subprojectToBeInserted);
    }

	@Test
    public void testInsertTaskToDatabase()
    {
        TaskRepository taskRepository = new TaskRepository();
        Task taskToInsertAndRetrieveFromDatabase = new Task
                (9999,
                        "TaskTest",
                        Priority.CRITICAL,
                        Complexity.VERY_HARD,
                        LocalDate.now().plusDays(7),
                        10,
                        Status.ACTIVE,
                        "Tester",
                        "UnitTest");

        Task taskToTest = new Task();
        taskRepository.insertNewTaskToDB(taskToInsertAndRetrieveFromDatabase);

        Connection connection;

        String url = "jdbc:mysql://den1.mysql2.gear.host:3306/taskz";
        String user = "taskz";
        String pass = "taskz!";
        try
        {
            connection = DriverManager.getConnection(url, user, pass);
            String sqlQueryToGetTestTask = "SELECT * FROM tasks WHERE Task_Name = 'TaskTest'";

            PreparedStatement ps = connection.prepareStatement(sqlQueryToGetTestTask);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                taskToTest.setParentSubProjectId(rs.getInt(1));
                taskToTest.setTaskName(rs.getString(2));
                taskToTest.setPriority(Priority.values()[rs.getInt(3)]);
                taskToTest.setComplexity(Complexity.values()[rs.getInt(4)]);
                taskToTest.setDeadline(rs.getDate(5).toLocalDate());
                taskToTest.setEstimatedTime(rs.getInt(6));
                taskToTest.setMember(rs.getString(7));
                taskToTest.setSkill(rs.getString(8));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // Always fails, as there is no parentSubprojectIdAvailable until further notice /RBP
        assertEquals(taskToTest,taskToInsertAndRetrieveFromDatabase);
    }


}
