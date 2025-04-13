package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.Note;
import br.com.atlantz.antares.model.User;
import br.com.atlantz.antares.model.dto.NoteDTO;
import br.com.atlantz.antares.repo.NoteRepo;
import br.com.atlantz.antares.util.error.ServiceInternException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService implements INoteService {

    @Autowired
    private NoteRepo repo;

    @Autowired
    private IUserService userService;

    @Override
    public Note save(Note note) {
        User user = userService.recoveryUserFromTokenAuth();
        if (user == null) {
            throw new ServiceInternException(TaskService.class, "Authorization error, user ID not valid.");
        }
        note.setUser(user);
        return repo.save(note);
    }

    @Override
    public List<Note> getActiveNotes() {
        try {
            User user = userService.recoveryUserFromTokenAuth();
            if (user == null) {
                return Collections.emptyList();
            }
            return repo.findAllByUserAndExclusionIsNull(user);
        } catch (Exception e) {
            throw new ServiceInternException(NoteService.class, "Error in getActiveNotes()", e);
        }
    }

    @Override
    public Optional<Note> findById(UUID id) {
        return repo.findByIdAndExclusionIsNull(id);
    }

    @Override
    public boolean softDelete(UUID id) {
        Note note = findById(id).orElse(null);
        if (note != null) {
            note.setExclusion(LocalDateTime.now());
            repo.save(note);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Note> updateNote(NoteDTO noteDTO) {
        Note note = findById(UUID.fromString(noteDTO.id())).orElse(null);
        if (note != null){
            note.setTitle(noteDTO.title());
            note.setDescription(noteDTO.description());
            return Optional.of(repo.save(note));
        }
        return Optional.empty();
    }
}
