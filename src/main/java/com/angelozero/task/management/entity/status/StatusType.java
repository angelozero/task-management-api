package com.angelozero.task.management.entity.status;

import com.angelozero.task.management.usecase.exception.StatusTypeException;
import lombok.Getter;

@Getter
public enum StatusType {
    CUSTOM(0, "Custom"),
    PENDING(1, "Pending"),
    IN_PROGRESS(2, "In progress"),
    COMPLETED(3, "Completed"),
    BLOCKED(4, "Blocked");

    private final int code;
    private final String description;

    StatusType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static StatusType fromCode(int code) {
        for (StatusType type : StatusType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new StatusTypeException("Invalid status type code: " + code);
    }
}
