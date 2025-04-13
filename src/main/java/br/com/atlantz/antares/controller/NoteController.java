package br.com.atlantz.antares.controller;

import br.com.atlantz.antares.model.Note;
import br.com.atlantz.antares.model.dto.NoteDTO;
import br.com.atlantz.antares.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/note")
public class NoteController
{
    @Autowired
    private INoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO note)
    {
        try
        {
            Note noteCreated = noteService.save(new Note(note));

            return ResponseEntity.ok(new NoteDTO(noteCreated.getId().toString(), noteCreated.getTitle(), noteCreated.getDescription()));
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes()
    {
        try
        {
            List<NoteDTO> dtos = new ArrayList<>();
            for (Note note : noteService.getActiveNotes())
            {
                dtos.add(new NoteDTO(note.getId().toString(), note.getTitle(), note.getDescription()));
            }
            return ResponseEntity.ok(dtos);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable UUID id)
    {
        Note note = noteService.findById(id);
        return note != null ? ResponseEntity.ok(new NoteDTO(note.getId().toString(), note.getTitle(), note.getDescription()))
                : ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<NoteDTO> updateNote(@RequestBody NoteDTO noteDTO)
    {
        try
        {
            if (noteDTO.id() == null) return ResponseEntity.notFound().build();

            Note note = noteService.updateNote(noteDTO);
            if (note != null)
            {
                return ResponseEntity.ok(new NoteDTO(note.getId().toString(), note.getTitle(), note.getDescription()));
            }
            return ResponseEntity.notFound().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id)
    {
        Note note = noteService.findById(id);

        if (note == null) return ResponseEntity.notFound().build();

        noteService.softDelete(note);
        return ResponseEntity.noContent().build();
    }
}

