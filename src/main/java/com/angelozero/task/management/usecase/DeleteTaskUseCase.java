package com.angelozero.task.management.usecase;

import com.angelozero.task.management.usecase.gateway.TaskGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DeleteTaskUseCase {

    private final TaskGateway taskGateway;
    private final FindTaskByIdUseCase findTaskByIdUseCase;

    public void execute(String id) {
        log.info("Deleting the Task - ID: {}", id);

        var taskFound = findTaskByIdUseCase.execute(id);

        if (taskFound == null) {
            log.info("No Task was found to delete");
        }

        taskGateway.delete(taskFound);

        log.info("Task deleted with success");
    }
}
