package com.angelozero.task.management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;


public record Event(Integer id,
                    String eventType,
                    Integer entityId,
                    Integer userId,
                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
                    LocalDateTime localDateTime,
                    boolean read,
                    String message) {
}
