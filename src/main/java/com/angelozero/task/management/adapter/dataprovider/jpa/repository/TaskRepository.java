package com.angelozero.task.management.adapter.dataprovider.jpa.repository;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<TaskEntity, String> {

    Page<TaskEntity> findByCompleted(Boolean completed, Pageable pageable);

    List<TaskEntity> findByIdIn(List<String> ids);
}
