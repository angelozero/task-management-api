package com.angelozero.task.management.entity.tasks;

public record SimpleTask(String id,
                         String description,
                         Boolean completed) {


    public TaskType getType() {
        return TaskType.SIMPLE;
    }
}
