package dk.kea.taskz.Services;

import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Repositories.SubprojectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubprojectService
{
    @Autowired
    SubprojectRepository subprojectRepository;

    public List<Subproject> getAllAssociatedSubprojects(int projectId)
    {
        return subprojectRepository.getAllAssociatedSubprojects(projectId);

    }

}
