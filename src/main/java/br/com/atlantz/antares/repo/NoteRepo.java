package br.com.atlantz.antares.repo;

import br.com.atlantz.antares.model.Galaxy;
import br.com.atlantz.antares.model.Note;
import br.com.atlantz.antares.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepo extends CrudRepository<Note, UUID>
{
    public List<Note> findByGalaxyAndExclusionIsNull(Galaxy galaxy);
}
