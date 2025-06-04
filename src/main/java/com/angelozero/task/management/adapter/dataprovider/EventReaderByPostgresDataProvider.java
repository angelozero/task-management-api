package com.angelozero.task.management.adapter.dataprovider;

import com.angelozero.task.management.adapter.dataprovider.jpa.repository.postgres.reader.EventReaderDataBaseRepository;
import com.angelozero.task.management.adapter.dataprovider.mapper.EventDataProviderMapper;
import com.angelozero.task.management.entity.Event;
import com.angelozero.task.management.usecase.exception.DataBaseDataProviderException;
import com.angelozero.task.management.usecase.gateway.event.EventReaderGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EventReaderByPostgresDataProvider implements EventReaderGateway {

    private final EventReaderDataBaseRepository eventReaderDataBaseRepository;
    private final EventDataProviderMapper eventDataProviderMapper;

    @Override
    public Event getById(Integer id) {
        try {
            var eventEntity = eventReaderDataBaseRepository.findById(id).orElse(null);

            return eventDataProviderMapper.toEvent(eventEntity);

        } catch (Exception ex) {
            log.error("Fail to get an Event into the reader database by id - Fail: {}", ex.getMessage());
            throw new DataBaseDataProviderException("Fail to get an Event into the reader database by id - Fail: " + ex.getMessage());
        }
    }

    @Override
    public Event getByPersonId(String id) {
        try {
            var eventEntity = eventReaderDataBaseRepository.findByUserId(id).orElse(null);

            return eventDataProviderMapper.toEvent(eventEntity);

        } catch (Exception ex) {
            log.error("Fail to get an Event into the reader database by Person id - Fail: {}", ex.getMessage());
            throw new DataBaseDataProviderException("Fail to get an Event into the reader database by Person id - Fail: " + ex.getMessage());
        }
    }

    @Override
    public Event save(Event event) {
        try {
            var eventEntity = eventDataProviderMapper.toEventEntity(event);
            var eventEntitySaved = eventReaderDataBaseRepository.save(eventEntity);

            return eventDataProviderMapper.toEvent(eventEntitySaved);

        } catch (Exception ex) {
            log.error("Fail to save an Event into the reader database - Fail: {}", ex.getMessage());
            throw new DataBaseDataProviderException("Fail to save an Event into the reader database - Fail: " + ex.getMessage());
        }
    }
}
