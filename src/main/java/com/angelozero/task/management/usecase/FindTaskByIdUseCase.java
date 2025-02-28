package com.angelozero.task.management.usecase;

import com.angelozero.task.management.entity.Task;
import com.angelozero.task.management.usecase.gateway.TaskGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FindTaskByIdUseCase {

    private final TaskGateway taskGateway;

    public Task execute(String id) {
        log.info("Getting a Task by id: {}", id);

        var task = taskGateway.findById(id);

        if (task == null) {
            log.info("No Task was found");
            return null;
        }

        log.info("Task was found with success");
        return task;
    }
}
