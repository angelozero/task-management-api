package com.angelozero.task.management.adapter.dataprovider;

import com.angelozero.task.management.adapter.config.RabbitMQConfig;
import com.angelozero.task.management.entity.Event;
import com.angelozero.task.management.usecase.exception.EventConsumerException;
import com.angelozero.task.management.usecase.exception.EventPublisherException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EventConsumerByRabbitMQDataProvider {

    private final RabbitMQConfig rabbitMQConfig;
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumer(String messageJson) {
        System.out.println("[Consumer] Received JSON: " + messageJson);
        try {
            Event receivedEvent = objectMapper.readValue(messageJson, Event.class);
            System.out.println("[Consumer] Received EventEntity: " + receivedEvent);
            System.out.println("[Consumer] Processing EventType: " + receivedEvent.eventType());
            System.out.println("[Consumer] Processing Message: " + receivedEvent.message());
            // Sua lógica de processamento do evento aqui

        } catch (Exception ex) {
            System.err.println("[Consumer] Error deserializing message: " + ex.getMessage());
            throw new EventConsumerException("[Consumer] Error deserializing message: " + ex.getMessage());
        }
    }
}
