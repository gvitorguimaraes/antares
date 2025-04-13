package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.Galaxy;
import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.User;
import br.com.atlantz.antares.model.dto.GalaxyDTO;
import br.com.atlantz.antares.model.dto.TaskDTO;
import br.com.atlantz.antares.model.enums.TaskStatusEnum;
import br.com.atlantz.antares.repo.GalaxyRepo;
import br.com.atlantz.antares.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService implements ITaskService
{
    @Autowired
    private TaskRepo repo;

    @Autowired
    private IGalaxyService galaxyService;

    @Autowired
    private IUserService userService;

    @Override
    public Task save(Task task)
    {
        // Can be removed in future versions, the correct galaxy will be provided by user
        Galaxy galaxy = galaxyService.getActiveGalaxies().getFirst();
        task.setGalaxy(galaxy);

        return repo.save(task);
    }

    @Override
    public List<Task> getActiveTasks()
    {
        try
        {
            User user =  userService.recoveryUserFromTokenAuth();
            if (user != null && user.getUniverse() != null) {
                return repo.findByGalaxyAndExclusionIsNull(user.getUniverse().getGalaxies().getFirst());
            }
            return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Task findById(UUID id)
    {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void softDelete(Task task)
    {
        if (task != null
                && task.getId() != null
                && !task.isDeleted())
        {
            task.setExclusion(LocalDateTime.now());
            repo.save(task);
        }
    }

    @Override
    public Task updateTask(TaskDTO taskDTO)
    {
        Task task = findById(UUID.fromString(taskDTO.id()));

        if (task != null)
        {
            task.setTitle(taskDTO.title());
            task.setDescription(taskDTO.description());
            task.setStatus(TaskStatusEnum.fromCode(taskDTO.statusCode()));
            task.setEnd_date(task.getEnd_date());
            return repo.save(task);
        }
        return null;
    }
}
