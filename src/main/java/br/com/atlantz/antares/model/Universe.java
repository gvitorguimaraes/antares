package br.com.atlantz.antares.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the entire universe of knowledge
 */
@Entity
@Table
public class Universe extends BaseEntity
{
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "universe", fetch = FetchType.LAZY)
    private List<Galaxy> galaxies;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<Galaxy> getGalaxies()
    {
        if (galaxies == null) galaxies = new ArrayList<>();
        return galaxies;
    }

    public void setGalaxies(List<Galaxy> galaxies)
    {
        this.galaxies = galaxies;
    }
}