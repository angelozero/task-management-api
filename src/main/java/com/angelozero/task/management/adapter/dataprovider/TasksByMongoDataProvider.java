package com.angelozero.task.management.adapter.dataprovider;

import com.angelozero.task.management.adapter.dataprovider.jpa.repository.TaskRepository;
import com.angelozero.task.management.adapter.dataprovider.mapper.TaskDataProviderMapper;
import com.angelozero.task.management.entity.Task;
import com.angelozero.task.management.usecase.gateway.TaskGateway;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public Page<Task> getAll(int page, int size, String sortField) {
        var pageable = StringUtils.isBlank(sortField) ? PageRequest.of(page, size) : PageRequest.of(page, size, Sort.by(sortField));
        var pagedTasksEntity = taskRepository.findAll(pageable);
        var tasks = taskDataProviderMapper.toTaskList(pagedTasksEntity.getContent());

        return new PageImpl<>(tasks, pageable, pagedTasksEntity.getTotalElements());
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
