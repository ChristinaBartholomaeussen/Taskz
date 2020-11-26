package dk.kea.taskz.Models;

import dk.kea.taskz.Models.Enums.Status;
import dk.kea.taskz.Models.Enums.Priority;

import java.time.LocalDate;
import java.util.List;

public class Task
{
    private int taskId;
    private int parentSubProjectId;
    private int severity;
    private String taskName;
    private LocalDate deadline;
    private double estimatedTime;
    private Enum<Status> status;
    private Enum<Priority> priority;
    private List<Member> assigned;
}


