package br.com.atlantz.antares.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.UUID;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @CreationTimestamp
    @Column(name = "inclusion", nullable = false, updatable = false)
    private LocalDateTime inclusion;

    @Column(name = "exclusion")
    private LocalDateTime exclusion;

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public LocalDateTime getInclusion()
    {
        return inclusion;
    }

    public void setInclusion(LocalDateTime inclusion)
    {
        this.inclusion = inclusion;
    }

    public LocalDateTime getExclusion()
    {
        return exclusion;
    }

    public void setExclusion(LocalDateTime exclusion)
    {
        this.exclusion = exclusion;
    }

    public boolean isDeleted()
    {
        return exclusion != null;
    }

    public String getDateFormated(LocalDateTime date)
    {
        // TODO - implement format
        return null;
    }
}
