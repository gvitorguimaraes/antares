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

    @Column
    private LocalDateTime date;

    @Column(length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_galaxy")
    private Galaxy galaxy;

    @ManyToOne
    @JoinColumn(name = "id_starCluster")
    private StarCluster starCluster;

    @ManyToOne
    @JoinColumn(name = "id_star")
    private Star star;

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

        if (obj instanceof Galaxy)
        {
            this.galaxy = (Galaxy) obj;
        }
        else if (obj instanceof StarCluster)
        {
            this.starCluster = (StarCluster) obj;
        }
        else if (obj instanceof Star)
        {
            this.star = (Star) obj;
        }
        else if (obj instanceof Task)
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

    public Galaxy getGalaxy()
    {
        return galaxy;
    }

    public void setGalaxy(Galaxy galaxy)
    {
        this.galaxy = galaxy;
    }

    public StarCluster getStarCluster()
    {
        return starCluster;
    }

    public void setStarCluster(StarCluster starCluster)
    {
        this.starCluster = starCluster;
    }

    public Star getStar()
    {
        return star;
    }

    public void setStar(Star star)
    {
        this.star = star;
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
