package com.angelozero.task.management.adapter.dataprovider.jpa.repository;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.TaskEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<TaskEntity, String> {
}
