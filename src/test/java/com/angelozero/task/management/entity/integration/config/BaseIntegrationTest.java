package com.angelozero.task.management.entity.integration.config;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.TaskEntity;
import com.angelozero.task.management.adapter.dataprovider.jpa.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = BaseIntegrationTest.Initializer.class)
public class BaseIntegrationTest {

    @Autowired
    protected TaskRepository repository;

    /**
     * Testcontainer - MongoDB
     */
    @ClassRule
    public static MongoDBContainer container = new MongoDBContainer("mongo:latest")
            .withExposedPorts(27017);


    /**
     * Configuration initializer - .properties file
     */
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.data.mongodb.uri=" + container.getConnectionString() + "/task-management-data-base-test",
                    "spring.data.mongodb.host=" + container.getHost(),
                    "spring.data.mongodb.port=" + container.getFirstMappedPort()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    public TaskEntity findTaskById(String id) {
        return repository.findById(id).orElse(null);
    }

    public TaskEntity saveTask(String description, Boolean isCompleted) {
        return repository.save(new TaskEntity(null, description, isCompleted));
    }

    public void deleteTaskData() {
        System.out.println("\nDeleting all Task Data");
        log.info("[BASE_INTEGRATION_TEST] - Deleting all Task Data");
        repository.deleteAll();
        log.info("[BASE_INTEGRATION_TEST] - Task data deleted with success");
    }
}
