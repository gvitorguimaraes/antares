package br.com.atlantz.antares.repo;

import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface TaskRepo extends CrudRepository<Task, UUID>
{
    public List<Task> findAllByUserAndExclusionIsNull(User user);
    public Optional<Task> findByIdAndExclusionIsNull(UUID id);
}
