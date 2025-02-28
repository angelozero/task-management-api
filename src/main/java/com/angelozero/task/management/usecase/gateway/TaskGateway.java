package com.angelozero.task.management.usecase.gateway;

import com.angelozero.task.management.entity.Task;

import java.util.List;

public interface TaskGateway {

    Task findById(String id);

    List<Task> getAll();

    void save(Task task);

    Task update(Task task);

    void delete(Task task);


}
