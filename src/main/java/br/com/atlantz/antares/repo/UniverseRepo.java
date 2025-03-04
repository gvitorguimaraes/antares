package br.com.atlantz.antares.repo;

import br.com.atlantz.antares.model.Universe;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UniverseRepo extends CrudRepository<Universe, UUID>
{
}
