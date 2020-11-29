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
    private List<Member> assigned;

    public Task(int taskId, int parentSubProjectId, String taskName, LocalDate deadline, double estimatedTime,
                Enum<Status> status, Enum<Priority> priority, Enum<Complexity> complexity, List<Member> assigned)
    {
        this.taskId = taskId;
        this.parentSubProjectId = parentSubProjectId;
        this.taskName = taskName;
        this.deadline = deadline;
        this.estimatedTime = estimatedTime;
        this.status = status;
        this.priority = priority;
        this.complexity = complexity;
        this.assigned = assigned;
    }
}


