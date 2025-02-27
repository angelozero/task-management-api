package com.angelozero.task.management.adapter.dataprovider;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.TaskEntity;
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
    public List<Task> getAll() {
        var taskEntityList = taskRepository.findAll();
        return taskDataProviderMapper.toTaskList(taskEntityList);
    }

    @Override
    public void save(Task task) {
        var taskEntity = taskDataProviderMapper.toTaskEntity(task);
        taskRepository.save(taskEntity);
    }
}
