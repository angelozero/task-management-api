package com.angelozero.task.management.entity.integration.config;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.TaskEntity;
import com.angelozero.task.management.adapter.dataprovider.jpa.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class TaskIntegrationTestConfig extends BaseIntegrationTestConfig {

    @Autowired
    protected TaskRepository repository;

    public TaskEntity findTaskById(String id) {
        return repository.findById(id).orElse(null);
    }

    public TaskEntity saveTask(String description, Boolean isCompleted) {
        return repository.save(new TaskEntity(null, description, isCompleted));
    }

    public void deleteTaskData() {
        repository.deleteAll();
        log.info("[TaskIntegrationTestConfig] - Task data deleted with success");
    }
}
