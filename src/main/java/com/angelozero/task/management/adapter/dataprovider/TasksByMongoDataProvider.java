package com.angelozero.task.management.adapter.dataprovider;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.TaskEntity;
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
    public List<Task> findByIds(List<String> ids) {
        var taskEntityList = taskRepository.findByIdIn(ids);

        return taskDataProviderMapper.toTaskList(taskEntityList);
    }


    @Override
    public Page<Task> findAll(int page, int size, String sortField, Boolean isCompleted) {
        Page<TaskEntity> pagedTasksEntity;
        var pageable = StringUtils.isBlank(sortField) ? PageRequest.of(page, size) : PageRequest.of(page, size, Sort.by(sortField));

        if (isCompleted != null) {
            pagedTasksEntity = taskRepository.findByCompleted(isCompleted, pageable);

        } else {
            pagedTasksEntity = taskRepository.findAll(pageable);
        }

        var tasks = taskDataProviderMapper.toTaskList(pagedTasksEntity.getContent());

        return new PageImpl<>(tasks, pageable, pagedTasksEntity.getTotalElements());
    }

    @Override
    public void save(Task task) {
        var taskEntity = taskDataProviderMapper.toTaskEntity(task);
        taskRepository.save(taskEntity);
    }

    @Override
    public void saveAll(List<Task> taskList) {
        var taskEntityList = taskDataProviderMapper.toTaskEntityList(taskList);
        taskRepository.saveAll(taskEntityList);
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
