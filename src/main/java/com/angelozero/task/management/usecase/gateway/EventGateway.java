package com.angelozero.task.management.usecase.gateway;

import com.angelozero.task.management.entity.Event;

public interface EventGateway {

    Event save(Event event);
}
