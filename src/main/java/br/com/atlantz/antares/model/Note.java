package br.com.atlantz.antares.model;

import jakarta.persistence.*;

@Entity
@Table
public class Note extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="id_user", referencedColumnName = "id")
    private User user;

    @Column(length = 500)
    private String title;

    @Column(length = 10000)
    private String description;

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

}
