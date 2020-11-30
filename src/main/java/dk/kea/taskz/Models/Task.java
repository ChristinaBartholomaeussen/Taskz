package dk.kea.taskz.Models;

import dk.kea.taskz.Models.Enums.Complexity;
import dk.kea.taskz.Models.Enums.Status;
import dk.kea.taskz.Models.Enums.Priority;

import java.time.LocalDate;
import java.util.List;

public class Task
{
    private int taskId;
	private int parentSubProjectId;
    private String taskName;
	private LocalDate deadline;
	private double estimatedTime;
	private Enum<Status> status;
	private Enum<Priority> priority;
	private Enum<Complexity> complexity;
//private List<Member> assigned;

    public Task(int taskId, int parentSubProjectId, String taskName, Enum<Priority> priority, Enum<Complexity> complexity, LocalDate deadline, double estimatedTime,
                Enum<Status> status)
    {
        this.taskId = taskId;
        this.parentSubProjectId = parentSubProjectId;
        this.taskName = taskName;
        this.deadline = deadline;
        this.estimatedTime = estimatedTime;
        this.status = status;
        this.priority = priority;
        this.complexity = complexity;
        //this.assigned = assigned;

		/**
		 *  - OVO
		 *  Der er ikke sat assigned members på i databasen. Derfor udkommentere jeg alt koden med assigned members, fordi det ikke er færdig implementeret endnu.
		 *  29/11
		 */
}


	public Task(int parentSubProjectId, String taskName, Enum<Priority> priority, Enum<Complexity> complexity, LocalDate deadline, double estimatedTime,
				Enum<Status> status)
	{
		this.taskId = taskId;
		this.parentSubProjectId = parentSubProjectId;
		this.taskName = taskName;
		this.deadline = deadline;
		this.estimatedTime = estimatedTime;
		this.status = status;
		this.priority = priority;
		this.complexity = complexity;
		//this.assigned = assigned;

		/**
		 *  - OVO
		 *  Der er ikke sat assigned members på i databasen. Derfor udkommentere jeg alt koden med assigned members, fordi det ikke er færdig implementeret endnu.
		 *  29/11
		 */
	}
	
	public Task() {
    	
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public double getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(double estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public Enum<Status> getStatus() {
		return status;
	}

	public void setStatus(Enum<Status> status) {
		this.status = status;
	}

	public Enum<Priority> getPriority() {
		return priority;
	}

	public void setPriority(Enum<Priority> priority) {
		this.priority = priority;
	}

	public Enum<Complexity> getComplexity() {
		return complexity;
	}

	public void setComplexity(Enum<Complexity> complexity) {
		this.complexity = complexity;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}


	public int getParentSubProjectId() {
		return parentSubProjectId;
	}

	public void setParentSubProjectId(int parentSubProjectId) {
		this.parentSubProjectId = parentSubProjectId;
	}
	
	@Override
	public String toString() {
    	return "Sub project id: " + parentSubProjectId + "\nTask name: " + taskName + "\nPrio: " + priority + "\nComplexity: " + complexity + "\ndeadline: " + deadline + "\nestimatidtime: " + estimatedTime + "\nStatus: " + status;
	}
}


