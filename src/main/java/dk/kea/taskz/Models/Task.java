package dk.kea.taskz.Models;

import dk.kea.taskz.Models.Enums.Complexity;
import dk.kea.taskz.Models.Enums.Status;
import dk.kea.taskz.Models.Enums.Priority;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Objects;


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
	private String member;
	private String skill;
	private int isDifficult;

	public Task() {

	}
	
	public Task(int taskId, String taskName, Priority priority, Complexity complexity, LocalDate deadline, double estimatedTime, String skill) {
		this.taskId = taskId;
		this.taskName = taskName;
		this.priority = priority;
		this.complexity = complexity;
		this.deadline = deadline;
		this.estimatedTime = estimatedTime;
		this.skill = skill;
	}

	public Task(int parentSubProjectId, String taskName, Enum<Priority> priority, Enum<Complexity> complexity, LocalDate deadline, double estimatedTime,
				Enum<Status> status, String member)
	{
		//this.taskId = taskId;
		this.parentSubProjectId = parentSubProjectId;
		this.taskName = taskName;
		this.deadline = deadline;
		this.estimatedTime = estimatedTime;
		this.status = status;
		this.priority = priority;
		this.complexity = complexity;
		this.member = member;
	}

    public Task(int taskId, int parentSubProjectId, String taskName, Enum<Priority> priority, Enum<Complexity> complexity,
				LocalDate deadline, double estimatedTime, Enum<Status> status, String member, String skill, int isDifficult)
    {
        this.taskId = taskId;
        this.parentSubProjectId = parentSubProjectId;
        this.taskName = taskName;
        this.deadline = deadline;
        this.estimatedTime = estimatedTime;
        this.status = status;
        this.priority = priority;
        this.complexity = complexity;
        this.member = member;
		this.skill = skill;
		this.isDifficult = isDifficult;
        
	}


	public Task(int parentSubProjectId, String taskName, Enum<Priority> priority, Enum<Complexity> complexity, LocalDate deadline, double estimatedTime,
				Enum<Status> status, String member, String skill)
	{
		//this.taskId = taskId;
		this.parentSubProjectId = parentSubProjectId;
		this.taskName = taskName;
		this.deadline = deadline;
		this.estimatedTime = estimatedTime;
		this.status = status;
		this.priority = priority;
		this.complexity = complexity;
		this.member = member;
		this.skill = skill;
		
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

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public int getParentSubProjectId() {
		return parentSubProjectId;
	}

	public void setParentSubProjectId(int parentSubProjectId) {
		this.parentSubProjectId = parentSubProjectId;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public int getIsDifficult() {
		return isDifficult;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		Task task = (Task) o;
		return parentSubProjectId == task.parentSubProjectId && Double.compare(task.estimatedTime, estimatedTime) == 0 && Objects.equals(taskName, task.taskName) && Objects.equals(deadline, task.deadline) && Objects.equals(status, task.status) && Objects.equals(priority, task.priority) && Objects.equals(complexity, task.complexity) && Objects.equals(member, task.member) && Objects.equals(skill, task.skill);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(parentSubProjectId, taskName, deadline, estimatedTime, status, priority, complexity, member, skill);
	}

}


