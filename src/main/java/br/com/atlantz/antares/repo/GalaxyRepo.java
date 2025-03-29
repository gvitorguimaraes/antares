package br.com.atlantz.antares.repo;

import br.com.atlantz.antares.model.Galaxy;
import br.com.atlantz.antares.model.Universe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface GalaxyRepo extends CrudRepository<Galaxy, UUID>
{
    public List<Galaxy> findByUniverseAndExclusionIsNull(Universe universe);
}
