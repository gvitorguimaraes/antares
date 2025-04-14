package br.com.atlantz.antares.controller;

import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.dto.TaskDTO;
import br.com.atlantz.antares.model.enums.TaskStatusEnum;
import br.com.atlantz.antares.model.mapper.TaskMapper;
import br.com.atlantz.antares.service.ITaskService;
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

@WebMvcTest(TaskController.class)
@ActiveProfiles("test")
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ITaskService taskService;

    @MockitoBean
    TaskMapper taskMapper;

    @Autowired
    ObjectMapper objectMapper;

    private TaskDTO taskDTO;
    private Task task;
    private UUID taskId;

    @BeforeEach
    void setUp() {
        taskId = UUID.randomUUID();

        taskDTO = new TaskDTO(
                taskId.toString(),
                "Teste de Tarefa",
                "Descrição da tarefa de teste",
                "N",
                LocalDateTime.now().plusDays(7)
        );

        task = new Task();
        task.setId(taskId);
        task.setTitle("Teste de Tarefa");
        task.setDescription("Descrição da tarefa de teste");
        task.setStatus(TaskStatusEnum.NEW);
        task.setEndDate(LocalDateTime.now().plusDays(7));

        when(taskMapper.toEntity(any(TaskDTO.class))).thenReturn(task);
        when(taskMapper.toDto(any(Task.class))).thenReturn(taskDTO);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void createTask_ShouldCreateAndReturnTask() throws Exception {
        when(taskService.save(any(Task.class))).thenReturn(task);

        taskDTO = new TaskDTO("",
                "Teste de Tarefa",
                "Descrição da tarefa de teste",
                "N",
                LocalDateTime.now().plusDays(7)
        );

        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO))
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Teste de Tarefa"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void getAllTasks_WithTasks_ShouldReturnTaskList() throws Exception {
        when(taskService.getActiveTasks()).thenReturn(Arrays.asList(task));

        mockMvc.perform(get("/task")
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(taskId.toString()))
                .andExpect(jsonPath("$[0].title").value("Teste de Tarefa"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void getAllTasks_WithNoTasks_ShouldReturnNoContent() throws Exception {
        when(taskService.getActiveTasks()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/task")
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void getTaskById_ExistingTask_ShouldReturnTask() throws Exception {
        when(taskService.findById(eq(taskId))).thenReturn(Optional.of(task));

        mockMvc.perform(get("/task/{id}", taskId)
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Teste de Tarefa"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void getTaskById_NonExistingTask_ShouldReturnNotFound() throws Exception {
        when(taskService.findById(any(UUID.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/task/{id}", UUID.randomUUID())
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void updateTask_ExistingTask_ShouldUpdateAndReturnTask() throws Exception {
        when(taskService.updateTask(any(TaskDTO.class))).thenReturn(Optional.of(task));

        mockMvc.perform(put("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO))
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Teste de Tarefa"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void updateTask_NonExistingTask_ShouldReturnNotFound() throws Exception {
        when(taskService.updateTask(any(TaskDTO.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO))
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void deleteTask_ExistingTask_ShouldReturnNoContent() throws Exception {
        when(taskService.softDelete(eq(taskId))).thenReturn(true);

        mockMvc.perform(delete("/task/{id}", taskId)
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void deleteTask_NonExistingTask_ShouldReturnNotFound() throws Exception {
        when(taskService.softDelete(any(UUID.class))).thenReturn(false);

        mockMvc.perform(delete("/task/{id}", UUID.randomUUID())
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void createTask_InvalidData_ShouldReturnBadRequest() throws Exception {
        TaskDTO invalidTaskDTO = new TaskDTO(
                null,
                "", // Título vazio viola a validação @NotBlank
                "Descrição da tarefa",
                "N",
                LocalDateTime.now().plusDays(7)
        );

        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTaskDTO))
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }
}
