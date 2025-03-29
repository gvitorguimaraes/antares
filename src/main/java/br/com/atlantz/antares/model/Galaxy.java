package br.com.atlantz.antares.model;

import br.com.atlantz.antares.model.dto.GalaxyDTO;
import jakarta.persistence.*;

@Entity
@Table
public class Galaxy extends BaseEntity
{
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_universe")
    private Universe universe;

    public Galaxy(){};

    public Galaxy (GalaxyDTO galaxyDTO)
    {
        this.name = galaxyDTO.name();
        this.description = galaxyDTO.description();
    }

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

    public Universe getUniverse()
    {
        return universe;
    }

    public void setUniverse(Universe universe)
    {
        this.universe = universe;
    }

}
