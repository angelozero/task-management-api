package com.angelozero.task.management.adapter.dataprovider;


import com.angelozero.task.management.adapter.config.RabbitMQConfig;
import com.angelozero.task.management.entity.Event;
import com.angelozero.task.management.usecase.exception.EventPublisherException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EventPublisherByRabbitMQDataProvider {

    private final RabbitMQConfig rabbitMQConfig;
    private final AmqpTemplate rabbitTemplate;
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    public void publish(Event event) {
        try {
            var messageJson = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(rabbitMQConfig.getExchangeName(), rabbitMQConfig.getRoutingKey(), messageJson);
            System.out.println("[Producer] Sent: " + messageJson);

        } catch (Exception ex) {
            log.error("[Producer] Error sending message: {}", ex.getMessage());
            throw new EventPublisherException("[Producer] Error sending message: " + ex.getMessage());
        }

    }
}
