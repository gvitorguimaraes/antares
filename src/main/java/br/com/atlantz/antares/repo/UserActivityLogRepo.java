package br.com.atlantz.antares.repo;

import br.com.atlantz.antares.model.UserActivityLog;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserActivityLogRepo extends CrudRepository<UserActivityLog, UUID>
{
}
