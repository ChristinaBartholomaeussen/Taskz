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

	/**
	 * -RBP
	 * Receives a Task from the method parameter, which is passed on insertNewTaskToDB method in TaskRepository
	 * and uses the two extensionmethods, getMember() and getTaskId() in the setATaskToRelocateResources
	 * in the Task Repository
	 * @param task
	 */
	public void insertTask(Task task)
	{
		taskRepository.insertNewTaskToDB(task);

		taskRepository.setATaskToRelocateResources(task.getMember(),task.getTaskId());
	}

	/**
	 * Receives an integer id from the method parameter, which is passed on to the
	 * deleteTaskFromDB method in Task Repository
	 * @param id
	 */
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

	/**
	 * - OVO
	 * Gets all the tasks that is specific to a single member.
	 * @param memberId
	 * @return
	 */
	public ArrayList<Task> getAllTasks(int memberId) {

		return taskRepository.getAllTaskToOneMember(memberId);
	}

	/**
	 * - OVO
	 * We use this method to get task closet to deadline.
	 * @param id
	 * @return
	 */
	public Task getEarlistDeadline(int id) {
		return taskRepository.getEarliestDeadLineFromDB(id);
	}
}
