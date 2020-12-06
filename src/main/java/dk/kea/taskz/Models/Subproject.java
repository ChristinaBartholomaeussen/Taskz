package dk.kea.taskz.Models;

import java.time.LocalDate;
import java.util.List;

public class Subproject
{
	private int subprojectId;
    private int parentProjectId;
    private double subprojectTotalEstimatedTime;
    private String subprojectWorkloadPerDay;
    private LocalDate subprojectStartDate;
    private LocalDate subprojectDeadline;
    private String subprojectName;
    private List<Task> taskList;

    public Subproject(String subprojectName, int parentProjectId) {
        this.parentProjectId = parentProjectId;
        this.subprojectName = subprojectName;
    }

    public Subproject(int subprojectId, int parentProjectId, String subprojectName) {
        this.subprojectId = subprojectId;
        this.parentProjectId = parentProjectId;
        this.subprojectName = subprojectName;
    }

    public Subproject(String subprojectName, int parentProjectId, LocalDate subprojectStartDate, LocalDate subprojectDeadline) {
        this.subprojectName = subprojectName;
        this.parentProjectId = parentProjectId;
        this.subprojectStartDate = subprojectStartDate;
        this.subprojectDeadline = subprojectDeadline;
    }

    public Subproject(int subprojectId, String subprojectName, int parentProjectId, double subprojectTotalEstimatedTime, LocalDate subprojectStartDate, LocalDate subprojectDeadline, String subprojectWorkloadPerDay) {
        this.subprojectId = subprojectId;
        this.parentProjectId = parentProjectId;
        this.subprojectName = subprojectName;
        this.subprojectStartDate = subprojectStartDate;
        this.subprojectDeadline = subprojectDeadline;
        this.subprojectWorkloadPerDay = subprojectWorkloadPerDay;
        this.subprojectTotalEstimatedTime = subprojectTotalEstimatedTime;
    }

    public String getSubprojectName()
    {
        return subprojectName;
    }

    public List<Task> getTaskList()
    {
        return taskList;
    }

	public int getSubprojectId() {
		return subprojectId;
	}

	public int getParentProjectId() {
		return parentProjectId;
	}

    public void setTaskList(List<Task> taskList)
    {
        this.taskList = taskList;
    }

    public double getSubprojectTotalEstimatedTime() {
        return subprojectTotalEstimatedTime;
    }

    public void setSubprojectTotalEstimatedTime(double subprojectTotalEstimatedTime) {
        this.subprojectTotalEstimatedTime = subprojectTotalEstimatedTime;
    }

    public String getSubprojectWorkloadPerDay() {
        return subprojectWorkloadPerDay;
    }

    public void setSubprojectWorkloadPerDay(String subprojectWorkloadPerDay) {
        this.subprojectWorkloadPerDay = subprojectWorkloadPerDay;
    }

    public LocalDate getSubprojectStartDate() {
        return subprojectStartDate;
    }

    public void setSubprojectStartDate(LocalDate subprojectStartDate) {
        this.subprojectStartDate = subprojectStartDate;
    }

    public LocalDate getSubprojectDeadline() {
        return subprojectDeadline;
    }

    public void setSubprojectDeadline(LocalDate subprojectDeadline) {
        this.subprojectDeadline = subprojectDeadline;
    }

    public void setSubprojectName(String subprojectName) {
        this.subprojectName = subprojectName;
    }
}
