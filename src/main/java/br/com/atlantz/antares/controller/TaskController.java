package br.com.atlantz.antares.controller;

import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.dto.TaskDTO;
import br.com.atlantz.antares.model.mapper.TaskMapper;
import br.com.atlantz.antares.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TaskController
{
    @Autowired
    private ITaskService taskService;
    private final TaskMapper taskMapper = new TaskMapper();

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        if (task.valuesAreValid()) {
            Task createdTask = taskService.save(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(taskMapper.toDto(createdTask));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<Task> tasks = taskService.getActiveTasks();
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable UUID id) {
        Task task =  taskService.findById(id).orElse(null);
        if (task != null){
            return ResponseEntity.ok(taskMapper.toDto(task));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO taskDTO) {
        if (taskDTO.id() == null || taskDTO.id().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Task taskUpdated = taskService.updateTask(taskDTO).orElse(null);
        if (taskUpdated != null) {
            return ResponseEntity.ok(taskMapper.toDto(taskUpdated));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        if (taskService.softDelete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

