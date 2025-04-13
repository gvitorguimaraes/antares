package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.Note;
import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.dto.NoteDTO;
import br.com.atlantz.antares.model.dto.TaskDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface INoteService
{
    Note save(Note note);
    List<Note> getActiveNotes();
    Optional<Note> findById(UUID id);
    boolean softDelete(UUID id);
    Optional<Note> updateNote(NoteDTO noteDTO);
}
