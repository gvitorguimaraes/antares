package br.com.atlantz.antares.controller;

import br.com.atlantz.antares.model.Note;
import br.com.atlantz.antares.model.dto.NoteDTO;
import br.com.atlantz.antares.model.mapper.NoteMapper;
import br.com.atlantz.antares.service.INoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
@ActiveProfiles("test")
public class NoteControllerTest
{

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    INoteService noteService;

    @MockitoBean
    NoteMapper noteMapper;

    @Autowired
    ObjectMapper objectMapper;

    private NoteDTO noteDTO;
    private Note note;
    private UUID noteId;

    @BeforeEach
    void setUp() {
        noteId = UUID.randomUUID();

        noteDTO = new NoteDTO(
                noteId.toString(),
                "Teste de nota",
                "Descrição da nota"
        );

        note = new Note();
        note.setId(noteId);
        note.setTitle("Teste de nota");
        note.setDescription("Descrição da nota");

        when(noteMapper.toEntity(any(NoteDTO.class))).thenReturn(note);
        when(noteMapper.toDto(any(Note.class))).thenReturn(noteDTO);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void createNote_ShouldCreateAndReturnNote() throws Exception {
        when(noteService.save(any(Note.class))).thenReturn(note);

        noteDTO = new NoteDTO("",
                "Teste de nota",
                "Descrição da nota"
        );

        mockMvc.perform(post("/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDTO))
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Teste de nota"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void getAllNotes_WithNotes_ShouldReturnNoteList() throws Exception {
        when(noteService.getActiveNotes()).thenReturn(Arrays.asList(note));

        mockMvc.perform(get("/note")
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(noteId.toString()))
                .andExpect(jsonPath("$[0].title").value("Teste de nota"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void getAllNotes_WithNoNotes_ShouldReturnNoContent() throws Exception {
        when(noteService.getActiveNotes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/note")
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void getNoteById_ExistingNote_ShouldReturnNote() throws Exception {
        when(noteService.findById(eq(noteId))).thenReturn(Optional.of(note));

        mockMvc.perform(get("/note/{id}", noteId)
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(noteId.toString()))
                .andExpect(jsonPath("$.title").value("Teste de nota"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void getNoteById_NonExistingNote_ShouldReturnNotFound() throws Exception {
        when(noteService.findById(any(UUID.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/note/{id}", UUID.randomUUID())
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void updateNote_ExistingNote_ShouldUpdateAndReturnNote() throws Exception {
        when(noteService.updateNote(any(NoteDTO.class))).thenReturn(Optional.of(note));

        mockMvc.perform(put("/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDTO))
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(noteId.toString()))
                .andExpect(jsonPath("$.title").value("Teste de nota"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void updateNote_NonExistingNote_ShouldReturnNotFound() throws Exception {
        when(noteService.updateNote(any(NoteDTO.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDTO))
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void deleteNote_ExistingNote_ShouldReturnNoContent() throws Exception {
        when(noteService.softDelete(eq(noteId))).thenReturn(true);

        mockMvc.perform(delete("/note/{id}", noteId)
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void deleteNote_NonExistingNote_ShouldReturnNotFound() throws Exception {
        when(noteService.softDelete(any(UUID.class))).thenReturn(false);

        mockMvc.perform(delete("/note/{id}", UUID.randomUUID())
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void createNote_InvalidData_ShouldReturnBadRequest() throws Exception {
        NoteDTO invalidNoteDTO = new NoteDTO(
                null,
                "", // Título vazio viola a validação @NotBlank
                "Descrição da nota"
        );

        mockMvc.perform(post("/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidNoteDTO))
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }
}
