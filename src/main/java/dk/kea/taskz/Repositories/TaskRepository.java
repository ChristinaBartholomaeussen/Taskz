package dk.kea.taskz.Repositories;

import com.mysql.cj.protocol.Resultset;
import dk.kea.taskz.Models.Enums.Complexity;
import dk.kea.taskz.Models.Enums.Priority;
import dk.kea.taskz.Models.Enums.Status;
import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Services.ConnectionService;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class TaskRepository {
	
	ConnectionService connection = new ConnectionService();

	/**
	 *  - OVO
	 *  Get all the task from the database
	 * @return
	 */
	public List<Task> getAllTasksFromDB() {
		ArrayList<Task> taskList = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.establishConnection().prepareStatement("SELECT * from taskz.Tasks");

			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				 Task task = new Task(resultSet.getInt(1), 
						 resultSet.getInt(2), 
						 resultSet.getString(3),
						Priority.values()[resultSet.getInt(4)],
						 Complexity.values()[resultSet.getInt(5)], 
						 resultSet.getDate(6).toLocalDate(), 
						 resultSet.getDouble(7), 
						 Status.values()[resultSet.getInt(8)],
						 resultSet.getString(9)
						);
				 taskList.add(task);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return taskList;
	}

	/**
	 *  - OVO
	 *  Returns a specific task from the database. Uses the ID to search for it.
	 * @param Task_ID
	 * @return
	 */
	public Task getASpecificTaskFromDB(int Task_ID) {
		String selectTask = " SELECT * FROM taskz.tasks WHERE Task_ID = ?";
		Task taskToReturn = new Task();
		
		try {
			PreparedStatement preparedStatement = connection.establishConnection().prepareStatement(selectTask);
			preparedStatement.setInt(1, Task_ID);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				
				taskToReturn = new Task(resultSet.getInt(1), 
						resultSet.getInt(2), 
						resultSet.getString(3),
						Priority.values()[resultSet.getInt(4)], 
						Complexity.values()[resultSet.getInt(5)], 
						resultSet.getDate(6).toLocalDate(), 
						resultSet.getDouble(7), 
						Status.values()[resultSet.getInt(8)],
						resultSet.getString(9)
				);

			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		
		return taskToReturn;
	}

	/**
	 * Gets the latest id from the database
	 * @return
	 */
	public int getLatestIdFromDB() {
		try {
			PreparedStatement preparedStatement = connection.establishConnection().prepareStatement("SELECT * FROM taskz.tasks ORDER BY Task_ID DESC LIMIT 1;");
			ResultSet resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				return resultSet.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return 404;
	}

	/**
	 *  - OVO
	 *  Creates a task from the database.
	 * @param task
	 */
	public void insertNewTaskToDB(Task task) {
		String insertTaskSQL = "INSERT INTO taskz.tasks(Task_ID, SubProject_ID, Task_Name, Priority, Complexity, Task_Deadline, Task_Estimated_Time, Status, Member) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";

		int id = getLatestIdFromDB() + 1;
		System.out.println(id);
		try {
			PreparedStatement preparedStatement = connection.establishConnection().prepareStatement(insertTaskSQL);
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, task.getParentSubProjectId());
			preparedStatement.setString(3, task.getTaskName());
			preparedStatement.setInt(4, task.getPriority().ordinal());
			preparedStatement.setInt(5, task.getComplexity().ordinal());
			preparedStatement.setDate(6, java.sql.Date.valueOf(task.getDeadline()));
			preparedStatement.setDouble(7, task.getEstimatedTime());
			preparedStatement.setInt(8, task.getStatus().ordinal());
			preparedStatement.setString(9,task.getMember());

			preparedStatement.execute();
		} catch (SQLException e) {
			System.out.println("Error happened in TaskRepository at insertNewTaskToDB" + e.getMessage());
		}
	}

	/**
	 *  - OVO
	 *  Deletes a task form the database
	 * @param Task_ID
	 */
	public void deleteTaskFromDB(int Task_ID) {
		String deleteTaskFromDB =  "DELETE FROM taskz.tasks WHERE Task_ID = ?";
		try {
			PreparedStatement preparedStatement = connection.establishConnection().prepareStatement(deleteTaskFromDB);
			preparedStatement.setInt(1, Task_ID);
			
			preparedStatement.execute();
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public List<Task> getAllAssociatedTasksToSubproject(int subprojectId)
	{
		String getAllAssociatedTasksToSubproject = "SELECT * FROM taskz.tasks WHERE SubProject_ID = " + subprojectId;
		List<Task> taskList = new ArrayList<>();

		try
		{
			PreparedStatement ps = connection.establishConnection().prepareStatement(getAllAssociatedTasksToSubproject);
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				taskList.add(new Task(
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						Priority.values()[rs.getInt(4)],
						Complexity.values()[rs.getInt(5)],
						rs.getDate(6).toLocalDate(),
						rs.getDouble(7),
						Status.values()[rs.getInt(8)],
						rs.getString(9)));
			}

		}
		catch(Exception e)
		{
			System.out.println("Error happened in TaskRepository at getAllAssociatedTasksToSubproject(): " + e);
		}

		return taskList;
	}

	/**
	 * - FMP
	 * Selects a specific task Status from the database
	 * @param idTask
	 * @return
	 */

	public int selectTaskStatusByID(int idTask) {
		String selectStatus = "SELECT Status FROM tasks WHERE Task_ID = ?";

		int taskStatus = 0;

		try {
			PreparedStatement preparedStatement = connection.establishConnection().prepareStatement(selectStatus);
			preparedStatement.setInt(1, idTask);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				taskStatus = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Error happened in TaskRepository at selectTaskStatusByID(): \n" + e.getMessage());
		}
		return taskStatus;
	}

	/**
	 * - FMP
	 * Updates a single task Status, with the goal of changing the task's display values on /subprojects
	 * @param idTask
	 */

	public void updateTaskStatus(int idTask) {
		String updateTaskByID = "UPDATE tasks SET Status = ? WHERE Task_ID = ?";

		int preleminaryStatus = selectTaskStatusByID(idTask);

		if (preleminaryStatus == 0) {
			try {
				PreparedStatement preparedStatement = connection.establishConnection().prepareStatement(updateTaskByID);
				preparedStatement.setInt(1, 1);
				preparedStatement.setInt(2, idTask);

				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Error happened in TaskRepository at updateTaskStatus() if preleminaryStatus == 0: \n" + e.getMessage());
			}
		}

		if (preleminaryStatus == 1) {
			try {
				PreparedStatement preparedStatement = connection.establishConnection().prepareStatement(updateTaskByID);
				preparedStatement.setInt(1, 0);
				preparedStatement.setInt(2, idTask);

				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Error happened in TaskRepository at updateTaskStatus() if preleminaryStatus == 1: \n" + e.getMessage());
			}
		}
	}
}
