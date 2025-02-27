package com.angelozero.task.management.usecase.gateway;

import com.angelozero.task.management.entity.Task;

import java.util.List;

public interface TaskGateway {

    List<Task> getAll();

    void save(Task task);

}
