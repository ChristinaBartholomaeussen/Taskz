package dk.kea.taskz.Models;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
/**(FMP, OVO, RBP, CMB)
 * Blueprint for object/model Subproject with getters/setters.
 * Constructor overloads because we need constructors with
 * different parameters
 */
public class Subproject
{
	private int subprojectId;
    private int parentProjectId;
    private double subprojectTotalEstimatedTime;
    private double subprojectCompletedTime;
    private String subprojectWorkloadPerDay;
    private String subprojectName;
    private LocalDate subprojectStartDate;
    private LocalDate subprojectDeadline;
    private List<Task> taskList;

    public Subproject()
    {
    }
/*
    public Subproject(String subprojectName, int parentProjectId) {
        this.parentProjectId = parentProjectId;
        this.subprojectName = subprojectName;
    } */

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

    public Subproject(int subprojectId, String subprojectName, int parentProjectId, double subprojectTotalEstimatedTime, LocalDate subprojectStartDate, LocalDate subprojectDeadline, String subprojectWorkloadPerDay, double subprojectCompletedTime) {
        this.subprojectId = subprojectId;
        this.parentProjectId = parentProjectId;
        this.subprojectName = subprojectName;
        this.subprojectStartDate = subprojectStartDate;
        this.subprojectDeadline = subprojectDeadline;
        this.subprojectWorkloadPerDay = subprojectWorkloadPerDay;
        this.subprojectTotalEstimatedTime = subprojectTotalEstimatedTime;
        this.subprojectCompletedTime = subprojectCompletedTime;
    }

    public String getSubprojectName()
    {
        return subprojectName;
    }

    public List<Task> getTaskList()
    {
        return taskList;
    }

    public void setSubprojectId(int subprojectId){
        this.subprojectId = subprojectId;
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

    public double getSubprojectCompletedTime() {
        return subprojectCompletedTime;
    }

    public void setSubprojectCompletedTime(double subprojectCompletedTime) {
        this.subprojectCompletedTime = subprojectCompletedTime;
    }

    // USED ONLY FOR TESTING
    public void setParentProjectId(int parentProjectId)
    {
        this.parentProjectId = parentProjectId;
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
        Subproject that = (Subproject) o;
        return parentProjectId == that.parentProjectId && Objects.equals(subprojectStartDate, that.subprojectStartDate) && Objects.equals(subprojectDeadline, that.subprojectDeadline);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(parentProjectId, subprojectStartDate, subprojectDeadline);
    }
}
