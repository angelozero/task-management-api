package com.angelozero.task.management.entity;

import com.angelozero.task.management.usecase.exception.TaskException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskTest {

    @Test
    @DisplayName("Validating a Task creation without description")
    void validatingATaskCreationWithoutDescription() {

        var exception = assertThrows(TaskException.class, () -> new Task("123", "", false));

        assertEquals("A Task must have a \"description\" value", exception.getMessage());
    }
}
