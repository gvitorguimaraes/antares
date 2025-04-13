package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.User;
import br.com.atlantz.antares.model.dto.TaskDTO;
import br.com.atlantz.antares.model.enums.TaskStatusEnum;
import br.com.atlantz.antares.repo.TaskRepo;
import br.com.atlantz.antares.util.error.ServiceInternException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private TaskRepo repo;

    @Autowired
    private IUserService userService;

    @Override
    public Task save(Task task) {
        User user = userService.recoveryUserFromTokenAuth();
        if (user == null) {
            throw new ServiceInternException(TaskService.class, "Authorization error, user ID not valid.");
        }
        task.setUser(user);
        if (task.getStatus() == null) {
            task.setStatus(TaskStatusEnum.NEW);
        }
        return repo.save(task);
    }

    @Override
    public List<Task> getActiveTasks() {
        try {
            User user = userService.recoveryUserFromTokenAuth();
            if (user == null) {
                return Collections.emptyList();
            }
            return repo.findAllByUserAndExclusionIsNull(user);
        } catch (Exception e) {
            throw new ServiceInternException(TaskService.class, "Error in getActiveTasks()", e);
        }
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return repo.findByIdAndExclusionIsNull(id);
    }

    @Override
    public boolean softDelete(UUID id) {
        Task task = findById(id).orElse(null);
        if (task != null) {
            task.setExclusion(LocalDateTime.now());
            repo.save(task);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Task> updateTask(TaskDTO taskDTO) {
        Task task = findById(UUID.fromString(taskDTO.id())).orElse(null);
        if (task != null){
            task.setTitle(taskDTO.title());
            task.setDescription(taskDTO.description());
            task.setStatus(TaskStatusEnum.fromCode(taskDTO.statusCode()));
            if (taskDTO.endDate() != null) {
                task.setEndDate(taskDTO.endDate());
            }
            return Optional.of(repo.save(task));
        }
        return Optional.empty();
    }
}
