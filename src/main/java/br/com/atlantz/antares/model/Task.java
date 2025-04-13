package br.com.atlantz.antares.model;

import br.com.atlantz.antares.model.dto.TaskDTO;
import br.com.atlantz.antares.model.enums.TaskStatusEnum;
import br.com.atlantz.antares.model.enums.TaskStatusEnumConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Task extends BaseEntity
{
    @Column(length = 500)
    private String title;

    @Column(length = 10000)
    private String description;

    @Convert(converter = TaskStatusEnumConverter.class)
    @Column(nullable = false)
    private TaskStatusEnum status;

    @Column
    private LocalDateTime end_date;

    @ManyToOne
    @JoinColumn(name = "id_galaxy")
    private Galaxy galaxy;

    public Task(){}

    public Task(TaskDTO dto)
    {
        this.setTitle(dto.title());
        this.setDescription(dto.description());
        this.setStatus(TaskStatusEnum.fromCode(dto.statusCode()));
        this.setEnd_date(dto.endDate());
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public TaskStatusEnum getStatus()
    {
        return status;
    }

    public void setStatus(TaskStatusEnum status)
    {
        this.status = status;
    }

    public LocalDateTime getEnd_date()
    {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date)
    {
        this.end_date = end_date;
    }

    public Galaxy getGalaxy()
    {
        return galaxy;
    }

    public void setGalaxy(Galaxy galaxy)
    {
        this.galaxy = galaxy;
    }
}
