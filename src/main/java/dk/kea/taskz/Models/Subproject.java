package dk.kea.taskz.Models;

import dk.kea.taskz.Services.SubprojectService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Subproject
{
    @Autowired
    SubprojectService subprojectService;

    private int subprojectId;
    private int parentProjectId;
    private String subprojectName;
    private List<Task> taskList;

    public Subproject(String subprojectName, int parentProjectId)
    {
        this.parentProjectId = parentProjectId;
        this.subprojectName = subprojectName;
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
