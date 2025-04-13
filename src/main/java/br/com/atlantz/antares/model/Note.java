package br.com.atlantz.antares.model;

import br.com.atlantz.antares.model.dto.NoteDTO;
import jakarta.persistence.*;

@Entity
@Table
public class Note extends BaseEntity
{
    @Column(length = 500)
    private String title;

    @Column(length = 10000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_galaxy")
    private Galaxy galaxy;

    public Note() {}

    public Note(NoteDTO dto)
    {
        this.title = dto.title();
        this.description = dto.description();
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

    public Galaxy getGalaxy()
    {
        return galaxy;
    }

    public void setGalaxy(Galaxy galaxy)
    {
        this.galaxy = galaxy;
    }
}
