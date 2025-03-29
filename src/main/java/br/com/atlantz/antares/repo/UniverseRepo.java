package br.com.atlantz.antares.repo;

import br.com.atlantz.antares.model.Universe;
import br.com.atlantz.antares.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UniverseRepo extends CrudRepository<Universe, UUID>
{
    public Universe findByUser(User user);
}
