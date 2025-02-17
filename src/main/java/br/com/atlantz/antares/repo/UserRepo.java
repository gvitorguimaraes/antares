package br.com.atlantz.antares.repo;

import br.com.atlantz.antares.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepo extends CrudRepository<User, UUID>
{
    // email = username
    public User findByEmail(String email);
}
