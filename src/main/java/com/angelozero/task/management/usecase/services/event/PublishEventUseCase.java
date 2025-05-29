package com.angelozero.task.management.usecase.services.event;

import com.angelozero.task.management.entity.Event;
import com.angelozero.task.management.usecase.exception.BusinessException;
import com.angelozero.task.management.usecase.gateway.EventGateway;
import com.angelozero.task.management.usecase.gateway.PersonGateway;
import com.angelozero.task.management.usecase.gateway.PublishEventGateway;
import com.angelozero.task.management.usecase.gateway.TaskGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class PublishEventUseCase {

    private final PublishEventGateway publishEventGateway;
    private final EventGateway eventGateway;
    private final PersonGateway personGateway;
    private final TaskGateway taskGateway;

    public void execute(String taskId, String email, String eventType, String message) {
        log.info("Publishing an Event with message - : {}", message);

        var person = personGateway.findByEmail(email);
        if (person == null) {
            log.error("A person with email {} was not found to publish an event", email);
            throw new BusinessException("A person with email " + email + " was not found to publish an event");
        }

        var task = taskGateway.findById(taskId);
        if (task == null) {
            log.error("A task with id {} was not found to publish an event", taskId);
            throw new BusinessException("A task with id " + taskId + " was not found to publish an event");
        }

        var event = new Event(null, eventType, task.id(), person.id(), LocalDateTime.now(), false, message);

        var eventSaved = eventGateway.save(event);
        log.info("Event {} saved with success", eventSaved.id());

        publishEventGateway.publish(eventSaved);
        log.info("Event {} published with success", event.id());
    }
}
