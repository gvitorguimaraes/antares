package br.com.atlantz.antares.repo;

import br.com.atlantz.antares.model.Note;
import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepo extends CrudRepository<Note, UUID>
{
    public List<Note> findAllByUserAndExclusionIsNull(User user);
    public Optional<Note> findByIdAndExclusionIsNull(UUID id);
}
