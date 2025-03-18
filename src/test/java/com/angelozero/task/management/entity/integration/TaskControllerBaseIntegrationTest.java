package com.angelozero.task.management.entity.integration;

import com.angelozero.task.management.adapter.controller.TaskController;
import com.angelozero.task.management.adapter.controller.rest.request.TaskRequest;
import com.angelozero.task.management.adapter.controller.rest.response.PagedResponse;
import com.angelozero.task.management.adapter.controller.rest.response.TaskResponse;
import com.angelozero.task.management.entity.integration.config.BaseIntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class TaskControllerBaseIntegrationTest extends BaseIntegrationTest {

    private static final String GET_TASK_BY_ID_URL = "/task/{id}";
    private static final String GET_TASK_PAGED_URL = "/task";
    private static final String PUT_TASK_UPDATE_BY_ID_URL = "/task/{id}";
    @Autowired
    private TaskController taskController;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @After
    public void after() {
        deleteTaskData();
    }

    @Test
    @DisplayName("Integration Test - Should find a task by id")
    public void shouldFindATaskById() throws Exception {
        var taskEntitySaved = saveTask("task-test", true);

        var jsonResponse = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_TASK_BY_ID_URL, taskEntitySaved.id())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var taskResponse = objectMapper.readValue(jsonResponse, TaskResponse.class);

        assertNotNull(taskResponse);
        assertEquals("task-test", taskResponse.description());
    }

    @Test
    @DisplayName("Integration Test - Should find paged tasks without parameters")
    public void shouldFindPagedTasksWithoutParams() throws Exception {
        saveTask("task-test-1", true);
        saveTask("task-test-2", true);
        saveTask("task-test-3", true);

        var jsonResponse = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_TASK_PAGED_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PagedResponse<TaskResponse> taskPagedResponse = objectMapper.readValue(jsonResponse,
                objectMapper.getTypeFactory().constructParametricType(PagedResponse.class, TaskResponse.class));

        assertNotNull(taskPagedResponse);
        assertFalse(taskPagedResponse.contents().isEmpty());
        assertEquals(0, taskPagedResponse.pageNumber());
        assertEquals(3, taskPagedResponse.contents().size());
        assertEquals(1, taskPagedResponse.totalPages());
        assertEquals(3, taskPagedResponse.totalElements());
        assertEquals(10, taskPagedResponse.size());
        assertNull(taskPagedResponse.content());
        assertEquals("task-test-1", taskPagedResponse.contents().getFirst().description());
        assertEquals("task-test-2", taskPagedResponse.contents().get(1).description());
        assertEquals("task-test-3", taskPagedResponse.contents().get(2).description());
        assertFalse(taskPagedResponse.hasNext());
        assertFalse(taskPagedResponse.hasPrevious());
    }

    @Test
    @DisplayName("Integration Test - Should find paged tasks with parameters")
    public void shouldFindPagedTasksWithParams() throws Exception {
        saveTask("task-test-1", true);
        saveTask("task-test-2", true);
        saveTask("task-test-3", false);

        var firstJsonResponse = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_TASK_PAGED_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("page", "0")
                        .param("size", "1")
                        .param("sortField", "description")
                        .param("isCompleted", "true"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var secondJsonResponse = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_TASK_PAGED_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("page", "1")
                        .param("size", "1")
                        .param("sortField", "description")
                        .param("isCompleted", "true"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PagedResponse<TaskResponse> firstTaskPagedResponse = objectMapper.readValue(firstJsonResponse,
                objectMapper.getTypeFactory().constructParametricType(PagedResponse.class, TaskResponse.class));

        PagedResponse<TaskResponse> secondTaskPagedResponse = objectMapper.readValue(secondJsonResponse,
                objectMapper.getTypeFactory().constructParametricType(PagedResponse.class, TaskResponse.class));

        // Response from first call...
        assertNotNull(firstTaskPagedResponse);
        assertFalse(firstTaskPagedResponse.contents().isEmpty());
        assertEquals(0, firstTaskPagedResponse.pageNumber());
        assertEquals(1, firstTaskPagedResponse.contents().size());
        assertEquals(2, firstTaskPagedResponse.totalPages());
        assertEquals(2, firstTaskPagedResponse.totalElements());
        assertEquals(1, firstTaskPagedResponse.size());
        assertNull(firstTaskPagedResponse.content());
        assertEquals("task-test-1", firstTaskPagedResponse.contents().getFirst().description());
        assertTrue(firstTaskPagedResponse.hasNext());
        assertFalse(firstTaskPagedResponse.hasPrevious());

        // Response from next call...
        assertNotNull(secondTaskPagedResponse);
        assertFalse(secondTaskPagedResponse.contents().isEmpty());
        assertEquals(1, secondTaskPagedResponse.pageNumber());
        assertEquals(1, secondTaskPagedResponse.contents().size());
        assertEquals(2, secondTaskPagedResponse.totalPages());
        assertEquals(2, secondTaskPagedResponse.totalElements());
        assertEquals(1, secondTaskPagedResponse.size());
        assertNull(secondTaskPagedResponse.content());
        assertEquals("task-test-2", secondTaskPagedResponse.contents().getFirst().description());
        assertFalse(secondTaskPagedResponse.hasNext());
        assertTrue(secondTaskPagedResponse.hasPrevious());
    }

    @Test
    @DisplayName("Integration Test - Should update a task by id")
    public void shouldUpdateATaskById() throws Exception {
        var taskEntitySaved = saveTask("task-test", true);
        var taskRequest = new ObjectMapper().writeValueAsString(new TaskRequest("task-test-updated", false));

        var jsonResponse = this.mockMvc.perform(MockMvcRequestBuilders
                        .put(PUT_TASK_UPDATE_BY_ID_URL, taskEntitySaved.id())
                        .content(taskRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var taskResponse = objectMapper.readValue(jsonResponse, TaskResponse.class);

        assertNotNull(taskResponse);
        assertEquals("task-test-updated", taskResponse.description());
        assertFalse(taskResponse.completed());
    }

    @Test
    @DisplayName("Integration Test - Should delete a task by id")
    public void shouldDeleteATaskById() throws Exception {
        var taskEntitySaved = saveTask("task-test", true);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete(PUT_TASK_UPDATE_BY_ID_URL, taskEntitySaved.id())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());

        var taskNotFound = findTaskById(taskEntitySaved.id());

        assertNull(taskNotFound);
    }
}
