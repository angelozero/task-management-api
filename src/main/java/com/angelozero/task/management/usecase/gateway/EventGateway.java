package com.angelozero.task.management.usecase.gateway;

import com.angelozero.task.management.entity.Event;

public interface EventGateway {

    Event getById(Integer id);
    Event save(Event event);
}
