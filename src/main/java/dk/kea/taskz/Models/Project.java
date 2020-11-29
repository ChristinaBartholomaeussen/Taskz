package dk.kea.taskz.Models;

import java.time.LocalDate;

public class Project {
    private int projectId;
    private String name;
    private LocalDate startDate;
    private LocalDate deadline;
    private double totalWorkHoursPerDay;
    private double totalEstimatedTime;

    public Project(String name) {
        this.name = name;
    }

    /**
     * HTML connection test // Delete when finished
     * @param name
     * @param startDate
     * @param deadline
     */

    public Project(String name, LocalDate startDate, LocalDate deadline) {
        this.name = name;
        this.startDate = startDate;
        this.deadline = deadline;
    }

    public Project(int projectId, String name, LocalDate deadline, double totalEstimatedTime){
        this.projectId = projectId;
        this.name = name;
        this.deadline = deadline;
        this.totalEstimatedTime = totalEstimatedTime;
    }

    public Project(int projectId, String name, LocalDate startDate, LocalDate deadline, double totalWorkHoursPerDay, double totalEstimatedTime) {
        this.projectId = projectId;
        this.name = name;
        this.startDate = startDate;
        this.deadline = deadline;
        this.totalWorkHoursPerDay = totalWorkHoursPerDay;
        this.totalEstimatedTime = totalEstimatedTime;
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

    public double getTotalWorkHoursPerDay() {
        return totalWorkHoursPerDay;
    }

    public void setTotalWorkHoursPerDay(double totalWorkHoursPerDay) {
        this.totalWorkHoursPerDay = totalWorkHoursPerDay;
    }

    public double getTotalEstimatedTime() {
        return totalEstimatedTime;
    }

    public void setTotalEstimatedTime(double totalEstimatedTime) {
        this.totalEstimatedTime = totalEstimatedTime;
    }

    @Override
    public String toString() {
        return "Project{" +
                "idProject=" + projectId +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", deadline=" + deadline +
                ", totalWorkHoursPerDay=" + totalWorkHoursPerDay +
                ", totalEstimatedTime=" + totalEstimatedTime +
                '}';
    }
}
