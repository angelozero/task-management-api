package com.angelozero.task.management.usecase.gateway;

import com.angelozero.task.management.entity.Task;
import org.springframework.data.domain.Page;

public interface TaskGateway {

    Task findById(String id);

    Page<Task> findAll(int page, int size, String sortField, Boolean isCompleted);

    void save(Task task);

    Task update(Task task);

    void delete(Task task);


}
