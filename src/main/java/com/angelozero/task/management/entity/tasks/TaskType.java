package com.angelozero.task.management.entity.tasks;

import com.angelozero.task.management.usecase.exception.TaskException;
import lombok.Getter;

@Getter
public enum TaskType {

    SIMPLE(1, "Simple Task"),
    REMINDER(2, "Task with Reminder"),
    HIGH_PRIORITY(3, "High Priority Task");

    private final int code;
    private final String description;

    TaskType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static TaskType fromCode(int code) {
        for (TaskType type : TaskType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new TaskException("Invalid task code priority: " + code);
    }
}