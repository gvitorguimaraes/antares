package br.com.atlantz.antares.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class UserActivityLog extends BaseEntity
{
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column
    private Boolean showInTimeline;

    @Column(name="date_activity")
    private LocalDateTime date;

    @Column(length = 2000)
    private String description;

    @Column(name="time_reg")
    private Double time;

    @ManyToOne
    @JoinColumn(name = "id_task")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "id_note")
    private Note note;

    public UserActivityLog(){}

    public UserActivityLog(User user, BaseEntity obj)
    {
        this.user = user;
        this.date = LocalDateTime.now();

        if (obj instanceof Task)
        {
            this.task = (Task) obj;
        }
        else if (obj instanceof Note)
        {
            this.note = (Note) obj;
        }
    }

    public UserActivityLog(User user, BaseEntity obj, Double time)
    {
        this.user = user;
        this.date = LocalDateTime.now();
        this.time = time;

        if (obj instanceof Task)
        {
            this.task = (Task) obj;
        }
        else if (obj instanceof Note)
        {
            this.note = (Note) obj;
        }
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Boolean getShowInTimeline()
    {
        return showInTimeline;
    }

    public UserActivityLog setShowInTimeline(Boolean showInTimeline)
    {
        this.showInTimeline = showInTimeline;
        return this;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Double getTime()
    {
        return time;
    }

    public void setTime(Double time)
    {
        this.time = time;
    }

    public Task getTask()
    {
        return task;
    }

    public void setTask(Task task)
    {
        this.task = task;
    }

    public Note getNote()
    {
        return note;
    }

    public void setNote(Note note)
    {
        this.note = note;
    }
}
