package com.angelozero.task.management.usecase.services.notification;

import org.springframework.stereotype.Service;

@Service
public class SendSmsNotificationTaskUseCase implements NotificationTaskUseCase{
    @Override
    public void execute(String recipient, String message) {

    }

    @Override
    public NotificationTaskType getType() {
        return NotificationTaskType.SMS;
    }
}
