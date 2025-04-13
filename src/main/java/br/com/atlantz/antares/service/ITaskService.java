package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.dto.TaskDTO;

import java.util.List;
import java.util.UUID;

public interface ITaskService
{
    Task save(Task task);
    List<Task> getActiveTasks();
    Task findById(UUID id);
    void softDelete(Task task);
    Task updateTask(TaskDTO taskDTO);
}
