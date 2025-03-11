package com.angelozero.task.management.usecase;

import com.angelozero.task.management.entity.Task;
import com.angelozero.task.management.usecase.gateway.TaskGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UpdateTaskUseCase {

    private final TaskGateway taskGateway;

    public Task execute(String id, Task task) {
        if (id == null) {
            log.info("No Task was informed to updated");
            return null;
        }

        log.info("Updating the Task - ID: {}, with values {}", id, task);

        var taskFounded = taskGateway.findById(id);

        if (taskFounded == null) {
            log.info("No Task was found");
            return null;
        }

        var taskToUpdate = updateTaskValues(id, task, taskFounded);

        var taskUpdated = taskGateway.update(taskToUpdate);

        log.info("Task was updated with success");
        return taskUpdated;
    }

    private Task updateTaskValues(String id, Task task, Task taskFounded) {
        var description = StringUtils.isBlank(task.description()) ? taskFounded.description() : task.description();
        var completed = task.completed() != null ? task.completed() : taskFounded.completed();

        return new Task(id, description, completed);
    }
}
