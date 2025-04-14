package br.com.atlantz.antares.controller;

import br.com.atlantz.antares.model.Note;
import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.dto.NoteDTO;
import br.com.atlantz.antares.model.mapper.NoteMapper;
import br.com.atlantz.antares.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/note")
public class NoteController
{
    @Autowired
    private INoteService noteService;
    private final NoteMapper noteMapper = new NoteMapper();

    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO noteDTO) {
        Note note = noteMapper.toEntity(noteDTO);
        if (note.valuesAreValid()) {
            Note createdNote = noteService.save(note);
            return ResponseEntity.status(HttpStatus.CREATED).body(noteMapper.toDto(createdNote));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        List<Note> notes = noteService.getActiveNotes();
        if (notes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notes.stream()
                .map(noteMapper::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable UUID id) {
        Note note =  noteService.findById(id).orElse(null);
        if (note != null){
            return ResponseEntity.ok(noteMapper.toDto(note));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<NoteDTO> updateNote(@RequestBody NoteDTO noteDTO) {
        if (noteDTO.id() == null || noteDTO.id().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Note noteUpdated = noteService.updateNote(noteDTO).orElse(null);
        if (noteUpdated != null) {
            return ResponseEntity.ok(noteMapper.toDto(noteUpdated));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id) {
        if (noteService.softDelete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

