package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.dto.TaskDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITaskService
{
    Task save(Task task);
    List<Task> getActiveTasks();
    Optional<Task> findById(UUID id);
    boolean softDelete(UUID id);
    Optional<Task> updateTask(TaskDTO taskDTO);
}
