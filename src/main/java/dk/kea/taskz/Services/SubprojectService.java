package dk.kea.taskz.Services;

import dk.kea.taskz.Repositories.SubprojectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubprojectService
{
    @Autowired
    SubprojectRepository subprojectRepository;

}
