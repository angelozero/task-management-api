package com.angelozero.task.management.adapter.dataprovider.mapper;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.TaskEntity;
import com.angelozero.task.management.entity.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskDataProviderMapper {

    List<Task> toTaskList(List<TaskEntity> taskEntityList);

    TaskEntity toTaskEntity(Task task);

    Task toTask(TaskEntity taskEntity);
}
