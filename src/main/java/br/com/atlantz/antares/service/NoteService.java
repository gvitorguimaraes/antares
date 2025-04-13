package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.Galaxy;
import br.com.atlantz.antares.model.Note;
import br.com.atlantz.antares.model.User;
import br.com.atlantz.antares.model.dto.NoteDTO;
import br.com.atlantz.antares.repo.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NoteService implements INoteService
{
    @Autowired
    private NoteRepo repo;

    @Autowired
    private IGalaxyService galaxyService;

    @Autowired
    private IUserService userService;

    @Override
    public Note save(Note note)
    {
        // Can be removed in future versions, the correct galaxy will be provided by user
        Galaxy galaxy = galaxyService.getActiveGalaxies().getFirst();
        note.setGalaxy(galaxy);

        return repo.save(note);
    }

    @Override
    public List<Note> getActiveNotes()
    {
        try
        {
            User user =  userService.recoveryUserFromTokenAuth();
            if (user != null && user.getUniverse() != null) {
                return repo.findByGalaxyAndExclusionIsNull(user.getUniverse().getGalaxies().getFirst());
            }
            return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Note findById(UUID id)
    {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void softDelete(Note note)
    {
        if (note != null
                && note.getId() != null
                && !note.isDeleted())
        {
            note.setExclusion(LocalDateTime.now());
            repo.save(note);
        }
    }

    @Override
    public Note updateNote(NoteDTO noteDTO)
    {
        Note note = findById(UUID.fromString(noteDTO.id()));

        if (note != null)
        {
            note.setTitle(noteDTO.title());
            note.setDescription(noteDTO.description());
            return repo.save(note);
        }
        return null;
    }
}
