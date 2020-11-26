package dk.kea.taskz.Models;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

public class Project {
    private int idProject;
    private String name;
    private LocalDate startDate;
    private LocalDate deadline;
    private double totalWorkHoursPerDay;
    private double totalEstimatedTime;

    public Project(String name) {
        this.name = name;
    }

    public Project(int idProject, String name, LocalDate startDate, LocalDate deadline, double totalWorkHoursPerDay, double totalEstimatedTime) {
        this.idProject = idProject;
        this.name = name;
        this.startDate = startDate;
        this.deadline = deadline;
        this.totalWorkHoursPerDay = totalWorkHoursPerDay;
        this.totalEstimatedTime = totalEstimatedTime;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
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
                "idProject=" + idProject +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", deadline=" + deadline +
                ", totalWorkHoursPerDay=" + totalWorkHoursPerDay +
                ", totalEstimatedTime=" + totalEstimatedTime +
                '}';
    }
}
