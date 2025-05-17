package com.angelozero.task.management.adapter.dataprovider;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.EventEntity;
import com.angelozero.task.management.adapter.dataprovider.jpa.repository.postgres.reader.EventReaderDataBaseRepository;
import com.angelozero.task.management.adapter.dataprovider.jpa.repository.postgres.writer.EventWriterDataBaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EventByPostgresDataProvider {

    private final EventWriterDataBaseRepository eventWriterDataBaseRepository;
    private final EventReaderDataBaseRepository eventReaderDataBaseRepository;

    public void save() {
        var entityWriter = new EventEntity(null, "test", 0, 0, LocalDateTime.now(), false, "test");
        var entityReader = new EventEntity(null, "test", 0, 0, LocalDateTime.now(), false, "test");
        var savedWriterEntity = eventWriterDataBaseRepository.saveAndFlush(entityWriter);
        var savedReaderEntity = eventReaderDataBaseRepository.saveAndFlush(entityReader);

        System.out.println(savedWriterEntity);
        System.out.println(savedReaderEntity);
    }
}
