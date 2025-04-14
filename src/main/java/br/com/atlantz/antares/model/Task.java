package br.com.atlantz.antares.model;

import br.com.atlantz.antares.model.enums.TaskStatusEnum;
import br.com.atlantz.antares.model.enums.TaskStatusEnumConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Task extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="id_user", referencedColumnName = "id")
    private User user;

    @Column(length = 500)
    private String title;

    @Column(length = 10000)
    private String description;

    @Convert(converter = TaskStatusEnumConverter.class)
    @Column(nullable = false)
    private TaskStatusEnum status;

    @Column
    private LocalDateTime endDate;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
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

    public LocalDateTime getEndDate()
    {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate)
    {
        this.endDate = endDate;
    }

    public boolean valuesAreValid(){
        return !this.title.isBlank()
                && this.status != null;
    }
}
