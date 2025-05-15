package com.angelozero.task.management.entity.status;

sealed public interface StatusTask permits Blocked, Completed, CustomStatusTask, InProgress, Pending {
    StatusType getType();
    String getName();
    int getCode();
}