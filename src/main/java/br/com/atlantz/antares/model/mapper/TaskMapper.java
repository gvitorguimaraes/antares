package br.com.atlantz.antares.model.mapper;

import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.dto.TaskDTO;
import br.com.atlantz.antares.model.enums.TaskStatusEnum;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TaskMapper {

    public TaskDTO toDto(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskDTO(
                task.getId().toString(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().getCode(),
                task.getEndDate()
        );
    }

    public Task toEntity(TaskDTO dto) {
        if (dto == null) {
            return null;
        }

        Task task = new Task();

        if (dto.id() != null && !dto.id().isEmpty()) {
            try {
                task.setId(UUID.fromString(dto.id()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Task ID invalid!");
            }
        }

        task.setTitle(dto.title());
        task.setDescription(dto.description());

        if (dto.statusCode() != null) {
            task.setStatus(TaskStatusEnum.fromCode(dto.statusCode()));
        } else {
            task.setStatus(TaskStatusEnum.NEW);
        }

        task.setEndDate(dto.endDate());
        return task;
    }
}
