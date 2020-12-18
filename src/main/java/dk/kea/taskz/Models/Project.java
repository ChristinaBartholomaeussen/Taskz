package dk.kea.taskz.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**(FMP, OVO, RBP, CMB)
 * Blueprint for object/model Project with getter/setter.
 * Constructor overload because we need constructor with
 * different parameters
 */
public class Project
{
    private int projectId;
    private String name;
    private LocalDate startDate;
    private LocalDate deadline;
    private String totalWorkHoursPerDay;
    private double totalEstimatedTime;
    private double completedTime;
    private List<Subproject> associatedSubprojects;

    public Project(){

    }
    public Project(String name) {
        this.name = name;
    }

    public Project(LocalDate startDate, LocalDate deadline, double totalEstimatedTime, double completedTime) {
        this.startDate = startDate;
        this.deadline = deadline;
        this.totalEstimatedTime = totalEstimatedTime;
        this.completedTime= completedTime;
    }

    public Project(String name, LocalDate startDate, LocalDate deadline) {
        this.name = name;
        this.startDate = startDate;
        this.deadline = deadline;
    }

    public Project(int projectId, String name, LocalDate startDate, LocalDate deadline, double totalEstimatedTime){
        this.projectId = projectId;
        this.name = name;
        this.startDate = startDate;
        this.deadline = deadline;
        this.totalEstimatedTime = totalEstimatedTime;
    }

    public Project(int projectId, String name, LocalDate startDate, LocalDate deadline, String totalWorkHoursPerDay, double totalEstimatedTime) {
        this.projectId = projectId;
        this.name = name;
        this.startDate = startDate;
        this.deadline = deadline;
        this.totalWorkHoursPerDay = totalWorkHoursPerDay;
        this.totalEstimatedTime = totalEstimatedTime;
        this.associatedSubprojects = new ArrayList<>();
    }

    public Project(int projectId, String name, LocalDate startDate, LocalDate deadline, String totalWorkHoursPerDay, double totalEstimatedTime, double completedTime) {
        this.projectId = projectId;
        this.name = name;
        this.startDate = startDate;
        this.deadline = deadline;
        this.totalWorkHoursPerDay = totalWorkHoursPerDay;
        this.completedTime = completedTime;
        this.totalEstimatedTime = totalEstimatedTime;
        this.associatedSubprojects = new ArrayList<>();
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getTotalWorkHoursPerDay() {
        return totalWorkHoursPerDay;
    }

    public void setTotalWorkHoursPerDay(String totalWorkHoursPerDay) {
        this.totalWorkHoursPerDay = totalWorkHoursPerDay;
    }

    public double getTotalEstimatedTime() {
        return totalEstimatedTime;
    }

    public void setTotalEstimatedTime(double totalEstimatedTime) {
        this.totalEstimatedTime = totalEstimatedTime;
    }

    public double getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(double completedTime) {
        this.completedTime = completedTime;
    }

    public List<Subproject> getAssociatedSubprojects()
    {
        return associatedSubprojects;
    }

    public void setAssociatedSubprojects(List<Subproject> associatedSubprojects)
    {
        this.associatedSubprojects = associatedSubprojects;
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
        Project project = (Project) o;
        return Objects.equals(name, project.name) && Objects.equals(startDate, project.startDate) && Objects.equals(deadline, project.deadline);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, startDate, deadline);
    }
}
