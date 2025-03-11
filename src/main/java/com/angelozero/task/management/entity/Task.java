package com.angelozero.task.management.entity;

import com.angelozero.task.management.usecase.exception.TaskException;
import org.apache.commons.lang3.StringUtils;

public record Task(String id,
                   String description,
                   Boolean completed) {

    public Task {
        if (StringUtils.isBlank(description)) {
            throw new TaskException("A Task must have a 'description' value");
        }
    }
}
