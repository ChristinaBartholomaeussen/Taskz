package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Repositories.TaskRepository;

public class TaskService {
	TaskRepository taskRepository = new TaskRepository();
	
	public void printData() {
		System.out.println(taskRepository.getAllTasksFromDB());
	}
	
	public int getLatestId() {
		return taskRepository.getLatestIdFromDB();
	}

	/**
	 *  Returns a specific task with use of ID.
	 * @param id
	 * @return
	 */
	public Task getASpecificTask(int id) {
		System.out.println("service");
		return taskRepository.getASpecificTaskFromDB(id);
	}
	
	public void insertTask(Task task) {
		taskRepository.insertNewTaskToDB(task);
	}
	
	public void deleteTask(int id) {
		taskRepository.deleteTaskFromDB(id);
	}
}
