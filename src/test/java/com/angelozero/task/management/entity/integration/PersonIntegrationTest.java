package com.angelozero.task.management.entity.integration;

import com.angelozero.task.management.adapter.controller.GRPCPersonController;
import com.angelozero.task.management.entity.Person;
import com.angelozero.task.management.entity.Task;
import com.angelozero.task.management.entity.integration.config.PersonIntegrationTestConfig;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
public class PersonIntegrationTest extends PersonIntegrationTestConfig {

    @Autowired
    private GRPCPersonController grpcPersonController;

    @Autowired
    private MockMvc mockMvc;

    @After
    public void after() {
        deletePersonData();
    }

    private static final String FIND_PERSON_BY_EMAIL_QUERY = """
            {
              "query": "query personByEmail($arg1: String!) { personByEmail(email: $arg1) { profileInfo taskList { id description completed } } }",
              "operationName": "personByEmail",
              "variables": { "arg1": "%s" }
            }
            """;

    @Test
    @DisplayName("Integration Test - Should find a person by id")
    public void shouldFindAPersonByEmail() throws Exception {
        var taskList = List.of(new Task(null, "description-1", true));
        var person = new Person(null, "name-1", "email-1", "profile-info-1", taskList);

        var savedPerson = savePerson(person);

        var response = mockMvc.perform(post("/graphql")
                        .content(String.format(FIND_PERSON_BY_EMAIL_QUERY, savedPerson.email()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertNotNull(response);
    }
}
