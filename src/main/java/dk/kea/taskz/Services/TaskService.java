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
	
	
	public void insertTask(Task task) {
		taskRepository.insertNewTaskToDB(task);
	}
}
