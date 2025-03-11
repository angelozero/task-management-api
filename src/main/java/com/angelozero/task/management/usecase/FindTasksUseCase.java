package com.angelozero.task.management.usecase;

import com.angelozero.task.management.entity.Task;
import com.angelozero.task.management.usecase.gateway.TaskGateway;
import com.angelozero.task.management.usecase.util.FieldValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FindTasksUseCase {

    private final TaskGateway taskGateway;

    public Page<Task> execute(int page, int size, String sortField) {
        log.info("Getting pageable Tasks");

        var validField = StringUtils.isBlank(sortField) ? null : FieldValidator.validateFieldName(Task.class, sortField);

        var tasks = taskGateway.getAll(page, size, validField);

        log.info("Tasks returned with success");
        return tasks;
    }
}
