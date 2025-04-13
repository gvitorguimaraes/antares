package br.com.atlantz.antares.controller;

import br.com.atlantz.antares.model.Galaxy;
import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.dto.GalaxyDTO;
import br.com.atlantz.antares.model.dto.TaskDTO;
import br.com.atlantz.antares.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/task")
public class TaskController
{
    @Autowired
    private ITaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO task)
    {
        try
        {
            Task taskCreated = taskService.save(new Task(task));

            return ResponseEntity.ok(new TaskDTO(taskCreated.getId().toString(), taskCreated.getTitle(), taskCreated.getDescription(), taskCreated.getStatus().getCode(), taskCreated.getEnd_date()));
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks()
    {
        try
        {
            List<TaskDTO> dtos = new ArrayList<>();
            for (Task task : taskService.getActiveTasks())
            {
                dtos.add(new TaskDTO(task.getId().toString(), task.getTitle(), task.getDescription(), task.getStatus().getCode(), task.getEnd_date()));
            }
            return ResponseEntity.ok(dtos);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable UUID id)
    {
        Task task = taskService.findById(id);
        return task != null ? ResponseEntity.ok(new TaskDTO(task.getId().toString(), task.getTitle(), task.getDescription(), task.getStatus().getCode(), task.getEnd_date()))
                : ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO taskDTO)
    {
        try
        {
            if (taskDTO.id() == null) return ResponseEntity.notFound().build();

            Task task = taskService.updateTask(taskDTO);
            if (task != null)
            {
                return ResponseEntity.ok(new TaskDTO(task.getId().toString(), task.getTitle(), task.getDescription(), task.getStatus().getCode(), task.getEnd_date()));
            }
            return ResponseEntity.notFound().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id)
    {
        Task task = taskService.findById(id);

        if (task == null) return ResponseEntity.notFound().build();

        taskService.softDelete(task);
        return ResponseEntity.noContent().build();
    }
}

