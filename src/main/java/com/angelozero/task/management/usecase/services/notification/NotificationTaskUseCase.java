package com.angelozero.task.management.usecase.services.notification;

public interface NotificationTaskUseCase {
    void execute(String recipient, String message);
    NotificationTaskType getType();
}
