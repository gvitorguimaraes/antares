package br.com.atlantz.antares.repo;

import br.com.atlantz.antares.model.Galaxy;
import br.com.atlantz.antares.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepo extends CrudRepository<Task, UUID>
{
    public List<Task> findByGalaxyAndExclusionIsNull(Galaxy galaxy);
}
