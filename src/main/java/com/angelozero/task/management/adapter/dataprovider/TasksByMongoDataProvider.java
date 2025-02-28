package com.angelozero.task.management.adapter.dataprovider;

import com.angelozero.task.management.adapter.dataprovider.jpa.repository.TaskRepository;
import com.angelozero.task.management.adapter.dataprovider.mapper.TaskDataProviderMapper;
import com.angelozero.task.management.entity.Task;
import com.angelozero.task.management.usecase.gateway.TaskGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TasksByMongoDataProvider implements TaskGateway {

    private final TaskRepository taskRepository;
    private final TaskDataProviderMapper taskDataProviderMapper;

    @Override
    public Task findById(String id) {
        var taskEntity = taskRepository.findById(id).orElse(null);

        return taskDataProviderMapper.toTask(taskEntity);
    }

    @Override
    public List<Task> getAll() {
        var taskEntityList = taskRepository.findAll();

        return taskDataProviderMapper.toTaskList(taskEntityList);
    }

    @Override
    public void save(Task task) {
        var taskEntity = taskDataProviderMapper.toTaskEntity(task);

        taskRepository.save(taskEntity);
    }

    @Override
    public Task update(Task task) {
        var taskEntity = taskDataProviderMapper.toTaskEntity(task);
        var taskEntityUpdated = taskRepository.save(taskEntity);

        return taskDataProviderMapper.toTask(taskEntityUpdated);
    }

    @Override
    public void delete(Task task) {
        var taskEntity = taskDataProviderMapper.toTaskEntity(task);

        taskRepository.delete(taskEntity);
    }
}
