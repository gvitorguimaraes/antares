package br.com.atlantz.antares.controller;

import br.com.atlantz.antares.model.Task;
import br.com.atlantz.antares.model.User;
import br.com.atlantz.antares.model.dto.TaskDTO;
import br.com.atlantz.antares.model.enums.TaskStatusEnum;
import br.com.atlantz.antares.repo.TaskRepo;
import br.com.atlantz.antares.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IUserService userService;

    @Autowired
    private TaskRepo taskRepository;

    private UUID taskId;
    private Task task;
    private TaskDTO taskDTO;
    private User user;

    @BeforeEach
    void setUp() {

        taskRepository.deleteAll();

        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Usuário de Teste");

        when(userService.recoveryUserFromTokenAuth()).thenReturn(user);

        task = new Task();
        task.setTitle("Tarefa de Integração");
        task.setDescription("Descrição da tarefa de integração");
        task.setStatus(TaskStatusEnum.NEW);
        task.setEndDate(LocalDateTime.now().plusDays(7));
        task.setUser(user);

        task = taskRepository.save(task);
        taskId = task.getId();


        taskDTO = new TaskDTO(
                null,
                "Nova Tarefa",
                "Descrição da nova tarefa",
                "N",
                LocalDateTime.now().plusDays(7)
        );
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void createTask_ShouldCreateAndReturnTask() throws Exception {
        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO))
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Nova Tarefa"))
                .andExpect(jsonPath("$.description").value("Descrição da nova tarefa"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void getAllTasks_ShouldReturnTaskList() throws Exception {
        mockMvc.perform(get("/task")
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Tarefa de Integração"))
                .andExpect(jsonPath("$[0].description").value("Descrição da tarefa de integração"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void getTaskById_ExistingTask_ShouldReturnTask() throws Exception {
        mockMvc.perform(get("/task/{id}", taskId)
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Tarefa de Integração"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void getTaskById_NonExistingTask_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/task/{id}", UUID.randomUUID())
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void updateTask_ExistingTask_ShouldUpdateAndReturnTask() throws Exception {
        TaskDTO updateDTO = new TaskDTO(
                taskId.toString(),
                "Tarefa Atualizada",
                "Descrição atualizada",
                "OP",
                LocalDateTime.now().plusDays(10)
        );

        mockMvc.perform(put("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO))
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Tarefa Atualizada"))
                .andExpect(jsonPath("$.description").value("Descrição atualizada"))
                .andExpect(jsonPath("$.statusCode").value(2));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void deleteTask_ExistingTask_ShouldSoftDeleteAndReturnNoContent() throws Exception {
        mockMvc.perform(delete("/task/{id}", taskId)
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());

        Optional<Task> deletedTask = taskRepository.findById(taskId);
        Assertions.assertTrue(deletedTask.isPresent());
        Assertions.assertTrue(deletedTask.get().isDeleted());
        Assertions.assertNotNull(deletedTask.get().getExclusion());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"A"})
    void createTask_InvalidData_ShouldReturnBadRequest() throws Exception {
        TaskDTO invalidDTO = new TaskDTO(
                null,
                "",
                "Descrição da tarefa",
                "N",
                LocalDateTime.now().plusDays(7)
        );

        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO))
                        .with(SecurityMockMvcRequestPostProcessors.user("testuser").roles("A"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }
}
