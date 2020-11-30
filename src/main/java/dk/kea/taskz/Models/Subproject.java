package dk.kea.taskz.Models;

import java.util.List;

public class Subproject
{
	private int subprojectId;
    private int parentProjectId;
    private String subprojectName;
    private List<Task> taskList;

    public Subproject(String subprojectName, int parentProjectId)
    {
        this.parentProjectId = parentProjectId;
        this.subprojectName = subprojectName;
    }

    public Subproject(int subprojectId, int parentProjectId, String subprojectName)
    {
        this.subprojectId = subprojectId;
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
}
