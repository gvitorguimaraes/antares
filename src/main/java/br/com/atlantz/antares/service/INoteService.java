package br.com.atlantz.antares.service;

import br.com.atlantz.antares.model.Note;
import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.dto.NoteDTO;
import br.com.atlantz.antares.model.dto.TaskDTO;

import java.util.List;
import java.util.UUID;

public interface INoteService
{
    Note save(Note task);
    List<Note> getActiveNotes();
    Note findById(UUID id);
    void softDelete(Note task);
    Note updateNote(NoteDTO taskDTO);
}
