package dk.kea.taskz.Services;
import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;


	public void insertTask(Task task) {
		taskRepository.insertNewTaskToDB(task);
	}
	
	public void deleteTask(int id) {
		taskRepository.deleteTaskFromDB(id);
	}

	/**
	 * - FMP
	 * Vehicle for updating task status
	 * @param idTask
	 */

	public void updateTaskStatus(int idTask) {
		taskRepository.updateTaskStatus(idTask);
	}
	
	
	public ArrayList<Task> getAllTasks(int id) {
		return taskRepository.getAllTaskToOneMember(id);
	} 
	
	public Task getEarlistDeadline(int id) {
		return taskRepository.getEarliestDeadLineFromDB(id);
	}
}
