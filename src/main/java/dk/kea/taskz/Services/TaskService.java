package dk.kea.taskz.Services;
import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Repositories.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

	TaskRepository taskRepository = new TaskRepository();


	public void insertTask(Task task) {
		taskRepository.insertNewTaskToDB(task);
	}
	
	public void deleteTask(int id) {
		taskRepository.deleteTaskFromDB(id);
	}

	/**
	 * - FMP
	 * Service layer update task status vehicle
	 * @param idTask
	 */

	public void updateTaskStatus(int idTask) {
		taskRepository.updateTaskStatus(idTask);
	}
}
