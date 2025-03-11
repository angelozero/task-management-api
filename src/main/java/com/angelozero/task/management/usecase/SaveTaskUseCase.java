package com.angelozero.task.management.usecase;

import com.angelozero.task.management.entity.Task;
import com.angelozero.task.management.usecase.gateway.TaskGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SaveTaskUseCase {

    private final TaskGateway taskGateway;

    public void execute(Task task) {
        log.info("Saving a Task");

        taskGateway.save(task);

        log.info("Task saved with success");
    }
}
