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
public class FindTasksUseCase {

    private final TaskGateway taskGateway;

    public List<Task> execute() {
        log.info("Getting all Tasks");

        var tasks = taskGateway.getAll();

        log.info("Tasks returned with success");
        return tasks;
    }
}
