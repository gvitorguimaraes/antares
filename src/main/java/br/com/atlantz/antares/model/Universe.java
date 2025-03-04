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
    @OneToOne
    @JoinColumn(name="id_user", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "universe", fetch = FetchType.LAZY)
    private List<Galaxy> galaxies;


    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
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