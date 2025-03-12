package com.angelozero.task.management.entity.integration;

import com.angelozero.task.management.adapter.controller.TaskController;
import com.angelozero.task.management.adapter.dataprovider.jpa.entity.TaskEntity;
import com.angelozero.task.management.entity.integration.config.BaseIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class TaskControllerBaseIntegrationTest extends BaseIntegrationTest {

    private static final String GET_PERSON_BY_ID_URL = "/task/{id}";

    @Autowired
    private TaskController taskController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void shouldFindATaskById() throws Exception {
        var taskEntitySaved = repository.save(new TaskEntity(null, "task-test", true));

        var result = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_PERSON_BY_ID_URL, taskEntitySaved.id())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result);
    }
}
