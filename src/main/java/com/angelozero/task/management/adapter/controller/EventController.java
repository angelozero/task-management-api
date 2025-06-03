package com.angelozero.task.management.adapter.controller;

import com.angelozero.task.management.adapter.controller.rest.request.EventRequest;
import com.angelozero.task.management.usecase.services.event.EventPublisherUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

    private final EventPublisherUseCase eventPublisherUseCase;

    @PostMapping
    public ResponseEntity<Void> saveEvent(@RequestBody EventRequest eventRequest) {
        var taskId = eventRequest.taskId();
        var email = eventRequest.email();
        var message = eventRequest.message();

        eventPublisherUseCase.execute(taskId, email, message);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //TODO get an event information and update read status
}
