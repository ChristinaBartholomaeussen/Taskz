package dk.kea.taskz.Models;

import java.util.ArrayList;
import java.util.List;

public class Subproject
{
    private String subprojectName;
    private int parentProjectId;
    private List<Task> taskList;

    public Subproject(String subprojectName, int parentProjectId)
    {
        this.subprojectName = subprojectName;
        this.parentProjectId = parentProjectId;
        taskList = new ArrayList<>();
    }

    public String getSubprojectName()
    {
        return subprojectName;
    }

    public List<Task> getTaskList()
    {
        return taskList;
    }
}
