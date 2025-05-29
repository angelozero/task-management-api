package com.angelozero.task.management.usecase.gateway;

import com.angelozero.task.management.entity.Event;

public interface PublishEventGateway {

    void publish(Event event);
}
