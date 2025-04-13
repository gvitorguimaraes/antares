package br.com.atlantz.antares.model.mapper;

import br.com.atlantz.antares.model.Note;
import br.com.atlantz.antares.model.dto.NoteDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NoteMapper
{
    public NoteDTO toDto(Note note) {
        if (note == null) {
            return null;
        }
        return new NoteDTO(
                note.getId().toString(),
                note.getTitle(),
                note.getDescription()
        );
    }

    public Note toEntity(NoteDTO dto) {
        if (dto == null) {
            return null;
        }

        Note note = new Note();

        if (dto.id() != null && !dto.id().isEmpty()) {
            try {
                note.setId(UUID.fromString(dto.id()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Note ID invalid!");
            }
        }

        note.setTitle(dto.title());
        note.setDescription(dto.description());

        return note;
    }
}
